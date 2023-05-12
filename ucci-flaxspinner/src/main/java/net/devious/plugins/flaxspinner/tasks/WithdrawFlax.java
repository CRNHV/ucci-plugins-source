package net.devious.plugins.flaxspinner.tasks;

import net.devious.plugins.flaxspinner.framework.SessionTask;
import net.runelite.api.NPC;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.pathfinder.model.BankLocation;
import net.unethicalite.client.Static;

public class WithdrawFlax extends SessionTask
{
    @Override
    public boolean validate()
    {
        return Inventory.isEmpty() && BankLocation.LUMBRIDGE_BANK.getArea().contains(LocalPlayer.get());
    }

    @Override
    public int execute()
    {
        if (!Bank.isOpen())
        {
            NPC banker = NPCs.getNearest("Banker");
            if (banker == null)
            {
                return 100;
            }

            banker.interact("Bank");
            return 100;
        }

        if (Bank.contains("Flax"))
        {
            Bank.withdrawAll("Flax", Bank.WithdrawMode.ITEM);
            return 600;
        }
        else
        {
            Static.getClient().setKeyboardIdleTicks(Integer.MAX_VALUE);
            Static.getClient().setMouseIdleTicks(Integer.MAX_VALUE);
        }

        return 100;
    }

    @Override
    public String getCurrentTask()
    {
        return "Withdraw flax";
    }
}
