package com.souls.starcraft.item.custom;

import java.util.List;

import com.souls.starcraft.ModDataComponents;
import com.souls.starcraft.constellation.ConstellationPattern;
import com.souls.starcraft.constellation.Constellations;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ConstellationPaperItem extends Item {
    public ConstellationPaperItem(Properties properties) {
        super(properties);
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

    //temp
    @Override 
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            stack.set(ModDataComponents.CONSTELLATION.get(), "libellula");
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
