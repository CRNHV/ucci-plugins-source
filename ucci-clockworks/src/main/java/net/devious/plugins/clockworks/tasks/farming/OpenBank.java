package net.devious.plugins.clockworks.tasks.farming;

import net.devious.plugins.clockworks.data.Locations;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.TileObject;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class OpenBank extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return TileObjects.getNearest(c -> c.hasAction("Use")) != null
                && !Inventory.contains("Steel bar")
                && !Bank.isOpen()
                && Locations.CAMELOT_TELEPORT.contains(LocalPlayer.get());
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Opening bank");
        TileObject bankChest = TileObjects.getNearest(c -> c.hasAction("Use"));
        if (bankChest == null)
        {
            return 300;
        }

        if (!Bank.isOpen())
        {
            bankChest.interact("Use");
            return 600;
        }

        return -1;
    }
}
