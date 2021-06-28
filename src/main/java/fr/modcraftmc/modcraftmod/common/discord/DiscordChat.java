package fr.modcraftmc.modcraftmod.common.discord;

import fr.modcraftmc.modcraftmod.ModcraftMod;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.UUID;

public class DiscordChat extends ListenerAdapter {

    private JDA bot;
    private DiscordWebhookSender webhookSender = new DiscordWebhookSender();

    public DiscordChat() {
        try {
            bot = JDABuilder.createDefault(ModcraftMod.config().getBotToken()).build();
            bot.awaitReady();
            bot.addEventListener(this);
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (bot != null) {
            bot.shutdown();
        }

        if (webhookSender != null) {
            webhookSender.shutdown();
        }
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.isWebhookMessage()
                && !event.getMember().getUser().isBot()
                && !event.getMessage().getContentRaw().isEmpty()
                && event.getMessage().getChannel().getId().equals(ModcraftMod.config().getChannelId())) {

            this.sendMessageToMc(event.getMember().getUser().getName(), event.getMessage().getContentRaw());
        }
    }

    private void sendMessageToMc(String name, String message) {

        if (ServerLifecycleHooks.getCurrentServer() != null) {

            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            server.getPlayerList().func_232641_a_(new StringTextComponent(name).appendString(" -> ").appendString(message), ChatType.SYSTEM, UUID.randomUUID());
        }
    }

    public void sendMessageToDiscord(ServerChatEvent event) {
        webhookSender.sendWebhook(event.getUsername(), event.getMessage(), String.format("https://minotar.net/avatar/%s/100.png", event.getUsername()));
    }

    public void sendStatusMessage(String message, ReturnCallback callback) {
        bot.getTextChannelById(ModcraftMod.config().getChannelId()).sendMessage(message).queue((msg -> {
            if (callback != null) callback.call(msg);
        }));
    }

    
    public interface ReturnCallback {
        void call(Message msg);
    }
}
