package fr.modcraftmc.modcraftmod.common.mixins;

import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(ServerPlayNetHandler.class)
public class MixinLogoutMessage {

    @Redirect(method = "onDisconnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;func_232641_a_(Lnet/minecraft/util/text/ITextComponent;Lnet/minecraft/util/text/ChatType;Ljava/util/UUID;)V"))
    public void redirect(PlayerList playerList, ITextComponent component, ChatType chatType, UUID uuid) {

        String username = component.getString().replace(" left the game", "");

        playerList.func_232641_a_(new StringTextComponent(String.format("§8[§4-§8] %s", username)), ChatType.SYSTEM, Util.DUMMY_UUID);
    }


}
