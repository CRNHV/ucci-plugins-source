package net.unethicalite.plugins.zulrah.tasks.Banking;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Item;
import net.runelite.api.TileObject;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.plugins.zulrah.data.GearSetup;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;
import net.unethicalite.plugins.zulrah.framework.SessionUpdater;

import java.util.List;

@Slf4j
public class DepositIntoBank extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        boolean seesBanker = NPCs.getNearest(c -> c.hasAction("Bank")) != null;
        GearSetup mageGear = ZulrahCycle.GREEN_CENTER_E.getZulrahType().getSetup();
        GearSetup rangeGear = ZulrahCycle.TANZ_CENTER_E.getZulrahType().getSetup();

        int freeSpotsNeeded = 28 - rangeGear.getItems().size();
        int freeSpots = Inventory.getFreeSlots();

        return seesBanker && !mageGear.anyUnequipped() && freeSpots != freeSpotsNeeded && !Inventory.isFull();
    }

    @Override
    public int execute()
    {
        log.info("Depositting items into bank");
        updateTask("Depositting items");

        TileObject bankChest = TileObjects.getNearest(c -> c.hasAction("Use") && c.getName().equalsIgnoreCase("Bank chest"));

        if (bankChest == null)
        {
            return 600;
        }

        if (!Bank.isOpen())
        {
            bankChest.interact("Use");
            Time.sleepUntil(() -> Bank.isOpen(), 1200);
            return 300;
        }

        GearSetup rangeGear = ZulrahCycle.TANZ_CENTER_E.getZulrahType().getSetup();
        List<Item> invItems = Inventory.getAll();
        for (Item invItem : invItems)
        {
            if (invItem == null)
            {
                continue;
            }

            if (!rangeGear.hasItem(invItem.getName()))
            {
                if (Inventory.contains(invItem.getName()) && Bank.isOpen())
                {
                    Bank.depositAll(invItem.getName());
                }
                Time.sleep(90, 160);
            }
        }
        return 600;
    }
}
