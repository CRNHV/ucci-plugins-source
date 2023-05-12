package net.devious.plugins.flaxspinner.tasks;

import net.devious.plugins.flaxspinner.framework.SessionTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class WalkToSpinningWheel extends SessionTask
{
    private WorldPoint spinningWheelPoint = new WorldPoint(3209, 3213, 1);

    @Override
    public boolean validate()
    {
        return Inventory.contains("Flax") && spinningWheelPoint.distanceTo(LocalPlayer.get()) > 1;
    }

    @Override
    public int execute()
    {
        Movement.walkTo(spinningWheelPoint);
        Time.sleepTick();
        Time.sleepUntil(() -> !LocalPlayer.get().isMoving(), 4800);
        return 600;
    }

    @Override
    public String getCurrentTask()
    {
        return "Walk to spinning wheel";
    }
}