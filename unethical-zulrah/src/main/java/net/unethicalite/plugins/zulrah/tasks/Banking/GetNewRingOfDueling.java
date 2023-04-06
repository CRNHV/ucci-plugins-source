package net.unethicalite.plugins.zulrah.tasks.Banking;

import net.runelite.api.Item;
import net.runelite.api.TileObject;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.framework.SessionUpdater;

public class GetNewRingOfDueling extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        boolean seesBanker = NPCs.getNearest(c -> c.hasAction("Bank")) != null;
        boolean noRingOfDueling = !Equipment.contains(c -> c.getName().contains(Constants.RING_OF_DUELING));

        return seesBanker && noRingOfDueling;
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Getting new ring of dueling");

        TileObject bankChest = TileObjects.getNearest(c -> c.hasAction("Use")
                && c.getName().equalsIgnoreCase("Bank chest"));

        if (bankChest == null)
        {
            return 1200;
        }

        if (!Bank.isOpen())
        {
            bankChest.interact("Use");
            Time.sleepUntil(() -> Bank.isOpen(), 3600);
        }
        Time.sleepTicks(1);
        if (!Inventory.contains(c -> c.getName().startsWith(Constants.RING_OF_DUELING)))
        {
            Item ringOfDueling = Bank.getFirst(c -> c.getName().startsWith(Constants.RING_OF_DUELING) && c.getQuantity() > 0);
            if (ringOfDueling == null)
            {

                return 600;
            }

            System.out.println(ringOfDueling.getName());
            Bank.withdraw(ringOfDueling.getName(), 1, Bank.WithdrawMode.ITEM);
            Time.sleepTicks(1);
            Bank.close();
        }

        if (!Equipment.contains(c -> c.getName().contains(Constants.RING_OF_DUELING)) && Inventory.contains(c -> c.getName().startsWith(Constants.RING_OF_DUELING)))
        {
            Inventory.getFirst(c -> c.getName().startsWith(Constants.RING_OF_DUELING)).interact("Wear");
        }
        return 600;
    }
}
