package net.devious.plugins.banwatch.data;

import java.util.ArrayList;

public class DiscordWebhookBuilder
{
    public static DiscordWebhook buildWebhook(String username)
    {
        DiscordWebhook webhook = new DiscordWebhook();
        webhook.username = "Vyre thief logger";
        webhook.content = username + " got two new blood shards.";
        webhook.embeds = new ArrayList<>();
        Embed embed = new Embed();
        embed.image = new Image();
        embed.image.url = "https://oldschool.runescape.wiki/images/Blood_shard.png?3d4c6";
        webhook.embeds.add(embed);
        return webhook;
    }
}
