package net.devious.plugins.zeahrc.tasks;

import net.devious.plugins.zeahrc.framework.SessionUpdater;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.plugins.Task;

import java.util.Random;

public class Sleep extends SessionUpdater implements Task
{
    static final Random rng = new Random();

    @Override
    public boolean validate()
    {
        return LocalPlayer.get().isAnimating();
    }

    @Override
    public int execute()
    {
        int animtation = LocalPlayer.get().getAnimation();
        int sleepTime;

        if (animtation == 7201 || animtation == 3873)
        {
            sleepTime = rng.nextInt(10000) + 600;
        }
        else
        {
            sleepTime = rng.nextInt(2400) + 600;
        }

        getSession().setCurrentTask("Sleeping: " + sleepTime + "ms");
        Time.sleep(sleepTime);
        return 1;
    }

    @Override
    public boolean isBlocking()
    {
        return true;
    }
}
