package net.unethicalite.plugins.zulrah.tasks.Zulrah;

import net.unethicalite.api.game.Combat;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;
import net.unethicalite.plugins.zulrah.framework.ZulrahTask;

public class SetLongRange extends ZulrahTask
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
        boolean isCorrectCycle = cycle == ZulrahCycle.TANZ_SOUTH_W;
        boolean isOnRapid = Combat.getAttackStyle() != Combat.AttackStyle.FOURTH;
        boolean equipTbp = Equipment.contains("Toxic blowpipe");

        return ismagic && isCorrectCycle && isOnRapid && equipTbp;
    }

    @Override
    public int execute()
    {
        updateTask("Setting long range");

        if (Combat.getAttackStyle() != Combat.AttackStyle.FOURTH)
        {
            Combat.setAttackStyle(Combat.AttackStyle.FOURTH);
        }

        return 600;
    }
}