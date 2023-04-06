package net.unethicalite.plugins.zulrah.framework;

import lombok.Getter;
import lombok.Setter;
import net.unethicalite.plugins.zulrah.UnethicalZulrahPlugin;
import net.unethicalite.plugins.zulrah.overlay.ZulrahSession;

@Getter
public abstract class SessionUpdater
{

    private ZulrahSession session;
    @Setter
    private UnethicalZulrahPlugin plugin;

    public void setSession(ZulrahSession session)
    {
        this.session = session;
    }

    public void updateTask(String task)
    {
        session.setCurrentTask(task);
    }

}
