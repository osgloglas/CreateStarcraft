package com.souls.starcraft.item.custom;

import java.util.List;

import com.souls.starcraft.ModDataComponents;
import com.souls.starcraft.constellation.ConstellationPaperManager;
import com.souls.starcraft.constellation.ConstellationPattern;
import com.souls.starcraft.constellation.Constellations;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ConstellationPaperItem extends Item {
    public ConstellationPaperItem(Properties properties) {
        super(properties);
    }

    @Override 
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (level.isClientSide()) {
            return;
        }

        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        ConstellationPaperManager.assignRandomConstellationToBlankPaper(player, stack);
    }

    @Override 
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String constellationId = stack.get(ModDataComponents.CONSTELLATION.get());

        if (constellationId != null) {
            ConstellationPattern constellation = Constellations.getById(constellationId);

            if (constellation != null) {
                tooltipComponents.add(Component.literal(constellation.name()).withStyle(ChatFormatting.GOLD));
            }
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
