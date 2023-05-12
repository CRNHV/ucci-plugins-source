package net.devious.plugins.banwatch;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("uccibanwatch")
public interface UcciBanwatchPluginConfig extends Config
{
    @ConfigItem(
            keyName = "discordWebhook",
            name = "discordWebhook",
            description = "discordWebhook",
            position = 0
    )
    default String webhookUrl()
    {
        return "discordWebhook";
    }

    @ConfigItem(
            keyName = "quitWhenBanned",
            name = "quitWhenBanned",
            description = "quitWhenBanned",
            position = 0
    )
    default boolean quitWhenBanned()
    {
        return true;
    }
}
