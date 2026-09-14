package com.souls.starcraft.block;

import java.util.function.Supplier;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.item.ModItems;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(StarCraft.MODID);

    public static final DeferredBlock<Block> AQUAMARINE_SAND = registerBlock("aquamarine_sand", () -> new Block(BlockBehaviour.Properties.of()
        .strength(1f).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> MARBLE_BLOCK = registerBlock("marble_block", () -> new Block(BlockBehaviour.Properties.of()
        .strength(2f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
