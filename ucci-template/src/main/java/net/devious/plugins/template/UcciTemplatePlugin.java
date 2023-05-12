package net.devious.plugins.template;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.devious.plugins.banwatch.UcciTemplatePluginConfig;
import net.devious.plugins.template.overlay.BotSession;
import net.devious.plugins.template.overlay.SessionOverlay;
import net.devious.plugins.template.tasks.TemplateTask;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.api.plugins.TaskPlugin;
import org.pf4j.Extension;

@PluginDescriptor(name = "Ucci template", enabledByDefault = false)
@Extension
@Slf4j
public class UcciTemplatePlugin extends TaskPlugin
{
    @Getter
    private BotSession session;
    @Inject
    private UcciTemplatePluginConfig config;

    @Inject
    private SessionOverlay sessionOverlay;
    @Inject
    private OverlayManager overlayManager;

    private boolean isRunning = false;

    private final TemplateTask[] tasks = new TemplateTask[]
            {
                    new TemplateTask()
            };

    @Override
    protected void startUp()
    {
        isRunning = true;

        session = new BotSession();
        overlayManager.add(sessionOverlay);

        for(TemplateTask task : tasks)
        {
            task.setSession(this.session);
        }
    }

    @Override
    protected void shutDown()
    {
        isRunning = false;

        overlayManager.remove(sessionOverlay);
    }

    @Override
    public Task[] getTasks()
    {
        if (!isRunning)
        {
            return new Task[0];
        }

        return tasks;
    }

    @Provides
    public UcciTemplatePluginConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(UcciTemplatePluginConfig.class);
    }
}
