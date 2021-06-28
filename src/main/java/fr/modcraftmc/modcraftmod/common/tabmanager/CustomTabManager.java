package fr.modcraftmc.modcraftmod.common.tabmanager;

import fr.modcraftmc.modcraftmod.ModcraftMod;
import net.minecraft.network.play.server.SPlayerListHeaderFooterPacket;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class CustomTabManager {

    private SPlayerListHeaderFooterPacket packet = new SPlayerListHeaderFooterPacket();

    private String defaultHeader;
    private String defaultFooter;

    public CustomTabManager() {

        defaultHeader = ModcraftMod.config().getTabHeader();
        defaultFooter = ModcraftMod.config().getTabFooter();

    }

    public void parseFooterPerPlayer() {

    }

    public void sendToAllPlayer() {
        ServerLifecycleHooks.getCurrentServer().getPlayerList().sendPacketToAllPlayers(packet);
    }
}
