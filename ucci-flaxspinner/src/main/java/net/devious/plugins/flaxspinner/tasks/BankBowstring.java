package net.devious.plugins.flaxspinner.tasks;

import net.devious.plugins.flaxspinner.framework.SessionTask;
import net.runelite.api.NPC;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.pathfinder.model.BankLocation;

public class BankBowstring extends SessionTask
{
    @Override
    public boolean validate()
    {
        return Inventory.contains("Bow string") && BankLocation.LUMBRIDGE_BANK.getArea().contains(LocalPlayer.get());
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

        Bank.depositInventory();
        return 678;
    }

    @Override
    public String getCurrentTask()
    {
        return "Bank bowstring";
    }
}
