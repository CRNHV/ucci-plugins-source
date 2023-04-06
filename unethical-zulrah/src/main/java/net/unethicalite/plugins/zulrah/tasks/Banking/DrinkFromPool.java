package net.unethicalite.plugins.zulrah.tasks.Banking;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.api.TileObject;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Combat;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.api.widgets.Prayers;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.framework.SessionUpdater;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class DrinkFromPool extends SessionUpdater implements Task
{
    List<String> ItemList = Arrays.asList(
            Constants.ANTIVENOM_POT + "(4)",
            Constants.PRAYER_POT + "(4)",
            Constants.ZUL_ANDRA_TELEPORT,
            Constants.PRAYER_POT + "(4)",
            Constants.RANGE_POTION + "(4)",
            Constants.MAGIC_POT + "(4)",
            Constants.RING_OF_RECOIL,
            Constants.RING_OF_RECOIL);

    @Override
    public boolean validate()
    {
        for (String item : ItemList)
        {
            if (!Inventory.contains(item))
            {
                return false;
            }
        }

        TileObject healPool = TileObjects.getNearest(c -> c.hasAction("Drink"));

        boolean hasKarambwan = Inventory.getCount(Constants.KARAMBWAN) > 12;
        boolean hasRecoil = Inventory.getCount(Constants.RING_OF_RECOIL) == 2;
        boolean hasPrayer = Inventory.getCount(Constants.PRAYER_POT + "(4)") == 2;

        return healPool != null && hasKarambwan && hasRecoil && hasPrayer && !LocalPlayer.get().isMoving();
    }

    @Override
    public int execute()
    {
        log.info("Drinking from pool");
        updateTask("Drinking from pool");

        TileObject healPool = TileObjects.getNearest(c -> c.hasAction("Drink"));
        if (healPool == null)
        {
            log.warn("[DrinkFromPool] Unable to find health pool");
            return 600;
        }

        healPool.interact("Drink");
        Time.sleepUntil((() -> Prayers.getPoints() == Skills.getLevel(Skill.PRAYER) && Combat.getMissingHealth() == 0), 15000);

        Time.sleepTicks(4);
        Inventory.getFirst(Constants.ZUL_ANDRA_TELEPORT).interact("Teleport");
        Time.sleepTicks(2);

        return 600;
    }
}
