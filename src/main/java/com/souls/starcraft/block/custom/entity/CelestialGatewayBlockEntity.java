package com.souls.starcraft.block.custom.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.gateway.CelestialGatewayRegistry;
import com.souls.starcraft.network.CloseCelestialGatewayPayload;
import com.souls.starcraft.network.GatewayDestinationPayload;
import com.souls.starcraft.network.OpenCelestialGatewayPayload;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.PacketDistributor;

public class CelestialGatewayBlockEntity extends BlockEntity {
    public CelestialGatewayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CELESTIAL_GATEWAY.get(), pos, state);
    }

    private final Set<UUID> playersOnGateway = new HashSet<>();

    public static void serverTick(
        Level level,
        BlockPos pos,
        BlockState state,
        CelestialGatewayBlockEntity gateway
    ) {
        CelestialGatewayRegistry.register(gateway.getGatewayId(), pos, level.dimension());

        AABB detectionBox = new AABB(pos).move(0, 1, 0);

        Set<UUID> currentPlayers = new HashSet<>();

        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, detectionBox)) {
            currentPlayers.add(player.getUUID());
        }

        //players who stepped on gateway
        for (UUID uuid : currentPlayers) {
            if (!gateway.playersOnGateway.contains(uuid)) {
                ServerPlayer player = level.getServer().getPlayerList().getPlayer(uuid);

                if (player != null) {
                    PacketDistributor.sendToPlayer(player, new OpenCelestialGatewayPayload());

                    for (CelestialGatewayRegistry.GatewayEntry entry : CelestialGatewayRegistry.getGateways()) {
                        if (entry.id().equals(gateway.getGatewayId())) {
                            continue;
                        }

                        PacketDistributor.sendToPlayer(player, new GatewayDestinationPayload(entry.id(), entry.pos(), pos));
                    }
                }
            }
        }

        //players who stepped off gateway
        for (UUID uuid : gateway.playersOnGateway) {
            if (!currentPlayers.contains(uuid)) {
                ServerPlayer player = level.getServer().getPlayerList().getPlayer(uuid);

                if (player != null) {
                    PacketDistributor.sendToPlayer(player, new CloseCelestialGatewayPayload());
                }
            }
        }

        //remember who's standing here
        gateway.playersOnGateway.clear();
        gateway.playersOnGateway.addAll(currentPlayers);
    }

    private UUID gatewayId = UUID.randomUUID();

    public UUID getGatewayId() {
        return gatewayId;
    }

    @Override 
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putUUID("GatewayId", gatewayId);
    }

    @Override 
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.hasUUID("GatewayId")) {
            gatewayId = tag.getUUID("GatewayId");
        }
    }
}
