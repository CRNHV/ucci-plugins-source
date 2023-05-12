package net.devious.plugins.banwatch;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.devious.plugins.banwatch.data.DiscordWebhook;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.events.LoginStateChanged;
import net.unethicalite.client.Static;
import okhttp3.*;
import org.pf4j.Extension;

import java.io.IOException;

import static com.openosrs.http.api.discord.DiscordClient.gson;
import static net.runelite.http.api.RuneLiteAPI.JSON;

@PluginDescriptor(name = "Ucci banwatch", enabledByDefault = false)
@Extension
@Slf4j
public class UcciBanwatchPlugin extends Plugin
{
    @Inject
    private OkHttpClient okHttpClient;

    @Inject
    private UcciBanwatchPluginConfig config;

    @Provides
    public UcciBanwatchPluginConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(UcciBanwatchPluginConfig.class);
    }

    @Subscribe
    private void onLoginStateChanged(LoginStateChanged e)
    {
        int index = e.getIndex();
        if (index != 14)
        {
            return;
        }

        String username = Static.getClient().getUsername();
        DiscordWebhook webhookPayload = buildWebhook(username);

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .post(RequestBody.create(JSON, gson.toJson(webhookPayload)))
                .url(config.webhookUrl())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                log.debug("unable to submit trade", e);
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                if (config.quitWhenBanned())
                {
                    System.exit(0);
                }
            }
        });


    }

    private static DiscordWebhook buildWebhook(String username)
    {
        DiscordWebhook webhook = new DiscordWebhook();
        webhook.username = "Banwatch";
        webhook.content = username + " was banned.";
        return webhook;
    }
}
