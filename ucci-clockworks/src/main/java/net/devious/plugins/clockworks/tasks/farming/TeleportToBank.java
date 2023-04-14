package net.devious.plugins.clockworks.tasks.farming;

import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.Spell;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.client.Static;

public class TeleportToBank extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Static.getClient().isInInstancedRegion()
                && !Inventory.contains("Steel bar")
                && Inventory.contains("Law rune")
                && Equipment.contains("Dust battlestaff");
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Teleport to bank");

        Spell teleport = SpellBook.Standard.CAMELOT_TELEPORT;
        Magic.cast(teleport);
        return 600;
    }
}
