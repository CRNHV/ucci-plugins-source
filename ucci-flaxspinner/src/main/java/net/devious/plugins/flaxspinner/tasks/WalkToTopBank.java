package net.devious.plugins.flaxspinner.tasks;

import net.devious.plugins.flaxspinner.framework.SessionTask;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.pathfinder.model.BankLocation;

public class WalkToTopBank extends SessionTask
{
    @Override
    public boolean validate()
    {
        return !Inventory.contains("Flax")
                && !BankLocation.LUMBRIDGE_BANK.getArea().contains(LocalPlayer.get());
    }

    @Override
    public int execute()
    {
        Movement.walkTo(BankLocation.LUMBRIDGE_BANK.getArea());
        Time.sleepTick();
        Time.sleepUntil(() -> !LocalPlayer.get().isMoving(), 5200);
        return 100;
    }

    @Override
    public String getCurrentTask()
    {
        return "Walk to bank";
    }
}
