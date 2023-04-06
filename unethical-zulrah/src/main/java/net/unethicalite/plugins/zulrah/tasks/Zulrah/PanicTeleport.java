package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.EquipmentInventorySlot;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.game.Combat;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.client.Static;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

public class PanicTeleport extends ZulrahTask
{
    @Override
    public boolean validate()
    {
        return Combat.getCurrentHealth() < 45
                && Inventory.getFirst(c -> c.hasAction("Eat")) == null
                && Static.getClient().isInInstancedRegion();
    }

    @Override
    public int execute()
    {
        updateTask("Panic teleporting");

        Inventory.getFirst(c -> c.getName().startsWith(Constants.RING_OF_DUELING)).interact("Wear");
        Time.sleepTick();
        Equipment.fromSlot(EquipmentInventorySlot.RING).interact(Constants.FEROX_ENCLAVE);
        return 200;
    }
}
