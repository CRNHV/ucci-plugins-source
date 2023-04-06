package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

@Slf4j
public class Traverse extends ZulrahTask
{
    @Override
    public boolean validate()
    {
        var groundContainsZulrahScroll = TileObjects.getNearest("Zul-Andra teleport") != null;
        return getZulrahCycle() != null
                && (!Players.getLocal().isMoving() || Players.getLocal().getInteracting() != null)
                && !isCloud(getZulrahCycle().getSafeSpot(getOrigin()))
                && (!getZulrahCycle().getSafeSpot(getOrigin()).equals(Players.getLocal().getWorldLocation())
                && getZulrahCycle() != ZulrahCycle.MAGMA_CENTER_NW
                && getZulrahCycle() != ZulrahCycle.MAGMA_CENTER_NE)
                && !groundContainsZulrahScroll;
    }

    @Override
    public int execute()
    {
        updateTask("Traversing to new safespot");

        Player local = Players.getLocal();
        WorldPoint westPillar = getOrigin().dx(-3).dy(3);
        WorldPoint eastPillar = getOrigin().dx(3).dy(3);

        if (getZulrahCycle().isCenter() && !Players.getLocal().getWorldLocation().equals(getZulrahCycle().getSafeSpot(getOrigin())))
        {
            if (westPillar.distanceTo(local) > eastPillar.distanceTo(local))
            {
                Movement.walkTo(getZulrahCycle().getSafeSpot(getOrigin()));
                return 1300;
            }

            Movement.walk(getZulrahCycle().getSafeSpot(getOrigin()));
            return 1300;
        }

        Movement.walk(getZulrahCycle().getSafeSpot(getOrigin()));
        return 550;
    }

    @Override
    public boolean isBlocking()
    {
        return false;
    }
}
