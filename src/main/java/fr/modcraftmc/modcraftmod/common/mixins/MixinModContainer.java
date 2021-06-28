package fr.modcraftmc.modcraftmod.common.mixins;

import fr.modcraftmc.modcraftmod.ModcraftMod;
import fr.modcraftmc.modcraftmod.common.discord.tracker.ServerStatusTracker;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

@Mixin(ModContainer.class)
public abstract class MixinModContainer {

    @Inject(method = "buildTransitionHandler", at = @At("HEAD"), remap = false)
    private static <T extends Event & IModBusEvent> void pushError(ModContainer target, ModLoadingStage.EventGenerator<T> eventGenerator, BiFunction<ModLoadingStage, Throwable, ModLoadingStage> stateChangeHandler, Executor executor, CallbackInfoReturnable<CompletableFuture<Void>> cir) {

        if (!target.getModId().equalsIgnoreCase("modcraftmod")) return;
        System.out.println(target.getCurrentState().name());
        ModcraftMod.serverStatusTracker.refreshStatusMessage(ServerStatusTracker.InternalState.valueOf(target.getCurrentState().name()), target.getCurrentState() == ModLoadingStage.CONSTRUCT);
    }
}
