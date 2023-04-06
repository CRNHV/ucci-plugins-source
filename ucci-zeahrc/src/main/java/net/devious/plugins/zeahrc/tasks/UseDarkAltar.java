package net.devious.plugins.zeahrc.tasks;

import net.devious.plugins.zeahrc.data.Constants;
import net.devious.plugins.zeahrc.framework.SessionUpdater;
import net.runelite.api.TileObject;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class UseDarkAltar extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        TileObject darkAltar = TileObjects.getNearest(c -> c.getName().equalsIgnoreCase(Constants.DarkAltar));

        return Inventory.isFull()
                && Inventory.contains(Constants.DenseEssenceBlock)
                && darkAltar != null && darkAltar.distanceTo(LocalPlayer.get()) < 10;
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Using dark altar");

        TileObject darkAltar = TileObjects.getNearest(c -> c.getName().equalsIgnoreCase(Constants.DarkAltar));
        if (darkAltar == null)
        {
            return 600;
        }

        darkAltar.interact("Venerate");
        int darkAltarTrips = getSession().getDarkAltarTrips();
        darkAltarTrips += 1;
        getSession().setDarkAltarTrips(darkAltarTrips);
        Time.sleep(200, 12000);
        return 600;
    }
}
