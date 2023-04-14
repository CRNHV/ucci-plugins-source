package net.devious.plugins.clockworks.tasks.farming;

import net.devious.plugins.clockworks.data.ItemIds;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.Spell;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.client.Static;

public class TeleportToHouse extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Inventory.contains(ItemIds.STEELBAR)
                && Inventory.contains("Law rune")
                && Equipment.contains("Dust battlestaff")
                && !Static.getClient().isInInstancedRegion();
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Teleport to house");

        Spell teleport = SpellBook.Standard.TELEPORT_TO_HOUSE;
        Magic.cast(teleport);
        return 600;
    }
}
