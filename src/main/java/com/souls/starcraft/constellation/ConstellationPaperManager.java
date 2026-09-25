package com.souls.starcraft.constellation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.souls.starcraft.ModDataComponents;
import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.item.custom.ConstellationPaperItem;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ConstellationPaperManager {
    public static List<ConstellationPattern> getMissingConstellationPapers(ServerPlayer player) {
        var knowledge = player.getData(ModDataAttachments.CELESTIAL_KNOWLEDGE.get());

        List<ConstellationPattern> missing = new ArrayList<>();

        for (String constellationId : knowledge.getDiscoveredConstellations()) {
            if (knowledge.hasPaperBeenAssigned(constellationId)) {
                continue;
            }

            ConstellationPattern constellation = Constellations.getById(constellationId);

            if (constellation != null) {
                missing.add(constellation);
            }
        }

        return missing;
    }

    public static ConstellationPattern getRandomMissingConstellation(ServerPlayer player) {
        List<ConstellationPattern> missing = getMissingConstellationPapers(player);

        if (missing.isEmpty()) {
            return null;
        }

        return missing.get(ThreadLocalRandom.current().nextInt(missing.size()));
    }

    public static void writeConstellationToBlankPaper(ServerPlayer player, String constellationId) {
        for (ItemStack stack : player.getInventory().items) {
            if (!(stack.getItem() instanceof ConstellationPaperItem)) {
                continue;
            }

            String existingConstellation = stack.get(ModDataComponents.CONSTELLATION.get());

            if (existingConstellation != null) {
                continue;
            }

            if (stack.getCount() == 1) {
                stack.set(ModDataComponents.CONSTELLATION.get(), constellationId);
            } else {
                ItemStack writtenPaper = stack.copyWithCount(1);

                writtenPaper.set(ModDataComponents.CONSTELLATION.get(), constellationId);

                stack.shrink(1);

                if (!player.getInventory().add(writtenPaper)) {
                    player.drop(writtenPaper, false);
                }
            }

            var knowledge = player.getData(ModDataAttachments.CELESTIAL_KNOWLEDGE.get());

            knowledge.markPaperAssigned(constellationId);

            return;
        }
    }

    public static boolean writeConstellationCopy(ServerPlayer player, String constellationId) {
        for (ItemStack stack : player.getInventory().items) {
            if (!(stack.getItem() instanceof ConstellationPaperItem)) {
                continue;
            }

            String existingConstellation = stack.get(ModDataComponents.CONSTELLATION.get());

            if (existingConstellation != null) {
                continue;
            }

            if (stack.getCount() == 1) {
                stack.set(ModDataComponents.CONSTELLATION.get(), constellationId);
            } else {
                ItemStack writtenPaper = stack.copyWithCount(1);

                writtenPaper.set(ModDataComponents.CONSTELLATION.get(), constellationId);

                stack.shrink(1);

                if (!player.getInventory().add(writtenPaper)) {
                    player.drop(writtenPaper, false);
                }
            }

            return true;
        }

        return false;
    }

    public static void assignRandomConstellationToBlankPaper(ServerPlayer player, ItemStack stack) {
        if (!(stack.getItem() instanceof ConstellationPaperItem)) {
            return;
        }

        String existingConstellation = stack.get(ModDataComponents.CONSTELLATION.get());

        if (existingConstellation != null) {
            return;
        }

        ConstellationPattern constellation = getRandomMissingConstellation(player);

        if (constellation == null) {
            return;
        }

        if (stack.getCount() == 1) {
            stack.set(ModDataComponents.CONSTELLATION.get(), constellation.id());
        } else {
            ItemStack writtenPaper = stack.copyWithCount(1);

            writtenPaper.set(ModDataComponents.CONSTELLATION.get(), constellation.id());

            stack.shrink(1);

            if (!player.getInventory().add(writtenPaper)) {
                player.drop(writtenPaper, false);
            }
        }

        var knowledge = player.getData(ModDataAttachments.CELESTIAL_KNOWLEDGE.get());

        knowledge.markPaperAssigned(constellation.id());
    }
}
