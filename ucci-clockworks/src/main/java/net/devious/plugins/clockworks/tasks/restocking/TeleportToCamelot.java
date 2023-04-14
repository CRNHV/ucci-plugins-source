package net.devious.plugins.clockworks.tasks.restocking;

import net.devious.plugins.clockworks.data.Locations;
import net.devious.plugins.clockworks.framework.BotMemory;
import net.devious.plugins.clockworks.framework.MemoryConstants;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.NPC;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.Spell;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.plugins.Task;

public class TeleportToCamelot extends SessionUpdater implements Task
{

    @Override
    public boolean validate()
    {
        boolean playerAtGe = Locations.GE_CENTER.contains(LocalPlayer.get());
        boolean restockDone = BotMemory.getBool(MemoryConstants.BL_NEEDS_RESTOCK) == false;

        System.out.print(playerAtGe + " " + restockDone);

        return Locations.GE_CENTER.contains(LocalPlayer.get()) && BotMemory.getBool(MemoryConstants.BL_NEEDS_RESTOCK) == false;
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Teleport to camelot");

        if (!Bank.isOpen())
        {
            NPC banker = NPCs.getNearest(c -> c.hasAction("Bank"));
            if (banker != null)
            {
                banker.interact("Bank");
            }
            return 600;
        }

        Bank.depositAllExcept(-1);
        Bank.withdrawAll("Law rune", Bank.WithdrawMode.ITEM);
        Bank.close();
        Spell teleport = SpellBook.Standard.CAMELOT_TELEPORT;
        Magic.cast(teleport);
        return 600;
    }
}
