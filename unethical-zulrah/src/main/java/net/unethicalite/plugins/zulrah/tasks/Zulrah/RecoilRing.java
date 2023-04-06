package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.Item;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.client.Static;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

import java.util.function.Predicate;

public class RecoilRing extends ZulrahTask
{
    private static final Predicate<Item> RECOIL_PREDICATE = i -> i.getName().contains("Ring of recoil")
            || i.getName().contains("Ring of suffering");

    @Override
    public boolean validate()
    {
        return !Equipment.contains(RECOIL_PREDICATE)
                && Inventory.contains(RECOIL_PREDICATE)
                && Static.getClient().isInInstancedRegion()
                && Inventory.getFirst(c -> c.hasAction("Eat")) != null;
    }

    @Override
    public int execute()
    {
        updateTask("Equipping recoil");

        Time.sleep(100, 600);
        Inventory.getFirst(RECOIL_PREDICATE).interact("Wear");
        return 550;
    }

    @Override
    public boolean isBlocking()
    {
        return false;
    }
}
