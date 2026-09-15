package com.souls.starcraft.event;

import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.mana.StarlightMana;
import com.souls.starcraft.network.StarlightManaPayload;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class StarlightManaEvents {
    @SubscribeEvent 
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        StarlightMana mana = serverPlayer.getData(ModDataAttachments.STARLIGHT_MANA);

        PacketDistributor.sendToPlayer(serverPlayer, new StarlightManaPayload(mana.getMana(), mana.getMaxMana()));
    }
}
