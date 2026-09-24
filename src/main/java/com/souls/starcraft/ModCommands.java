package com.souls.starcraft;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.constellation.Constellations;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("starcraft")
            .then(Commands.literal("knowledge")
            .then(Commands.literal("reset")
            .executes(context -> resetKnowledge(context.getSource())
            ))
            .then(Commands.literal("list")
            .executes(context -> listKnowledge(context.getSource())
            ))
            .then(Commands.literal("discover")
            .then(Commands.argument("constellation", StringArgumentType.word())
            .executes(context -> discoverConstellation(context.getSource(), StringArgumentType.getString(context, "constellation")
            )))))
        );
    }

    public static int resetKnowledge(CommandSourceStack source) {
        ServerPlayer player;

        try {
            player = source.getPlayerOrException();
        } catch (Exception exception) {
            source.sendFailure(Component.literal("This command must be run by a player"));

            return 0;
        }

        var knowledge = player.getData(ModDataAttachments.CELESTIAL_KNOWLEDGE.get());

        knowledge.reset();

        source.sendSuccess(() -> Component.literal("Celestial knowledge has been reset"), false);

        return 1;
    }

    private static int listKnowledge(CommandSourceStack source) {
        ServerPlayer player;

        try {
            player = source.getPlayerOrException();
        } catch (Exception exception) {
            source.sendFailure(Component.literal("This command must be run by a player"));

            return 0;
        }

        var knowledge = player.getData(ModDataAttachments.CELESTIAL_KNOWLEDGE.get());
        var discovered = knowledge.getDiscoveredConstellations();

        if (discovered.isEmpty()) {
            source.sendSuccess(() -> Component.literal("You have not discovered any constellations"), false);

            return 1;
        }

        source.sendSuccess(() -> Component.literal("Discovered constellations:"), false);

        for (String cId : discovered) {
            var constellation = Constellations.getById(cId);

            String name = constellation != null ? constellation.name() : cId;

            source.sendSuccess(() -> Component.literal("- " + name), false);
        }

        return 1;
    }

    private static int discoverConstellation(CommandSourceStack source, String cId) {
       ServerPlayer player; 

        try {
            player = source.getPlayerOrException();
        } catch (Exception exception) {
            source.sendFailure(Component.literal("This command must be run by a player"));

            return 0;
        }

        var constellation = Constellations.getById(cId);

        if (constellation == null) {
            source.sendFailure(Component.literal("Unknown Constellation: " + cId));

            return 0;
        }

        var knowledge = player.getData(ModDataAttachments.CELESTIAL_KNOWLEDGE.get());

        boolean newlyDiscovered = knowledge.discover(cId);

        if (newlyDiscovered) {
            source.sendSuccess(() -> Component.literal("Discovered " + constellation.name() + "!"), false);
        } else {
            source.sendSuccess(() -> Component.literal(constellation.name() + " is already discovered"), false);
        }

        return 1;
    }
}
