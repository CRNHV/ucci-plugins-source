package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.NPC;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

public class AttackZulrah extends ZulrahTask
{
    @Override
    public boolean validate()
    {
        boolean canAttack =
                NPCs.getNearest(x -> x.getName().contains(Constants.ZULRAH_NAME) && x.getHealthRatio() != 0) != null
                        && NPCs.getNearest(Constants.ZULRAH_NAME).getAnimation() != Constants.DISAPPEAR_ANIMATION
                        && NPCs.getNearest(Constants.ZULRAH_NAME).getAnimation() != Constants.APPEAR_ANIMATION;

        return (getZulrahCycle() != null
                && Players.getLocal().getInteracting() == null
                && (getZulrahCycle().getSafeSpot(getOrigin()).distanceTo(Players.getLocal()) <= 8
                || getZulrahCycle().equals(ZulrahCycle.TANZ_SOUTH_CW))
                && !inCloud()
                && canAttack)
                && attackCondition();
    }

    @Override
    public int execute()
    {
        updateTask("Attacking zulrah");

        NPC zulrah = NPCs.getNearest(x -> x.getName().equals(Constants.ZULRAH_NAME) && x.getHealthRatio() != 0);
        if (zulrah != null)
        {
            zulrah.interact("Attack");
        }

        Time.sleep(100, 200);
        return 500;
    }

    private boolean attackCondition()
    {
        return getZulrahCycle() != ZulrahCycle.GREEN_SOUTH_E || !Players.getLocal().isMoving();
    }
}
