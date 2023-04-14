package net.devious.plugins.clockworks.tasks.restocking;

import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.Task;

public class WalkToGe extends SessionUpdater implements Task
{
    WorldArea varrockArea = new WorldArea(3137, 3376, 153, 142, 0);
    WorldArea geArea = new WorldArea(3156, 3482, 17, 17, 0);

    @Override
    public boolean validate()
    {
        return !geArea.contains(LocalPlayer.get()) && varrockArea.contains(LocalPlayer.get());
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Walk to GE");

        if (!Movement.isWalking())
        {
            Movement.walkTo(geArea);
            return 600;
        }

        WorldPoint destination = Movement.getDestination();
        if (destination != null)
        {
            Time.sleepUntil(() -> destination.distanceTo(LocalPlayer.get()) < Rand.nextInt(3, 7), 5200);
            Movement.walkTo(geArea);
        }

        return 600;
    }
}
