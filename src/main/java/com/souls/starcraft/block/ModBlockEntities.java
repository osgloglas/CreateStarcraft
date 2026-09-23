package com.souls.starcraft.block;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.custom.entity.AttunementAltarBlockEntity;
import com.souls.starcraft.block.custom.entity.CelestialGatewayBlockEntity;
import com.souls.starcraft.block.custom.entity.CrystalLensBlockEntity;
import com.souls.starcraft.block.custom.entity.StarlightBasinBlockEntity;
import com.souls.starcraft.block.custom.entity.TelescopeBlockEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, StarCraft.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StarlightBasinBlockEntity>> STARLIGHT_BASIN = BLOCK_ENTITIES.register(
        "starlight_basin", () -> BlockEntityType.Builder.of(StarlightBasinBlockEntity::new, ModBlocks.STARLIGHT_BASIN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CelestialGatewayBlockEntity>> CELESTIAL_GATEWAY = BLOCK_ENTITIES.register(
        "celestial_gateway", () -> BlockEntityType.Builder.of(CelestialGatewayBlockEntity::new, ModBlocks.CELESTIAL_GATEWAY.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrystalLensBlockEntity>> CRYSTAL_LENS = BLOCK_ENTITIES.register(
        "crystal_lens", () -> BlockEntityType.Builder.of(CrystalLensBlockEntity::new, ModBlocks.CRYSTAL_LENS.get()).build(null));
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AttunementAltarBlockEntity>> ATTUNEMENT_ALTAR = BLOCK_ENTITIES.register(
        "attunement_altar", () -> BlockEntityType.Builder.of(AttunementAltarBlockEntity::new, ModBlocks.ATTUNEMENT_ALTAR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TelescopeBlockEntity>> TELESCOPE_BE = BLOCK_ENTITIES.register(
        "telescope_be", () -> BlockEntityType.Builder.of(TelescopeBlockEntity::new, ModBlocks.TELESCOPE.get()).build(null));
}
