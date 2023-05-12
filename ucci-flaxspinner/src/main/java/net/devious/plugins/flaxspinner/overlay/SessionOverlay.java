package net.devious.plugins.flaxspinner.overlay;


import com.google.inject.Inject;
import net.devious.plugins.flaxspinner.FlaxSpinnerPlugin;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;

import java.awt.*;

public class SessionOverlay extends OverlayPanel
{
    private final FlaxSpinnerPlugin plugin;

    @Inject
    private SessionOverlay(FlaxSpinnerPlugin plugin)
    {
        super(plugin);
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        setPriority(OverlayPriority.HIGHEST);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        BotSession session = plugin.getSession();

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Ucci flaxspinner v0.0.1")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Bot time: ")
                .right(session.getElapsedTime())
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Task: ")
                .right(session.getCurrentTask())
                .build());
        panelComponent.setPreferredSize(new Dimension(175, 75));

        return super.render(graphics);
    }
}
