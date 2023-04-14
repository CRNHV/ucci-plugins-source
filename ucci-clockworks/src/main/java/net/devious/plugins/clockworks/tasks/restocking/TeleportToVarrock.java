package net.devious.plugins.clockworks.tasks.restocking;

import net.devious.plugins.clockworks.data.Locations;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.coords.WorldArea;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class TeleportToVarrock extends SessionUpdater implements Task
{
    WorldArea varrockArea = new WorldArea(3204, 3420, 19, 19, 0);

    @Override
    public boolean validate()
    {
        return Worlds.getCurrentWorld().isAllPkWorld() == false
                && Inventory.contains("Varrock teleport")
                && !Locations.VARROCK_TELEPORT.contains(LocalPlayer.get())
                && !Locations.GE_CENTER.contains(LocalPlayer.get());
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Tele to varrock");

        Inventory.getFirst("Varrock teleport").interact("Break");
        Time.sleepUntil(() -> varrockArea.contains(LocalPlayer.get()), 5200);
        return 600;
    }
}
