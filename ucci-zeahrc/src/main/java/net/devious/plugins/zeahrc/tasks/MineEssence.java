package net.devious.plugins.zeahrc.tasks;

import net.devious.plugins.zeahrc.data.Constants;
import net.devious.plugins.zeahrc.framework.SessionUpdater;
import net.runelite.api.TileObject;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class MineEssence extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        TileObject runeStone = TileObjects.getNearest(Constants.DenseRunestone);

        return !Inventory.isFull()
                && !Inventory.contains(Constants.DarkEssenceBlock)
                && !LocalPlayer.get().isAnimating()
                && runeStone != null && runeStone.distanceTo(LocalPlayer.get()) < 15;
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Mining essence");

        TileObject runeStone = TileObjects.getNearest(c -> c.hasAction("Chip")
                && c.getName().equalsIgnoreCase(Constants.DenseRunestone));

        if (runeStone == null)
        {
            System.out.println("Runestone == null!");
            return 600;
        }

        runeStone.interact("Chip");
        Time.sleepUntil(() -> !LocalPlayer.get().isMoving(), 9600);
        return 600;
    }

    @Override
    public boolean isBlocking()
    {
        return true;
    }
}
