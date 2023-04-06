package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.unethicalite.api.commons.Time;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

import java.util.Random;

public class SwitchGear extends ZulrahTask
{
    private static final Random rng = new Random();

    @Override
    public boolean validate()
    {
        return getZulrahCycle() != null && getZulrahCycle().getZulrahType().getSetup().anyUnequipped();
    }

    @Override
    public int execute()
    {
        updateTask("Switching gear");

        getZulrahCycle().getZulrahType().getSetup().switchGear(rng.nextInt(150) + 100);
        Time.sleep(100, 255);
        return 100;
    }

    @Override
    public boolean isBlocking()
    {
        return false;
    }
}
