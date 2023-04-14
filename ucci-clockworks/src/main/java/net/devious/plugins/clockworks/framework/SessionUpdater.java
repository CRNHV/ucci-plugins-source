package net.devious.plugins.clockworks.framework;

import lombok.Getter;
import lombok.Setter;
import net.devious.plugins.clockworks.ClockworkConfig;
import net.devious.plugins.clockworks.ClockworkPlugin;
import net.devious.plugins.clockworks.overlay.BotSession;

@Getter
public abstract class SessionUpdater
{

    @Setter
    private BotSession session;
    @Setter
    private ClockworkPlugin plugin;

    @Setter
    @Getter
    private ClockworkConfig config;


}
