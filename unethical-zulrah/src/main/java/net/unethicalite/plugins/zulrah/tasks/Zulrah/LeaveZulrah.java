package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.TileItem;
import net.runelite.api.TileObject;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

import java.util.List;

import static net.unethicalite.plugins.zulrah.data.Constants.LOOT_TABLE;

@Slf4j
public class LeaveZulrah extends ZulrahTask
{
    @Override
    public boolean validate()
    {
        List<TileItem> groundItems = TileItems.getAll(i -> LOOT_TABLE.contains(i.getName()));
        return groundItems.isEmpty() && TileObjects.getNearest("Zul-Andra teleport") != null;
    }

    @Override
    public int execute()
    {
        updateTask("Leaving zulrah");
        TileObject zulrahScroll = TileObjects.getNearest("Zul-Andra teleport");

        if (zulrahScroll == null)
        {
            log.warn("Scroll not found");
            return 600;
        }

        if (Dialog.isOpen() && Dialog.chooseOption("Yes"))
        {
            Dialog.chooseOption("Yes");
        }
        else
        {
            zulrahScroll.interact("Read");
        }
        return 1200;
    }
}
