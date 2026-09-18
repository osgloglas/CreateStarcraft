package com.souls.starcraft.block;

import java.util.function.Supplier;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.custom.StarlightBasinBlock;
import com.souls.starcraft.block.custom.StarlightCrafterBlock;
import com.souls.starcraft.fluid.ModFluids;
import com.souls.starcraft.item.ModItems;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
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
    public static final DeferredBlock<Block> STARLIGHT_CRAFTER = registerBlock("starlight_crafter", () -> new StarlightCrafterBlock(BlockBehaviour.Properties.of()
        .strength(2f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion()));
    public static final DeferredBlock<Block> STARLIGHT_BASIN = registerBlock("starlight_basin", () -> new StarlightBasinBlock(BlockBehaviour.Properties.of()
        .strength(2f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion()));
    public static final DeferredBlock<Block> MARBLE_STAIRS = registerBlock("marble_stairs", () -> new StairBlock(ModBlocks.MARBLE_BLOCK.get().defaultBlockState(),
        BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> MARBLE_SLAB = registerBlock("marble_slab", () -> new SlabBlock(BlockBehaviour.Properties.of()
        .strength(2f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> MARBLE_WALL = registerBlock("marble_wall", () -> new WallBlock(BlockBehaviour.Properties.of()
        .strength(2f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<LiquidBlock> LIQUID_STARLIGHT_BLOCK = registerBlock("liquid_starlight", () -> new LiquidBlock(ModFluids.LIQUID_STARLIGHT.get(),
        BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).lightLevel(state -> 8)));

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
