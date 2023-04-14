package net.devious.plugins.clockworks;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ClockworkPlugin")
public interface ClockworkConfig extends Config
{
    @ConfigItem(
            keyName = "pkWorld",
            name = "pkWorld",
            description = "pkWorld"
    )
    default int pkWorld()
    {
        return 560;
    }
}
