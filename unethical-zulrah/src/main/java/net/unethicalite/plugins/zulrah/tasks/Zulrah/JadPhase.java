package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.Prayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Projectiles;
import net.unethicalite.api.widgets.Prayers;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

public class JadPhase extends ZulrahTask
{
    private Prayer current;

    @Override
    public boolean validate()
    {
        if (getZulrahCycle() != null && getZulrahCycle().isJad())
        {
            if (Projectiles.getAll(e -> e.getId() == Constants.PROJECTILE_RANGED_ID).size() > 0 && !Prayers.isEnabled(Prayer.PROTECT_FROM_MAGIC))
            {
                return true;
            }
            else if (Projectiles.getAll(e -> e.getId() == Constants.PROJECTILE_MAGE_ID).size() > 0 && !Prayers.isEnabled(Prayer.PROTECT_FROM_MISSILES))
            {
                return true;
            }
            else return current == null;
        }
        else
        {
            current = null;
        }

        return false;
    }

    @Override
    public int execute()
    {
        updateTask("Jad phase");

        if (current == null)
        {
            current = getZulrahCycle().getZulrahType().getDefensivePrayer();
        }

        if (!Prayers.isEnabled(getZulrahCycle().getZulrahType().getOffensivePrayer()))
        {
            Prayers.toggle(getZulrahCycle().getZulrahType().getOffensivePrayer());
            Time.sleepUntil(() -> Prayers.isEnabled(getZulrahCycle().getZulrahType().getOffensivePrayer()), 500);
        }

        if (Projectiles.getAll(e -> e.getId() == Constants.PROJECTILE_RANGED_ID).size() > 0)
        {
            current = Prayer.PROTECT_FROM_MAGIC;
        }

        if (Projectiles.getAll(e -> e.getId() == Constants.PROJECTILE_MAGE_ID).size() > 0)
        {
            current = Prayer.PROTECT_FROM_MISSILES;
        }

        if (!Prayers.isEnabled(current))
        {
            Prayers.toggle(current);
            Time.sleepUntil(() -> Prayers.isEnabled(current), 1000);
        }

        return 1000;
    }
}
