package net.unethicalite.plugins.zulrah.data.phases;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.runelite.api.Prayer;
import net.unethicalite.plugins.zulrah.data.GearSetup;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public enum ZulrahType
{
    RANGE(2042),
    MELEE(2043),
    MAGIC(2044),
    JAD_MAGIC_FIRST(2042),
    JAD_RANGE_FIRST(2042);

    // This is very ugly, don't do this
    public static Prayer rangePray;
    public static Prayer magePray;

    private final int id;
    @Setter
    private GearSetup setup;

    public static void setRangedMeleePhaseGear(GearSetup gearSetup)
    {
        RANGE.setSetup(gearSetup);
        MELEE.setSetup(gearSetup);
        JAD_MAGIC_FIRST.setSetup(gearSetup);
        JAD_RANGE_FIRST.setSetup(gearSetup);
        magePray = Prayer.MYSTIC_MIGHT;
    }

    public static void setMagePhaseGear(GearSetup gearSetup, boolean useRigour)
    {
        MAGIC.setSetup(gearSetup);
        rangePray = useRigour ? Prayer.RIGOUR : Prayer.EAGLE_EYE;
    }

    public int id()
    {
        return id;
    }

    public Prayer getOffensivePrayer()
    {
        if (this == ZulrahType.MAGIC)
        {
            return rangePray;
        }

        return magePray;
    }

    public Prayer getDefensivePrayer()
    {
        switch (this)
        {
            case MAGIC:
            case JAD_MAGIC_FIRST:
                return Prayer.PROTECT_FROM_MAGIC;

            default:
                return Prayer.PROTECT_FROM_MISSILES;
        }
    }
}
