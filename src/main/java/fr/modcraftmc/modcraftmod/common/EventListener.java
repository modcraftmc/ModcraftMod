package fr.modcraftmc.modcraftmod.common;

import fr.modcraftmc.modcraftmod.ModcraftMod;
import fr.modcraftmc.modcraftmod.common.discord.tracker.PlayerStatusTracker;
import fr.modcraftmc.modcraftmod.common.discord.tracker.ServerStatusTracker;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayerListHeaderFooterPacket;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.*;

public class EventListener {

    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event) {
        System.out.println(event.getMessage());
        if (ModcraftMod.discordChat != null) ModcraftMod.discordChat.sendMessageToDiscord(event);
    }

    @SubscribeEvent
    public static void serverStarting(FMLServerAboutToStartEvent event) {
        ModcraftMod.serverStatusTracker.pushServerState(ServerStatusTracker.State.SERVER_STARTING);
    }

    @SubscribeEvent
    public static void serverStarted(FMLServerStartedEvent event) {
        ModcraftMod.serverStatusTracker.pushServerState(ServerStatusTracker.State.SERVER_STARTED);
    }

    @SubscribeEvent
    public static void serverStopped(FMLServerStoppingEvent event) {
        ModcraftMod.serverStatusTracker.pushServerState(ServerStatusTracker.State.SERVER_STOPPED);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ModcraftMod.playerStatusTracker.pushPlayerState((ServerPlayerEntity) event.getPlayer(), PlayerStatusTracker.State.PLAYER_LOGIN);
        SPlayerListHeaderFooterPacket packet = new SPlayerListHeaderFooterPacket();
        packet.header = new StringTextComponent("test");
        packet.footer = new StringTextComponent("ccc");
        ((ServerPlayerEntity) event.getPlayer()).connection.sendPacket(packet);
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        ModcraftMod.playerStatusTracker.pushPlayerState((ServerPlayerEntity) event.getPlayer(), PlayerStatusTracker.State.PLAYER_LOGOUT);
    }

    @SubscribeEvent
    public static void onPlayerDie(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayerEntity) {
            ModcraftMod.playerStatusTracker.pushPlayerState((ServerPlayerEntity) event.getEntity(), PlayerStatusTracker.State.PLAYER_DIE, event.getSource().getDeathMessage(event.getEntityLiving()).getString());
        }
    }

    @SubscribeEvent
    public static void onPlayerAdvancement(AdvancementEvent event) {
        if (event.getAdvancement().getDisplay() != null && event.getAdvancement().getDisplay().shouldAnnounceToChat())
            ModcraftMod.playerStatusTracker.pushPlayerState((ServerPlayerEntity) event.getPlayer(), PlayerStatusTracker.State.PLAYER_ADVANCEMENT, event.getAdvancement().getDisplayText().getString());
    }
}
