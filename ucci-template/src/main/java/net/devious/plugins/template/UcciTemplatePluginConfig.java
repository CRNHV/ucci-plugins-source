package net.devious.plugins.banwatch;

import net.devious.plugins.template.data.CoolState;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("uccibanwatch")
public interface UcciTemplatePluginConfig extends Config
{
    @ConfigItem(
            keyName = "stringInput",
            name = "String input",
            description = "A string of characters",
            position = 0
    )
    default String stringInput()
    {
        return "You can input strings here";
    }

    @ConfigItem(
            keyName = "enumSelectionBox",
            name = "Enum selectin",
            description = "Are we cool?",
            position = 0
    )
    default CoolState enumSelection()
    {
        return CoolState.COOL;
    }
}
