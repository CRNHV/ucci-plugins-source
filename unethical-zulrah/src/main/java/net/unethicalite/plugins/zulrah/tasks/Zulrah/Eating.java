package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.Item;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.Projectiles;
import net.unethicalite.api.game.Combat;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.plugins.zulrah.UnethicalZulrahPlugin;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahType;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

public class Eating extends ZulrahTask
{
    private Item food;
    private Item karambwan;
    private NPC zulrah;

    @Override
    public boolean validate()
    {
        food = Inventory.getFirst(x -> !x.getName().equals(Constants.KARAMBWAN) && x.hasAction(Constants.EAT_ACTION));
        karambwan = Inventory.getFirst(x -> x.hasAction(Constants.EAT_ACTION) && x.getName().equals(Constants.KARAMBWAN));
        zulrah = NPCs.getNearest(x -> x.getName().equals(Constants.ZULRAH_NAME));

        return canTickEat() || canEatInBetweenSpawns() || (food == null && canEat());
    }

    private boolean canEat()
    {
        return getZulrahCycle() != null
                && karambwan != null
                && ((getZulrahCycle().isMagic() || food == null)
                && Combat.getCurrentHealth() <= Skills.getLevel(Skill.HITPOINTS)
                && Combat.getCurrentHealth() < 60);
    }

    private boolean canTickEat()
    {
        return (UnethicalZulrahPlugin.atZulrah()
                && getZulrahCycle() != null)
                && (karambwan != null
                && Combat.getCurrentHealth() > 0
                && Players.getLocal().getAnimation() != Constants.EAT_ANIMATION
                && Inventory.getFirst(e -> e.hasAction(Constants.EAT_ACTION)) != null
                && ((Combat.getCurrentHealth() <= 45)
                || (getZulrahCycle().getZulrahType() == ZulrahType.MAGIC && Projectiles.getNearest(1044) != null
                && Combat.getCurrentHealth() <= Constants.MAX_HIT)));
    }

    private boolean canEatInBetweenSpawns()
    {
        return Combat.getCurrentHealth() <= Skills.getLevel(Skill.HITPOINTS) - 20
                && zulrah != null
                && (zulrah.getAnimation() == Constants.DISAPPEAR_ANIMATION || zulrah.getAnimation() == Constants.APPEAR_ANIMATION)
                && karambwan != null;
    }

    @Override
    public int execute()
    {
        if (canTickEat())
        {
            updateTask("Tick eating");

            karambwan.interact(Constants.EAT_ACTION);
            Time.sleep(250, 350);
        }

        if (canEatInBetweenSpawns())
        {
            updateTask("Eating in between spawns");

            karambwan.interact(Constants.EAT_ACTION);
            Time.sleep(250, 350);
        }

        if (canEat())
        {
            updateTask("Eating");

            karambwan.interact(Constants.EAT_ACTION);
        }

        return 300;
    }

    @Override
    public boolean isBlocking()
    {
        return false;
    }
}
