package net.devious.plugins.clockworks.tasks.farming;

import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.TileObject;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.client.Static;

public class MakeClockwork extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Static.getClient().isInInstancedRegion() && Inventory.contains("Steel bar");
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Making clockwork");
        TileObject bench = TileObjects.getNearest(c -> c.getName().startsWith("Clockmaker"));
        if (bench == null)
        {
            return 600;
        }

        if (bench.distanceTo(LocalPlayer.get()) > 2)
        {
            bench.interact("Craft");
            Time.sleepUntil(() -> !LocalPlayer.get().isMoving(), 1800);
            return -1;
        }

        if (Dialog.canContinue())
        {
            Dialog.continueSpace();
        }

        if (Dialog.isOpen())
        {
            Dialog.chooseOption(1); // Make clockwork
            return 1200;
        }

        if (LocalPlayer.get().getAnimation() == 4107)
        {
            bench.interact("Craft");
            return 600;
        }

        return -1;
    }
}
