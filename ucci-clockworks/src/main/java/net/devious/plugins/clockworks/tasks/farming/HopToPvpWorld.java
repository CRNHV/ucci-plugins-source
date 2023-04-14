package net.devious.plugins.clockworks.tasks.farming;

import net.devious.plugins.clockworks.data.Locations;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.World;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.api.widgets.Dialog;

public class HopToPvpWorld extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Locations.CAMELOT_TELEPORT.distanceTo(LocalPlayer.get()) < 10 && Worlds.getCurrentWorld().getId() != getConfig().pkWorld();
    }

    @Override
    public int execute()
    {
        if (Dialog.canContinue())
        {
            Dialog.continueSpace();
            return 300;
        }

        int pvpWorldId = getConfig().pkWorld();

        World worldToHopTo = Worlds.getFirst(pvpWorldId);
        if (worldToHopTo != null)
        {
            getSession().setCurrentTask("Hopping to pvp world");
            Worlds.hopTo(worldToHopTo);
        }
        else
        {
            getSession().setCurrentTask("World" + pvpWorldId + " not found");
        }


        return 300;
    }
}
