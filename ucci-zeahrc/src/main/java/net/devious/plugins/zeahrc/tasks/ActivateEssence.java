package net.devious.plugins.zeahrc.tasks;

import net.devious.plugins.zeahrc.framework.SessionUpdater;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.plugins.Task;

public class ActivateEssence extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Inventory.contains("Blood essence") && !Inventory.contains("Blood essence (active)");
    }

    @Override
    public int execute()
    {
        Inventory.getFirst("Blood essence").interact("Activate");
        return 300;
    }
}
