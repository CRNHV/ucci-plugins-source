package net.devious.plugins.template.framework;

import lombok.Setter;
import net.devious.plugins.template.overlay.BotSession;
import net.unethicalite.api.plugins.Task;

public abstract class SessionTask implements Task
{
    @Setter
    private BotSession session;

    protected void setCurrentTask(String task)
    {
        session.setCurrentTask(task);
    }
}
