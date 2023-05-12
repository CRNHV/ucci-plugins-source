package net.devious.plugins.flaxspinner.tasks;

import net.devious.plugins.flaxspinner.framework.SessionTask;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

public class SpinFlax extends SessionTask
{
    private WorldPoint spinningWheelPoint = new WorldPoint(3209, 3213, 1);

    @Override
    public boolean validate()
    {
        return spinningWheelPoint.distanceTo(LocalPlayer.get()) <= 1
                && Inventory.contains("Flax")
                && !LocalPlayer.get().isAnimating();
    }

    @Override
    public int execute()
    {
        TileObject wheel = TileObjects.getNearest("Spinning wheel");
        if (wheel == null)
        {
            return 600;
        }

        wheel.interact("Spin");
        Time.sleepTick();
        Widget spinWidget = Widgets.fromId(17694736);
        if (spinWidget != null)
        {
            spinWidget.interact("Spin");
            Time.sleepUntil(() -> !Inventory.contains("Flax"), 20_000);
        }
        return 600;
    }

    @Override
    public String getCurrentTask()
    {
        return "Spin flax";
    }
}
