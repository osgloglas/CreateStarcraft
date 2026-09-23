package com.souls.starcraft.item.custom;

import java.util.function.Consumer;

import com.souls.starcraft.item.custom.renderer.TelescopeItemRenderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class TelescopeBlockItem extends BlockItem {
    public TelescopeBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override 
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            private TelescopeItemRenderer renderer;

            @Override 
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new TelescopeItemRenderer();
                }

                return renderer;
            }
        });
    }
}
