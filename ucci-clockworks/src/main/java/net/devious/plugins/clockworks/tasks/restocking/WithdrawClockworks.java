package net.devious.plugins.clockworks.tasks.restocking;

import net.devious.plugins.clockworks.data.Locations;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.NPC;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.GrandExchange;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class WithdrawClockworks extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Locations.GE_CENTER.contains(LocalPlayer.get()) && !Inventory.contains("Clockwork") && !Inventory.contains("Coins") && !GrandExchange.isOpen();
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Withdraw clockwork/coins");

        if (!Bank.isOpen())
        {
            OpenBank();
            return 600;
        }

        if (!Inventory.contains("Clockwork"))
        {
            if (Bank.getCount(true, "Clockwork") >= 1)
            {
                Bank.withdrawAll("Clockwork", Bank.WithdrawMode.NOTED);
            }
            else
            {
                Bank.withdrawAll("Coins", Bank.WithdrawMode.ITEM);
            }

            Time.sleepTick();
            Bank.close();
        }

        return 600;
    }

    private void OpenBank()
    {
        NPC banker = NPCs.getNearest(c -> c.hasAction("Bank"));
        if (banker != null)
        {
            banker.interact("Bank");
        }
    }
}
