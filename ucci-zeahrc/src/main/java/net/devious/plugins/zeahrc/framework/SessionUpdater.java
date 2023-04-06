package net.devious.plugins.zeahrc.framework;

import lombok.Getter;
import lombok.Setter;
import net.devious.plugins.zeahrc.ZeahRcPlugin;
import net.devious.plugins.zeahrc.overlay.ZeahRcSession;

@Getter
public abstract class SessionUpdater
{
    private ZeahRcSession session;
    @Setter
    private ZeahRcPlugin plugin;


    public void setSession(ZeahRcSession session)
    {
        this.session = session;
    }

    public void updateTask(String task)
    {
        session.setCurrentTask(task);
    }

}
