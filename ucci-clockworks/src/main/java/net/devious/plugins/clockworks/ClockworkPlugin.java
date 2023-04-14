package net.devious.plugins.clockworks;


import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.devious.plugins.clockworks.framework.BotMemory;
import net.devious.plugins.clockworks.framework.MemoryConstants;
import net.devious.plugins.clockworks.framework.SessionUpdater;
import net.devious.plugins.clockworks.overlay.BotSession;
import net.devious.plugins.clockworks.overlay.SessionOverlay;
import net.devious.plugins.clockworks.tasks.farming.*;
import net.devious.plugins.clockworks.tasks.restocking.*;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.unethicalite.api.plugins.Plugins;
import net.unethicalite.api.plugins.Task;
import net.unethicalite.api.plugins.TaskPlugin;
import net.unethicalite.client.Static;
import org.pf4j.Extension;

import javax.swing.*;

@Extension
@PluginDescriptor(name = "Ucci clockworks", enabledByDefault = false)
@Slf4j
public class ClockworkPlugin extends TaskPlugin
{
    @Inject
    private SessionOverlay statsOverlay;

    @Inject
    private OverlayManager overlayManager;

    private boolean running;

    private BotSession session;

    private final Task[] farmingTasks = new Task[]
            {
                    new TeleportToCamelot(),
                    new HopToPvpWorld(),
                    new OpenBank(),
                    new DepositItems(),
                    new WithdrawItems(),
                    new TeleportToHouse(),
                    new MakeClockwork(),
                    new TeleportToBank(),
            };

    private final Task[] restockTasks = new Task[]
            {

                    new WithdrawTeleTab(),
                    new HopToNormalWorld(),
                    new TeleportToVarrock(),
                    new WalkToGe(),
                    new WithdrawClockworks(),
                    new SellClockwork(),
                    new BuySteelBar(),
            };


    @Inject
    private ClockworkConfig config;

    @SneakyThrows
    @Override
    protected void startUp()
    {
        running = true;
        session = new BotSession();
        session.setCurrentTask("Starting up");

        session.startTimer();

        overlayManager.add(statsOverlay);

        for (Task task : farmingTasks)
        {
            if (task instanceof SessionUpdater)
            {
                SessionUpdater sessionTask = (SessionUpdater) task;
                sessionTask.setSession(session);
                sessionTask.setConfig(config);
            }
        }

        for (Task task : restockTasks)
        {
            if (task instanceof SessionUpdater)
            {
                SessionUpdater sessionTask = (SessionUpdater) task;
                sessionTask.setSession(session);
                sessionTask.setConfig(config);
            }
        }
    }

    @Override
    public void stop()
    {
        running = false;
        session = new BotSession();
        overlayManager.remove(statsOverlay);
        BotMemory.wipeMemory();
    }

    public Task[] getTasks()
    {
        if (!running || Static.getClient().getGameState() == GameState.HOPPING)
        {
            return new Task[0];
        }

        if (BotMemory.getBool(MemoryConstants.BL_NEEDS_RESTOCK) == false)
        {
            return farmingTasks;
        }

        if (BotMemory.getBool(MemoryConstants.BL_NEEDS_RESTOCK) == true)
        {
            return restockTasks;
        }

        return new Task[0];
    }

    @Provides
    ClockworkConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ClockworkConfig.class);
    }

    @Subscribe
    private void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() == GameState.LOGIN_SCREEN)
        {
            SwingUtilities.invokeLater(() -> Plugins.stopPlugin(this));
        }
    }

    public BotSession getSession()
    {
        return this.session;
    }
}

