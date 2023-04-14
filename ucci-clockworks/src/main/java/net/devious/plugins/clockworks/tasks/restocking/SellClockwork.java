package net.devious.plugins.clockworks.tasks.restocking;

import net.devious.plugins.clockworks.data.Locations;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.GrandExchangeOffer;
import net.runelite.api.GrandExchangeOfferState;
import net.runelite.api.NPC;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.items.GrandExchange;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

import java.util.Optional;

public class SellClockwork extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        Optional<GrandExchangeOffer> clockworkOffer = GrandExchange.getOffers().stream().filter(c -> c.getItemId() == 8792).findFirst();
        boolean isSelling = clockworkOffer.isPresent();
        return (Locations.GE_CENTER.contains(LocalPlayer.get()) && Inventory.contains("Clockwork")) || isSelling;
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Selling clockwork");

        if (!GrandExchange.isOpen())
        {
            NPCs.getNearest("Grand Exchange Clerk").interact("Exchange");
            return 600;
        }

        Optional<GrandExchangeOffer> clockworkOffer = GrandExchange.getOffers().stream().filter(c -> c.getItemId() == 8792).findFirst();
        if (clockworkOffer.isEmpty())
        {
            GrandExchange.sell(8792, Inventory.getCount(true, "Clockwork"), 700);
            return 600;
        }
        else
        {
            GrandExchangeOffer offer = clockworkOffer.get();
            if (offer.getState() == GrandExchangeOfferState.SOLD)
            {
                GrandExchange.collect(true);
            }

            OpenBank();
        }

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
}
