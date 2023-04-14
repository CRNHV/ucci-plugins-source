package net.devious.plugins.clockworks.tasks.restocking;

import net.devious.plugins.clockworks.data.ItemIds;
import net.devious.plugins.clockworks.framework.BotMemory;
import net.devious.plugins.clockworks.framework.MemoryConstants;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.GrandExchangeOffer;
import net.runelite.api.GrandExchangeOfferState;
import net.runelite.api.NPC;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.GrandExchange;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

import java.util.List;
import java.util.stream.Collectors;

public class BuySteelBar extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Inventory.getCount(true, "Coins") > 100000 && BotMemory.getBool(MemoryConstants.BL_NEEDS_STEELBAR);
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Buying steel bar");

        if (!GrandExchange.isOpen())
        {
            NPC clerk = NPCs.getNearest("Grand Exchange Clerk");
            if (clerk == null)
            {
                return 600;
            }

            clerk.interact("Exchange");
            return 600;
        }

        List<GrandExchangeOffer> geOffers = GrandExchange
                .getOffers()
                .stream().filter(c -> c.getItemId() == ItemIds.STEELBAR)
                .collect(Collectors.toList());

        for (GrandExchangeOffer geOffer : geOffers)
        {
            if (geOffer.getItemId() == ItemIds.STEELBAR && geOffer.getState() == GrandExchangeOfferState.BOUGHT)
            {
                GrandExchange.collect(true);
                Time.sleepTick();
                BotMemory.setBool(MemoryConstants.BL_NEEDS_STEELBAR, false);
                BotMemory.setBool(MemoryConstants.BL_NEEDS_RESTOCK, false);
                OpenBank();
                return 600;
            }

            if (geOffer.getItemId() == ItemIds.STEELBAR && geOffer.getState() == GrandExchangeOfferState.BUYING)
            {
                return 600;
            }
        }

        if (geOffers.isEmpty() && BotMemory.getBool(MemoryConstants.BL_NEEDS_STEELBAR) == true)
        {
            int quantityToBuy = Inventory.getCount(true, "Coins") / 450;
            if (quantityToBuy > 2500)
            {
                quantityToBuy = 2500;
            }
            GrandExchange.buy(ItemIds.STEELBAR, quantityToBuy, 450);
        }

        Time.sleepTick();
        return 600;
    }

    private void OpenBank()
    {
        NPC banker = NPCs.getNearest(c -> c.hasAction("Bank"));
        if (banker != null)
        {
            banker.interact("Bank");
        }
    }

    @Override
    public boolean isBlocking()
    {
        return true;
    }
}
