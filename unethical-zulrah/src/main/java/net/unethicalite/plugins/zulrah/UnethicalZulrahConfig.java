package net.unethicalite.plugins.zulrah;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("unethicalzulrah")
public interface UnethicalZulrahConfig extends Config
{
    @ConfigItem(
            keyName = "rangeGear",
            name = "Ranged gear names",
            description = ""
    )
    default String rangeGearNames()
    {
        return "Ancient chaps,Ancient d'hide body,Amulet of fury,Toxic blowpipe,Ava's accumulator";
    }

    @ConfigItem(
            keyName = "mageGear",
            name = "Mage gear names",
            description = "Ahrim's robetop,Ahrim's robeskirt,Trident of the swamp,Book of darkness"
    )
    default String mageGearNames()
    {
        return "Ahrim's robetop,Ahrim's robeskirt,Trident of the swamp,Occult necklace,Saradomin cape,Barrows gloves,Book of darkness";
    }

    @ConfigItem(
            keyName = "useRigour",
            name = "useRigour",
            description = "useRigour"
    )
    default boolean useRigour()
    {
        return false;
    }

    @ConfigItem(
            keyName = "webApiUrl",
            name = "webApiUrl",
            description = "webApiUrl"
    )
    default String webApiUrl()
    {
        return "bots.hoeving.net";
    }

}
