package net.devious.plugins.clockworks.overlay;


import com.google.inject.Inject;
import net.devious.plugins.clockworks.ClockworkPlugin;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;

import java.awt.*;

public class SessionOverlay extends OverlayPanel
{
    private final ClockworkPlugin plugin;

    @Inject
    private SessionOverlay(ClockworkPlugin plugin)
    {
        super(plugin);
        setPosition(OverlayPosition.TOP_LEFT);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        BotSession session = plugin.getSession();

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Ucci clockworks v0.0.2")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Bot time: ")
                .right(session.getElapsedTime())
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Status: ")
                .right(session.getCurrentTask())
                .build());

        return super.render(graphics);
    }
}
