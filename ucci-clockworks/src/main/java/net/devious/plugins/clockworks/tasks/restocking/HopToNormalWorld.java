package net.devious.plugins.clockworks.tasks.restocking;

import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.GameState;
import net.runelite.api.World;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Worlds;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.client.Static;

public class HopToNormalWorld extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Inventory.contains("Varrock teleport") && Worlds.getCurrentWorld().isAllPkWorld();
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Hopping to norm");

        if (Bank.isOpen())
        {
            Bank.close();
            return 600;
        }

        World w = Worlds.getRandom(c -> c.isMembers()
                && !c.isAllPkWorld()
                && c.isNormal()
                && c.getLocation() == Worlds.getCurrentWorld().getLocation()
                && !c.getActivity().startsWith("Fresh")
                && !c.getActivity().startsWith("Speed"));
        Worlds.hopTo(w);
        Time.sleepUntil(() -> Static.getClient().getGameState() != GameState.HOPPING, 10000);
        return 600;
    }

    @Override
    public boolean isBlocking()
    {
        return true;
    }
}
