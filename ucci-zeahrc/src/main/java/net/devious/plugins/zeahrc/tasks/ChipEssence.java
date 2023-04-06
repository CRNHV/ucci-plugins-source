package net.devious.plugins.zeahrc.tasks;

import net.devious.plugins.zeahrc.data.Constants;
import net.devious.plugins.zeahrc.framework.SessionUpdater;
import net.runelite.api.Item;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class ChipEssence extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Inventory.isFull()
                && !Inventory.contains(c -> c.getName().equalsIgnoreCase(Constants.EssenceFragments))
                || (!Inventory.contains(c -> c.getName().equalsIgnoreCase(Constants.EssenceFragments))
                && Inventory.contains(Constants.DarkEssenceBlock)
                && TileObjects.getNearest(c -> c.hasAction("Bind")) != null);
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Chipping essence");

        Item chisel = Inventory.getFirst(c -> c.getName().equalsIgnoreCase("Chisel"));
        if (chisel == null)
        {
            return 600;
        }

        while (Inventory.contains(c -> c.getName().equalsIgnoreCase(Constants.DarkEssenceBlock)))
        {
            Item darkEssenceBlock = Inventory.getFirst(c -> c.getName().equalsIgnoreCase(Constants.DarkEssenceBlock));
            if (darkEssenceBlock == null)
            {
                return 600;
            }

            chisel.useOn(darkEssenceBlock);
        }
        return 100;
    }
}
