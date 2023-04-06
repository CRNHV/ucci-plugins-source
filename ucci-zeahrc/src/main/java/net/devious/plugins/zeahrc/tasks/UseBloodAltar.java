package net.devious.plugins.zeahrc.tasks;

import net.devious.plugins.zeahrc.data.Constants;
import net.devious.plugins.zeahrc.framework.SessionUpdater;
import net.runelite.api.TileObject;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class UseBloodAltar extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        TileObject bloodAltar = TileObjects.getNearest(c -> c.hasAction("Bind"));

        return bloodAltar != null && Inventory.contains(Constants.EssenceFragments) && bloodAltar.distanceTo(LocalPlayer.get()) < 25;
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Using blood altar");

        TileObjects.getNearest(c -> c.hasAction("Bind")).interact("Bind");
        Time.sleepUntil(() -> !Inventory.contains(Constants.EssenceFragments), 4800);
        Time.sleep(600, 10000);
        return 600;
    }

    @Override
    public boolean isBlocking()
    {
        return true;
    }
}
