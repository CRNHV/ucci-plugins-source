package net.unethicalite.plugins.zulrah.tasks.Banking;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.TileObject;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.framework.SessionUpdater;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class WithdrawFromBank extends SessionUpdater implements Task
{
    List<String> ItemList = Arrays.asList(
            Constants.ANTIVENOM_POT + "(4)",
            Constants.ZUL_ANDRA_TELEPORT,
            Constants.PRAYER_POT + "(4)",
            Constants.PRAYER_POT + "(4)",
            Constants.RANGE_POTION + "(4)",
            Constants.MAGIC_POT + "(4)",
            Constants.RING_OF_RECOIL,
            Constants.RING_OF_RECOIL);

    @Override
    public boolean validate()
    {
        boolean hasNoMoreFood = Inventory.getCount(c -> c.hasAction("Eat")) < 10;
        boolean hasNoMoreAntiV = Inventory.getCount(c -> c.getName().contains("Anti-venom")) == 0;
        boolean hasNoMorePrayer = Inventory.getCount(c -> c.getName().contains("Prayer")) == 0;
        boolean seesBanker = NPCs.getNearest(c -> c.hasAction("Bank")) != null;

        return seesBanker && (hasNoMoreFood || hasNoMoreAntiV || hasNoMorePrayer);
    }

    @Override
    public int execute()
    {
        log.info("Getting items from bank");
        updateTask("Getting items from bank");

        TileObject bankChest = TileObjects.getNearest(c -> c.hasAction("Use") && c.getName().equalsIgnoreCase("Bank chest"));

        if (bankChest == null)
        {
            return 600;
        }

        if (!Bank.isOpen())
        {
            bankChest.interact("Use");
            Time.sleepUntil(() -> Bank.isOpen(), 3600);
            Time.sleepTicks(1);
            return 600;
        }

        for (String itemName : ItemList)
        {
            if (Bank.getCount(true, item -> item.getName().equalsIgnoreCase(itemName)) == 0)
            {
                getPlugin().stop();
                return 600;
            }
            Bank.withdraw(itemName, 1, Bank.WithdrawMode.ITEM);
            Time.sleep(40, 75);
        }

        int freeSlots = Inventory.getFreeSlots();
        int karambwanAmount = Bank.getCount(true, 3144);

        if (freeSlots > karambwanAmount)
        {
            getPlugin().stop();
            return 100;
        }
        else
        {
            log.info("Getting karambwans");
            Bank.withdrawAll(Constants.KARAMBWAN, Bank.WithdrawMode.ITEM);
            Time.sleep(400, 800);
        }
        Bank.close();
        return 100;
    }

}
