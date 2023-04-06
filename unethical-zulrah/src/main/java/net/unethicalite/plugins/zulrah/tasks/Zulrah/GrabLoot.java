package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.TileItem;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Prayers;
import net.unethicalite.client.Static;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

import java.util.List;

import static net.unethicalite.plugins.zulrah.data.Constants.LOOT_TABLE;

public class GrabLoot extends ZulrahTask
{
    @Override
    public boolean validate()
    {
        List<TileItem> groundItems = TileItems.getAll(i -> LOOT_TABLE.contains(i.getName()));
        return !groundItems.isEmpty() && Static.getClient().isInInstancedRegion();
    }

    @Override
    public int execute()
    {
        updateTask("Grabbing loot");

        if (Prayers.anyActive())
        {
            Prayers.disableAll();
        }

        List<TileItem> groundItems = TileItems.getAll(i -> LOOT_TABLE.contains(i.getName()));
        int itemCount = groundItems.size();
        int freeInvSpots = Inventory.getFreeSlots();

        int plusAmount = Inventory.contains(c -> c.getName() == (Constants.TOXIC_BLOWPIPE_NAME)) ? 1 : 0;
        if (itemCount >= freeInvSpots)
        {
            int freeInvNeeded = itemCount + plusAmount - freeInvSpots;
            for (int i = 0; i < freeInvNeeded; i++)
            {
                Inventory.getFirst(Constants.KARAMBWAN).interact("Eat");
                Time.sleepTicks(1);
            }
        }

        for (TileItem item : groundItems)
        {
            item.interact("Take");
            Time.sleep(50, 80);
        }

        return 0;
    }
}
