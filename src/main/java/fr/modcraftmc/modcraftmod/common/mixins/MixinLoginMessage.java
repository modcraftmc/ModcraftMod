package fr.modcraftmc.modcraftmod.common.mixins;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.login.ServerLoginNetHandler;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(PlayerList.class)
public abstract class MixinLoginMessage {

    @Shadow public abstract void func_232641_a_(ITextComponent p_232641_1_, ChatType p_232641_2_, UUID p_232641_3_);

    @Redirect(method = "initializeConnectionToPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;func_232641_a_(Lnet/minecraft/util/text/ITextComponent;Lnet/minecraft/util/text/ChatType;Ljava/util/UUID;)V"))
    public void redirect(PlayerList playerList, ITextComponent component, ChatType chatType, UUID uuid) {

        String username = component.getString().replace(" joined the game", "");

        this.func_232641_a_(new StringTextComponent(String.format("§8[§a+§8] %s", username)), ChatType.SYSTEM, Util.DUMMY_UUID);
    }
}

