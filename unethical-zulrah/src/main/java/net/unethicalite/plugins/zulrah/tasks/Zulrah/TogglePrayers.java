package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import com.google.inject.Inject;
import net.runelite.api.NPC;
import net.runelite.api.Prayer;
import net.runelite.api.Skill;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.widgets.Prayers;
import net.unethicalite.plugins.zulrah.UnethicalZulrahConfig;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

import static net.unethicalite.plugins.zulrah.UnethicalZulrahPlugin.atZulrah;

public class TogglePrayers extends ZulrahTask
{
    private boolean enable = false;

    @Inject
    private UnethicalZulrahConfig config;

    @Override
    public boolean validate()
    {
        NPC zulrah = NPCs.getNearest(Constants.ZULRAH_NAME);
        if (!atZulrah())
        {
            if (enable)
            {
                enable = false;
            }
        }

        if (getZulrahCycle() != null && !enable && !getZulrahCycle().equals(ZulrahCycle.INITIAL))
        {
            enable = true;
        }

        return zulrah != null
                && zulrah.getHealthRatio() != 0
                && atZulrah()
                && (canToggleOffensive() || canToggleDefensive() || canTurnOffPrayer());
    }

    @Override
    public int execute()
    {
        updateTask("Toggle prayers");

        if (canToggleDefensive() && getZulrahCycle() != null)
        {
            Prayer defensive = getZulrahCycle().getZulrahType().getDefensivePrayer();
            Prayers.toggle(defensive);
            Time.sleepUntil(() -> Prayers.isEnabled(defensive), 1200);
            return 300;
        }

        if (canToggleOffensive() && getZulrahCycle() != null)
        {
            Prayer offensive = getZulrahCycle().getZulrahType().getOffensivePrayer();
            Prayers.toggle(offensive);
            Time.sleepUntil(() -> Prayers.isEnabled(offensive), 1200);
            return 300;
        }

        if (canTurnOffPrayer() && getZulrahCycle() != null)
        {
            if (Prayers.isEnabled(Prayer.PROTECT_FROM_MAGIC))
            {
                Prayers.toggle(Prayer.PROTECT_FROM_MAGIC);
                Time.sleepUntil(() -> !Prayers.isEnabled(Prayer.PROTECT_FROM_MAGIC), 1200);
            }

            if (Prayers.isEnabled(Prayer.PROTECT_FROM_MISSILES))
            {
                Prayers.toggle(Prayer.PROTECT_FROM_MISSILES);
                Time.sleepUntil(() -> !Prayers.isEnabled(Prayer.PROTECT_FROM_MISSILES), 1200);
            }
            return 300;
        }

        return 100;
    }

    private boolean canToggleOffensive()
    {
        return getZulrahCycle() != null
                && atZulrah()
                && Prayers.getPoints() > 0
                && Skills.getLevel(Skill.PRAYER) >= 45
                && !Prayers.isEnabled(getZulrahCycle().getZulrahType().getOffensivePrayer());
    }

    private boolean canTurnOffPrayer()
    {
        return getZulrahCycle().isMelee();
    }

    private boolean canToggleDefensive()
    {
        return getZulrahCycle() != null
                && enable
                && atZulrah()
                && !getZulrahCycle().isJad()
                && !getZulrahCycle().isMelee()
                && Prayers.getPoints() > 0
                && !Prayers.isEnabled(getZulrahCycle().getZulrahType().getDefensivePrayer());
    }

    @Override
    public boolean isBlocking()
    {
        return false;
    }
}
