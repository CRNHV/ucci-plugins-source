package net.devious.plugins.zeahrc.tasks;

import net.devious.plugins.zeahrc.data.Constants;
import net.devious.plugins.zeahrc.data.Locations;
import net.devious.plugins.zeahrc.framework.SessionUpdater;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.Task;

public class GoToBloodAltar extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        return Inventory.contains(c -> c.getName().equalsIgnoreCase(Constants.EssenceFragments))
                && Inventory.contains(c -> c.getName().equalsIgnoreCase(Constants.DarkEssenceBlock))
                && LocalPlayer.get().distanceTo(Locations.BloodAltar) >= 8;
    }

    @Override
    public int execute()
    {
        getSession().setCurrentTask("Go to blood altar");

        Movement.walkTo(Locations.BloodAltar);
        Time.sleepUntil(() -> Movement.getDestination().distanceTo(LocalPlayer.get()) < 3, 1200);

        return 100;
    }
}
