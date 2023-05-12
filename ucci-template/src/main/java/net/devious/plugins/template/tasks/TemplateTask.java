package net.devious.plugins.template.tasks;

import net.devious.plugins.template.framework.SessionTask;

public class TemplateTask extends SessionTask
{
    @Override
    public boolean validate()
    {
        return false;
    }

    @Override
    public int execute()
    {
        setCurrentTask("Doing template stuff");

        return 600;
    }
}
