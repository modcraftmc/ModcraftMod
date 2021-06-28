package fr.modcraftmc.modcraftmod;

import fr.modcraftmc.modcraftmod.common.EventListener;
import fr.modcraftmc.modcraftmod.common.commands.SpeedCommand;
import fr.modcraftmc.modcraftmod.common.config.ServerConfig;
import fr.modcraftmc.modcraftmod.common.discord.DiscordChat;
import fr.modcraftmc.modcraftmod.common.discord.tracker.PlayerStatusTracker;
import fr.modcraftmc.modcraftmod.common.discord.tracker.ServerStatusTracker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod("modcraftmod")
public class ModcraftMod {

    public static final Logger LOGGER = LogManager.getLogger();
    private static final ServerConfig config = ServerConfig.loadConfig(new File("modcraftmc", "modcraftmod.json"));
    public static DiscordChat discordChat = new DiscordChat();

    public static PlayerStatusTracker playerStatusTracker = new PlayerStatusTracker();
    public static ServerStatusTracker serverStatusTracker = new ServerStatusTracker();

    public ModcraftMod() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doServerStuff);

        MinecraftForge.EVENT_BUS.register(EventListener.class);
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    private void doServerStuff(final FMLDedicatedServerSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        new SpeedCommand().register(event.getDispatcher());

    }


    @SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent event) {
        if (discordChat != null) discordChat.disconnect();
    }

    public static ServerConfig config() {
        return config;
    }
}
