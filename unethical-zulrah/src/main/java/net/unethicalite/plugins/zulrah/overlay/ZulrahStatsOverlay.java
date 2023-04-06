package net.unethicalite.plugins.zulrah.overlay;

import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.unethicalite.plugins.zulrah.UnethicalZulrahPlugin;

import javax.inject.Inject;
import java.awt.*;

public class ZulrahStatsOverlay extends OverlayPanel
{
    private final UnethicalZulrahPlugin plugin;

    @Inject
    private ZulrahStatsOverlay(UnethicalZulrahPlugin plugin)
    {
        super(plugin);
        setPosition(OverlayPosition.TOP_LEFT);
        setPriority(OverlayPriority.LOW);
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        ZulrahSession session = plugin.getSession();

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Unethical Zulrah | Improved by Ucci")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("")
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Total kills:")
                .right(Integer.toString(session.getKills()))
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
