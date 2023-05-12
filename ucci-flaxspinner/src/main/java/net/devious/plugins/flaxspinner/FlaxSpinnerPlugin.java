package net.devious.plugins.flaxspinner;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.devious.plugins.flaxspinner.framework.SessionTask;
import net.devious.plugins.flaxspinner.overlay.BotSession;
import net.devious.plugins.flaxspinner.overlay.SessionOverlay;
import net.devious.plugins.flaxspinner.tasks.*;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.unethicalite.api.plugins.LoopedPlugin;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(name = "Ucci flaxspinner", enabledByDefault = false)
@Slf4j
public class FlaxSpinnerPlugin extends LoopedPlugin
{
    @Inject
    private SessionOverlay statsOverlay;
    @Inject
    private OverlayManager overlayManager;

    @Getter
    private BotSession session;

    private boolean running = false;

    private final SessionTask[] tasks = new SessionTask[]
            {
                    new WalkToTopBank(),
                    new BankBowstring(),
                    new WithdrawFlax(),
                    new WalkToSpinningWheel(),
                    new SpinFlax(),
            };

    @Override
    protected void startUp()
    {
        running = true;
        session = new BotSession();
        session.startTimer();
        overlayManager.add(statsOverlay);
    }

    @Override
    protected void shutDown()
    {
        running = false;
        overlayManager.remove(statsOverlay);
    }

    @Override
    protected int loop()
    {
        if (!running)
        {
            return 600;
        }

        for (SessionTask task : tasks)
        {
            if (task.validate())
            {
                int returnValue = task.execute();
                session.setCurrentTask(task.getCurrentTask());
                return returnValue;
            }
        }

        session.setCurrentTask("Idle");

        return 100;
    }
}
