package net.unethicalite.plugins.zulrah.tasks.Banking;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.NPC;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.client.Static;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.framework.SessionUpdater;

@Slf4j
public class GoToBank extends SessionUpdater implements Task
{

    private final WorldPoint FEROX_BANK_SPOT = new WorldPoint(3130, 3631, 0);
    private final WorldPoint FEROX_TELE_SPOT = new WorldPoint(3151, 3634, 0);

    @Override
    public boolean validate()
    {
        boolean isAtBank = LocalPlayer.get().distanceTo(FEROX_BANK_SPOT) <= 8 && NPCs.getNearest(c -> c.hasAction("Bank")) != null;

        boolean atZulrah = Static.getClient().isInInstancedRegion();
        boolean hasNoMoreFood = Inventory.getCount(c -> c.hasAction("Eat")) < 10;
        boolean hasNoMoreAntiV = Inventory.getCount(c -> c.getName().contains("Anti-venom")) == 0;
        boolean hasNoMorePrayer = Inventory.getCount(c -> c.getName().contains("Prayer")) == 0;

        boolean result =
                ((hasNoMoreFood || hasNoMorePrayer || hasNoMoreAntiV)
                        && !atZulrah
                        && !isAtBank) || Players.getLocal().distanceTo(FEROX_TELE_SPOT) < 4;

        return result;
    }

    @Override
    public int execute()
    {
        log.info("Going to bank");
        updateTask("Going to bank");

        boolean isClosetoFerox = LocalPlayer.get().distanceTo(FEROX_BANK_SPOT) < 30;
        if (!isClosetoFerox)
        {
            log.info("Teleporting to ferox");
            // Teleport to ferox
            if (!Equipment.contains(c -> c.getName().startsWith(Constants.RING_OF_DUELING)) && Inventory.contains(c -> c.getName().startsWith(Constants.RING_OF_DUELING)))
            {
                Inventory
                        .getFirst(c -> c.getName().startsWith(Constants.RING_OF_DUELING))
                        .interact("Wear");
                Time.sleepUntil(() -> Equipment.fromSlot(EquipmentInventorySlot.RING).hasAction(Constants.FEROX_ENCLAVE), 1200);
            }

            if (Equipment.contains(c -> c.getName().startsWith(Constants.RING_OF_DUELING)))
            {
                Equipment.fromSlot(EquipmentInventorySlot.RING).interact(Constants.FEROX_ENCLAVE);
                Time.sleepTicks(3);
            }
            return 600;
        }
        else
        {
            log.info("Walking to banker");
            // Walk untill we see the banker
            NPC banker = NPCs.getNearest(c -> c.hasAction("Bank"));
            if (banker == null)
            {
                Movement.walkTo(FEROX_BANK_SPOT);
                Time.sleepUntil(() -> !LocalPlayer.get().isMoving() || NPCs.getNearest(c -> c.hasAction("Bank")) != null, 4800);
                return 300;
            }

            TileObject bankChest = TileObjects.getNearest(c -> c.hasAction("Use") && c.getName().equalsIgnoreCase("Bank chest"));
            if (bankChest == null)
            {
                banker.interact("Bank");
            }
            else
            {
                bankChest.interact("Use");
            }
            Time.sleepUntil(() -> Bank.isOpen(), 4800);
        }

        return 600;
    }
}
