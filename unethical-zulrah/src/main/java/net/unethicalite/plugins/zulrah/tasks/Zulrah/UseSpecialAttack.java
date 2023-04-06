package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.game.Combat;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.client.Static;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

public class UseSpecialAttack extends ZulrahTask
{
    @Override
    public boolean validate()
    {
        if (getZulrahCycle() == null || !Static.getClient().isInInstancedRegion())
        {
            return false;
        }

        if (!Equipment.contains("Toxic blowpipe"))
        {
            return false;
        }

        int specAmount = Combat.getSpecEnergy();
        boolean canAttack =
                NPCs.getNearest(x -> x.getName().contains(Constants.ZULRAH_NAME) && x.getHealthRatio() != 0) != null
                        && NPCs.getNearest(Constants.ZULRAH_NAME).getAnimation() != Constants.DISAPPEAR_ANIMATION
                        && NPCs.getNearest(Constants.ZULRAH_NAME).getAnimation() != Constants.APPEAR_ANIMATION;

        return specAmount >= 50
                && Combat.getCurrentHealth() <= Skills.getLevel(Skill.HITPOINTS) - 25
                && getZulrahCycle().isMagic()
                && canAttack;
    }

    @Override
    public int execute()
    {
        updateTask("Use special attack");

        if (!Combat.isSpecEnabled())
        {
            Combat.toggleSpec();
            Time.sleepTick();
            return 300;
        }

        NPC zulrah = NPCs.getNearest(x -> x.getName().contains(Constants.ZULRAH_NAME) && x.getHealthRatio() != 0);
        if (zulrah != null)
        {
            zulrah.interact("Attack");
        }

        return 300;
    }
}
