package net.unethicalite.plugins.zulrah.tasks.Zulrah;


import net.runelite.api.NPC;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.game.Combat;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.plugins.zulrah.data.Constants;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

public class SetRapid extends ZulrahTask
{
    @Override
    public boolean validate()
    {
        ZulrahCycle cycle = getZulrahCycle();
        if (cycle == null)
        {
            return false;
        }

        boolean ismagic = cycle.isMagic();
        boolean isCorrectCycle = cycle != ZulrahCycle.TANZ_SOUTH_W;
        boolean isOnRapid = Combat.getAttackStyle() == Combat.AttackStyle.FOURTH;
        boolean equipTbp = Equipment.contains("Toxic blowpipe");

        return ismagic && isCorrectCycle && isOnRapid && equipTbp;
    }

    @Override
    public int execute()
    {
        updateTask("Setting to rapid");

        if (Combat.getAttackStyle() != Combat.AttackStyle.SECOND)
        {
            Combat.setAttackStyle(Combat.AttackStyle.SECOND);
        }

        Time.sleepUntil(() -> Combat.isSpecEnabled(), 600);

        NPC zulrah = NPCs.getNearest(x -> x.getName().equals(Constants.ZULRAH_NAME) && x.getHealthRatio() != 0);
        if (zulrah != null)
        {
            zulrah.interact("Attack");
        }

        return 600;
    }
}
