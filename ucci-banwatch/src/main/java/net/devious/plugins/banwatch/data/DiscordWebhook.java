package net.devious.plugins.banwatch.data;

import java.util.ArrayList;

public class DiscordWebhook
{
    public String username;
    public String avatar_url;
    public String content;
    public ArrayList<Embed> embeds = new ArrayList<>();
    public ArrayList<Object> components = new ArrayList<>();
}
