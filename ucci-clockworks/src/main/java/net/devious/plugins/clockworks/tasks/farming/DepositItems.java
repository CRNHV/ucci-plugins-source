package net.devious.plugins.clockworks.tasks.farming;

import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.runelite.api.widgets.Widget;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.api.widgets.Widgets;

public class DepositItems extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return TileObjects.getNearest(c -> c.getName().equalsIgnoreCase("Bank chest")) != null
                && !Inventory.contains("Steel bar")
                && !Inventory.isEmpty()
                && Bank.isOpen();
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Deposit items");

        Widget depositAll = Widgets.fromId(786474);
        if (depositAll != null && depositAll.isVisible())
        {
            depositAll.interact("Deposit inventory");
        }
        return 600;
    }
}
