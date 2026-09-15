package com.souls.starcraft.mana;

import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.network.StarlightManaPayload;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class StarlightManaHandler {
    @SubscribeEvent 
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        StarlightMana mana = player.getData(ModDataAttachments.STARLIGHT_MANA);

        if (mana.getMana() >= mana.getMaxMana()) {
            return;
        }

        boolean isNight = player.level().isNight();

        float manaPerSecond = isNight ? 8.0F : 4.0F;

        //minecraft is running 20 tps
        float manaPerTick = manaPerSecond / 20.0F;

        mana.addMana(manaPerTick);

        if (player.tickCount % 4 == 0 || mana.getMana() >= mana.getMaxMana()) {
            PacketDistributor.sendToPlayer(player, new StarlightManaPayload(mana.getMana(), mana.getMaxMana()));
        }
    }
}
