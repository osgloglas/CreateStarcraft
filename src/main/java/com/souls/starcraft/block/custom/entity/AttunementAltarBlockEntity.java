package com.souls.starcraft.block.custom.entity;

import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.block.ModBlocks;
import com.souls.starcraft.constellation.ConstellationPattern;
import com.souls.starcraft.constellation.Constellations;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AttunementAltarBlockEntity extends BlockEntity {
    public AttunementAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ATTUNEMENT_ALTAR.get(), pos, state);
    }

    private long ritualStartTime = -1;
    private static final int RITUAL_DURATION = 20 * 18;

    public long getRitualTicks() {
        return ritualStartTime;
    }

    public int getRitualDuration() {
        return RITUAL_DURATION;
    }

    public boolean isRitualRunning() {
        if (level == null || ritualStartTime < 0) {
            return false;
        }

        return level.getGameTime() - ritualStartTime < RITUAL_DURATION;
    }

    public void startRitual() {
        if (level == null || isRitualRunning()) return;

        if (!hasValidStructure()) return;
        if (!matchesConstellation(Constellations.LIBELLULA)) return;

        ritualStartTime = level.getGameTime();
        setChanged();

        if (!level.isClientSide()) {
            BlockState state = getBlockState();

            level.sendBlockUpdated(worldPosition, state, state, 3);
        }
    }

    private boolean hasValidStructure() {
        return hasValidBlackMarbleFloor() && hasValidStairs() && hasValidCorners();
    }

    private boolean hasValidBlackMarbleFloor() {
        if (level == null) return false;

        for (int x = -7; x <= 7; x++) {
            for (int z = -7; z <= 7; z++) {
                BlockPos checkPos = worldPosition.offset(x, -1, z);

                if (!level.getBlockState(checkPos).is(ModBlocks.BLACK_MARBLE_BLOCK.get())) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean hasValidStairs() {
        if (level == null) return false;

        for (int i = -6; i <= 6; i++) {
            //north
            if (!level.getBlockState(worldPosition.offset(i, -1, -8))
                .is(ModBlocks.MARBLE_STAIRS.get())) return false;

            //south
            if (!level.getBlockState(worldPosition.offset(i, -1, 8))
                .is(ModBlocks.MARBLE_STAIRS.get())) return false;

            //west
            if (!level.getBlockState(worldPosition.offset(-8, -1, i))
                .is(ModBlocks.MARBLE_STAIRS.get())) return false;

            //east
            if (!level.getBlockState(worldPosition.offset(8, -1, i))
                .is(ModBlocks.MARBLE_STAIRS.get())) return false;
        }

        return true;
    }

    private boolean hasValidCorners() {
        if (level == null) return false;

        return checkCorner(-8, -8, -1, -1) && checkCorner(8, -8, 1, -1) && checkCorner(-8, 8, -1, 1) && checkCorner(8, 8, 1, 1);
    }

    private boolean checkCorner(int x, int z, int xDir, int zDir) {
        BlockPos b = worldPosition.offset(x, -1, z);
        BlockPos a = b.offset(0, 0, -zDir);
        BlockPos c = b.offset(-xDir, 0, 0);

        if (!level.getBlockState(a).is(ModBlocks.MARBLE_BLOCK.get())) return false;
        if (!level.getBlockState(b).is(ModBlocks.MARBLE_BLOCK.get())) return false;
        if (!level.getBlockState(c).is(ModBlocks.MARBLE_BLOCK.get())) return false;

        for (int y = 1; y <= 3; y++) {
            if (!level.getBlockState(b.above(y)).is(ModBlocks.MARBLE_WALL.get())) {
                return false;
            }
        }

        return level.getBlockState(b.above(4)).is(ModBlocks.MARBLE_BLOCK.get());
    }

    private BlockPos rotateStar(BlockPos star, int rotation) {
        int x = star.getX();
        int y = star.getY();
        int z = star.getZ();

        return switch (rotation) {
            case 0 -> new BlockPos(x, y, z);
            case 1 -> new BlockPos(-z, y, x);
            case 2 -> new BlockPos(-x, y, -z);
            case 3 -> new BlockPos(z, y, -x);
            default -> throw new IllegalArgumentException("Invalid Rotation");
        };
    }

    private boolean matchesConstellationRotation(ConstellationPattern pattern, int rotation) {
        if (level == null) return false;

        for (BlockPos star : pattern.stars()) {
            BlockPos rotated = rotateStar(star, rotation);
            BlockPos pedestalPos = worldPosition.offset(rotated);

            if (!level.getBlockState(pedestalPos).is(ModBlocks.MARBLE_PEDESTAL.get())) {
                return false;
            }
        }

        return true;
    }

    private boolean matchesConstellation(ConstellationPattern pattern) {
        for (int rotation = 0; rotation < 4; rotation++) {
            if (matchesConstellationRotation(pattern, rotation)) {
                return true;
            }
        }

        return false;
    }

    public float getRitualProgress(float partialTick) {
        if (!isRitualRunning()) {
            return 0.0F;
        }

        float elapsed = (level.getGameTime() - ritualStartTime) + partialTick;

        return Math.min(elapsed / RITUAL_DURATION, 1.0F);
    }

    public float getRitualTime(float partialTick) {
        if (!isRitualRunning()) {
            return 0.0F;
        }

        return ((level.getGameTime() - ritualStartTime) + partialTick) / 20.0F;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AttunementAltarBlockEntity blockEntity) {
        if (level.isClientSide()) return;

        if (blockEntity.ritualStartTime > 0) {
            blockEntity.ritualStartTime--;

            if (blockEntity.ritualStartTime == 0 && !level.isClientSide()) {
                blockEntity.setChanged();
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putLong("RitualTicks", ritualStartTime);
    }

    @Override 
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        ritualStartTime = tag.getLong("RitualTicks");
    }

    @Override 
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override 
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
