package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

import java.util.List;

public class AvoidAttack extends ZulrahTask
{
    private static final List<Integer> DANGEROUS_ROTATIONS = List.of(1944, 1641, 2048, 2492, 2550);
    private WorldPoint initial;
    private WorldPoint avoid;

    @Override
    public boolean validate()
    {
        NPC zulrah = NPCs.getNearest(c -> c.getName().equalsIgnoreCase(Constants.ZULRAH_NAME) && c.getHealthRatio() > 0);
        if (zulrah == null || zulrah.isDead())
        {
            return false;
        }

        return getZulrahCycle() != null
                && (getZulrahCycle() == ZulrahCycle.MAGMA_CENTER_NW || getZulrahCycle() == ZulrahCycle.MAGMA_CENTER_NE)
                && (Players.getLocal().getInteracting() != null || inCloud()
                || getZulrahCycle().getSafeSpot(getOrigin()).distanceTo(Players.getLocal()) > 1) // Used to be 4
                && !Movement.isWalking();
//				&& zulrah.getAnimation() == 5806;
    }

    @Override
    public int execute()
    {
        updateTask("Avoiding attack");

        NPC zulrah = NPCs.getNearest(Constants.ZULRAH_NAME);
        initial = getZulrahCycle().getSafeSpot(getOrigin());
        WorldPoint myPos = Players.getLocal().getWorldLocation();

        if (zulrah != null)
        {
            int sum = zulrah.getOrientation() + Players.getLocal().getOrientation();
            if (getZulrahCycle() == ZulrahCycle.MAGMA_CENTER_NW)
            {
                avoid = initial.dx(-1).dy(-3);
            }
            else
            {
                avoid = initial.dx(-2).dy(0);
            }

            if (!DANGEROUS_ROTATIONS.contains(sum))
            {
                if (initial.distanceTo(myPos) > 4 && !Movement.isWalking())
                {
                    Movement.walk(avoid);
                }
            }
            else
            {
                if (avoid.equals(myPos)
                        && !Movement.isWalking())
                {
                    Movement.walk(initial);
                }
                else if (!Movement.isWalking())
                {
                    Movement.walk(avoid);
                }
            }
        }
        else if (!myPos.equals(initial) && !Movement.isWalking())
        {
            Movement.walk(initial);
        }

        return 300;
    }
}
