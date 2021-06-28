package fr.modcraftmc.modcraftmod.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;

public class SpeedCommand {

    public void register(CommandDispatcher<CommandSource> dispatcher) {

        LiteralArgumentBuilder<CommandSource> speedCommand = Commands.literal("speed")
                .then(Commands.argument("speed", DoubleArgumentType.doubleArg(0, 10)).executes(this::changePlayerSpeed))
                .then(Commands.literal("reset").executes(this::resetPlayerSPeed));

        dispatcher.register(speedCommand);

    }

    private int resetPlayerSPeed(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        if (ctx.getSource().asPlayer().abilities.isFlying) {
            ctx.getSource().asPlayer().getAttribute(Attributes.FLYING_SPEED).setBaseValue((double)0.4F);
        } else {
            ctx.getSource().asPlayer().getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)0.7F);
        }
        return 1;
    }

    private int changePlayerSpeed(CommandContext<CommandSource> ctx) throws CommandSyntaxException {

        ServerPlayerEntity player = ctx.getSource().asPlayer();

        if (player.abilities.isFlying) {
            player.getAttribute(Attributes.FLYING_SPEED).setBaseValue(DoubleArgumentType.getDouble(ctx, "speed") / 10);
        } else {
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(DoubleArgumentType.getDouble(ctx, "speed") / 10);
        }

        return 1;
    }
}
