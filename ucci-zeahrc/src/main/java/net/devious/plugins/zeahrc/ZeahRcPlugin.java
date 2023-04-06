package net.devious.plugins.zeahrc;


import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.devious.plugins.zeahrc.framework.SessionUpdater;
import net.devious.plugins.zeahrc.overlay.ZeahRcSession;
import net.devious.plugins.zeahrc.overlay.ZeahRcStatsOverlay;
import net.devious.plugins.zeahrc.tasks.*;
import net.runelite.api.GameState;
import net.runelite.api.events.ActorDeath;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.plugins.Plugins;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.api.plugins.TaskPlugin;
import org.pf4j.Extension;

import javax.swing.*;

@Extension
@PluginDescriptor(name = "Ucci ZeahRc", enabledByDefault = false)
@Slf4j
public class ZeahRcPlugin extends TaskPlugin
{
    @Inject
    private ZeahRcStatsOverlay statsOverlay;

    @Inject
    private OverlayManager overlayManager;

    private boolean running;

    private ZeahRcSession session;

    private Task[] tasks;

    @SneakyThrows
    @Override
    protected void startUp()
    {
        tasks = new Task[]
        {
                new ActivateEssence(),
                new Sleep(),
                new GoToRunestone(),
                new MineEssence(),
                new GoToDarkAltar(),
                new UseDarkAltar(),
                new ChipEssence(),
                new GoToBloodAltar(),
                new UseBloodAltar(),
        };

        running = true;
        session = new ZeahRcSession();
        session.setCurrentTask("Starting up");

        session.startTimer();

        for (Task t : tasks)
        {
            if (t instanceof SessionUpdater)
            {
                SessionUpdater sessionTask = (SessionUpdater) t;
                sessionTask.setSession(this.getSession());
                sessionTask.setPlugin(this);
            }
        }

        overlayManager.add(statsOverlay);
    }

    @Override
    public void stop()
    {
        running = false;
        session = new ZeahRcSession();

        overlayManager.remove(statsOverlay);
    }

    public Task[] getTasks()
    {
        if (running)
        {
            return tasks;
        }

        return new Task[0];
    }

    @Subscribe
    private void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() == GameState.LOGIN_SCREEN)
        {
            SwingUtilities.invokeLater(() -> Plugins.stopPlugin(this));
        }
    }

    @Subscribe
    private void onActorDeath(ActorDeath event)
    {
        if (event.getActor() == Players.getLocal())
        {
            SwingUtilities.invokeLater(() -> Plugins.stopPlugin(this));
        }
    }


    public ZeahRcSession getSession()
    {
        return this.session;
    }
}

