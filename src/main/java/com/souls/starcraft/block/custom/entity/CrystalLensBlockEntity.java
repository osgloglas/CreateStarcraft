package com.souls.starcraft.block.custom.entity;

import java.util.List;

import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.block.ModBlocks;
import com.souls.starcraft.block.custom.GrowableCrystalBlock;
import com.souls.starcraft.fluid.ModFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CrystalLensBlockEntity extends BlockEntity {
    public CrystalLensBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRYSTAL_LENS.get(), pos, state);
    }

    private static BlockPos findStarlightBelow(Level level, BlockPos lensPos) {
        BlockPos checkPos = lensPos.below();

        //search through frame blocks
        while (level.getBlockState(checkPos).is(ModBlocks.LENS_FRAME.get())) {
            checkPos = checkPos.below();
        }

        if (level.getFluidState(checkPos).is(ModFluids.LIQUID_STARLIGHT.get())) {
            return checkPos;
        }

        return null;
    }

    private int growthTimer = 0;

    public static void serverTick(Level level, BlockPos pos, BlockState state, CrystalLensBlockEntity lens) {
        //growing
        BlockPos checkPos = pos.below();

        while (level.getBlockState(checkPos).is(ModBlocks.LENS_FRAME.get())) {
            checkPos = checkPos.below();
        }

        BlockState targState = level.getBlockState(checkPos);

        if (targState.is(ModBlocks.GROWABLE_CRYSTAL.get())) {
            lens.growthTimer++;

            //speed (every 5s for testing = 100)
            if (lens.growthTimer >= 100) {
                int stage = targState.getValue(GrowableCrystalBlock.STAGE);

                if (stage < 2) {
                    level.setBlock(checkPos, targState.setValue(GrowableCrystalBlock.STAGE, stage + 1), 3);
                }

                lens.growthTimer = 0;
            }

            return;
        }

        lens.growthTimer = 0;

        //create baby crystal
        if (!level.getFluidState(checkPos).is(ModFluids.LIQUID_STARLIGHT.get())) {
            return;
        }

        AABB area = new AABB(checkPos);

        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area, item -> item.getItem().is(Items.QUARTZ));

        if (items.isEmpty()) {
            return;
        }

        ItemEntity quartzEntity = items.get(0);
        ItemStack quartzStack = quartzEntity.getItem();

        //consume 1 quartz
        quartzStack.shrink(1);

        if (quartzStack.isEmpty()) {
            quartzEntity.discard();
        }

        //replace with baby crystal
        BlockState babyCrystal = ModBlocks.GROWABLE_CRYSTAL.get().defaultBlockState().setValue(GrowableCrystalBlock.STAGE, 0);

        level.setBlock(checkPos, babyCrystal, 3);
    }
}
