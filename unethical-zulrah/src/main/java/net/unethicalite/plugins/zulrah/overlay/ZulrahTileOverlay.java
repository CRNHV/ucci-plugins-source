package net.unethicalite.plugins.zulrah.overlay;

import com.google.common.base.Strings;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.*;
import net.unethicalite.plugins.zulrah.UnethicalZulrahPlugin;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahCycle;
import net.unethicalite.plugins.zulrah.data.phases.ZulrahNode;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class ZulrahTileOverlay extends Overlay
{
    private final Client client;
    private final UnethicalZulrahPlugin plugin;

    @Inject
    private ZulrahTileOverlay(Client client, UnethicalZulrahPlugin plugin)
    {
        this.client = client;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Stroke stroke = new BasicStroke(1);
        Color tileColor = Color.GREEN;

        if (plugin.node != null)
        {
            WorldPoint worldPoint = plugin.node.getZulrahCycle().getSafeSpot(plugin.origin);
            if (worldPoint.getPlane() != client.getPlane())
            {
                return null;
            }

            drawTile(graphics, worldPoint, tileColor, plugin.node.getZulrahCycle().name(), stroke);
        }

        if (plugin.node != null && plugin.node.hasChildren() && plugin.node.getZulrahCycle() != ZulrahCycle.INITIAL)
        {
            List<ZulrahNode> childNodes = plugin.node.getChildren();
            ZulrahNode childNode = childNodes.get(0);
            WorldPoint worldPoint = childNode.getZulrahCycle().getSafeSpot(plugin.origin);
            if (worldPoint.getPlane() != client.getPlane())
            {
                return null;
            }

            Color futureColor = Color.BLUE;
            drawTile(graphics, worldPoint, futureColor, childNode.getZulrahCycle().name(), stroke);
        }

        return null;
    }

    private void drawTile(Graphics2D graphics, WorldPoint point, Color color, @Nullable String label, Stroke borderStroke)
    {
        WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();

        if (point.distanceTo(playerLocation) >= 100)
        {
            return;
        }

        LocalPoint lp = LocalPoint.fromWorld(client, point);
        if (lp == null)
        {
            return;
        }

        Polygon poly = Perspective.getCanvasTilePoly(client, lp);
        if (poly != null)
        {
            OverlayUtil.renderPolygon(graphics, poly, color, new Color(0, 0, 0, 0), borderStroke);
        }

        if (!Strings.isNullOrEmpty(label))
        {
            Point canvasTextLocation = Perspective.getCanvasTextLocation(client, graphics, lp, label, 0);
            if (canvasTextLocation != null)
            {
                OverlayUtil.renderTextLocation(graphics, canvasTextLocation, label, color);
            }
        }
    }
}
