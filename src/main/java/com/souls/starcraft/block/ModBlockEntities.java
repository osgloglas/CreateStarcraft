package com.souls.starcraft.block;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.custom.entity.StarlightBasinBlockEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, StarCraft.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StarlightBasinBlockEntity>> STARLIGHT_BASIN = BLOCK_ENTITIES.register(
        "starlight_basin", () -> BlockEntityType.Builder.of(StarlightBasinBlockEntity::new, ModBlocks.STARLIGHT_BASIN.get()).build(null));
}
