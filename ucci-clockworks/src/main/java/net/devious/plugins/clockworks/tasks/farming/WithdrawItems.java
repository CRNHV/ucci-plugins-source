package net.devious.plugins.clockworks.tasks.farming;

import net.devious.plugins.clockworks.framework.BotMemory;
import net.devious.plugins.clockworks.framework.MemoryConstants;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class WithdrawItems extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Bank.isOpen() && Inventory.isEmpty();
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Withdraw items");

        if (!Bank.isOpen())
        {
            return 600;
        }

        if (Bank.getCount(true, "Steel bar") >= 1 && Bank.getCount(true, "Law rune") >= 10)
        {
            Bank.withdrawAll("Law rune", Bank.WithdrawMode.ITEM);
            Bank.withdrawAll("Steel bar", Bank.WithdrawMode.ITEM);
            Time.sleepTick();
        }

        if (Bank.getCount(true, "Steel bar") <= 1 && !Inventory.contains("Steel bar"))
        {
            BotMemory.setBool(MemoryConstants.BL_NEEDS_RESTOCK, true);
            BotMemory.setBool(MemoryConstants.BL_NEEDS_STEELBAR, true);
        }

        return 600;
    }

    @Override
    public boolean isBlocking()
    {
        return true;
    }
}
