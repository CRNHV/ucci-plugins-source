package net.unethicalite.plugins.zulrah.tasks.Banking;

import lombok.extern.slf4j.Slf4j;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.plugins.zulrah.data.GearSetup;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;
import net.unethicalite.plugins.zulrah.framework.SessionUpdater;

@Slf4j
public class EquipMagicGear extends SessionUpdater implements Task
{
    @Override
    public boolean validate()
    {
        boolean seesBanker = NPCs.getNearest(c -> c.hasAction("Bank")) != null;
        GearSetup mageGear = ZulrahCycle.GREEN_CENTER_E.getZulrahType().getSetup();

        return seesBanker && mageGear.anyUnequipped();
    }

    @Override
    public int execute()
    {
        log.info("Equipping magic gear");
        updateTask("Equipping magic gear");

        GearSetup mageGear = ZulrahCycle.GREEN_CENTER_E.getZulrahType().getSetup();
        mageGear.switchGear(60);
        Time.sleepUntil(() -> !mageGear.anyUnequipped(), 1200);
        return 600;
    }
}
