package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.pathfinder.Walker;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.plugins.zulrah.framework.SessionUpdater;

public class EnterZulrah extends SessionUpdater implements Task
{
    private static final WorldPoint BOAT_LOC = new WorldPoint(2215, 3057, 0);

    @Override
    public boolean validate()
    {
        boolean hasEnoughFood = Inventory.getCount("Cooked karambwan") >= 10;
        boolean hasEnoguhPrayer = Inventory.contains(i -> i.getName().contains("Prayer"));
        return TileObjects.getFirstSurrounding(BOAT_LOC, 10, x -> x.hasAction("Quick-Board")) != null && hasEnoughFood && hasEnoguhPrayer;
    }

    @Override
    public int execute()
    {
        updateTask("Entering Zulrah");

        TileObject boat = TileObjects.getFirstSurrounding(BOAT_LOC, 10, x -> x.hasAction("Quick-Board"));
        if (Movement.isWalking())
        {
            return 1000;
        }

        if (boat != null)
        {
            boat.interact("Quick-Board");
        }
        else
        {
            if (BOAT_LOC.distanceTo(LocalPlayer.get()) < 20)
            {
                Walker.walkTo(BOAT_LOC);
                Time.sleepUntil(() -> !Movement.isWalking(), 4800);
            }
        }

        return 1000;
    }
}
