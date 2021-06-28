package fr.modcraftmc.modcraftmod.common.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import fr.modcraftmc.modcraftmod.ModcraftMod;

public class DiscordWebhookSender {

    private WebhookClient client;

    public DiscordWebhookSender() {
        if (ModcraftMod.config().getWebhookUrl().isEmpty()) return;
        client = WebhookClient.withUrl(ModcraftMod.config().getWebhookUrl());
    }

    public void sendWebhook(String username, String content, String url) {

        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
        messageBuilder.setUsername(username);
        messageBuilder.setAvatarUrl(url);
        messageBuilder.setContent(content);
        client.send(messageBuilder.build());

    }

    public void shutdown() {
        if (client != null) {
            client.close();
        }
    }
}
