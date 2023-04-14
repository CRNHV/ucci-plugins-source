package net.devious.plugins.clockworks.tasks.restocking;

import net.devious.plugins.clockworks.data.Locations;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class WithdrawTeleTab extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Bank.isOpen() && !Inventory.contains("Varrock teleport") && !Locations.GE_CENTER.contains(LocalPlayer.get());
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Withdraw tab");

        Bank.depositAllExcept(-1);
        Bank.withdraw("Varrock teleport", 1, Bank.WithdrawMode.ITEM);
        Time.sleepTick();
        return 600;
    }
}
