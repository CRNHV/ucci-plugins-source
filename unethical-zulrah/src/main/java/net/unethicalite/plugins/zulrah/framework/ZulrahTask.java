package net.unethicalite.plugins.zulrah.framework;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.GameObject;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;

@Getter
@Setter
public abstract class ZulrahTask extends SessionUpdater implements Task
{
    private ZulrahCycle zulrahCycle;
    private WorldPoint origin;

    protected boolean isCloud(WorldPoint position)
    {
        for (TileObject cloud : TileObjects.getSurrounding(
                Players.getLocal().getWorldLocation(), 10, 11700))
        {
            if (cloud instanceof GameObject)
            {
                WorldArea area = ((GameObject) cloud).getWorldArea();
                if (area.contains(position))
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean inCloud()
    {
        return isCloud(Players.getLocal().getWorldLocation());
    }
}
