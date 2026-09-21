package com.souls.starcraft.item.custom;

import java.util.List;

import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.entity.projectile.StarlightBoltProjectile;
import com.souls.starcraft.mana.StarlightMana;
import com.souls.starcraft.menu.StarWandMenu;
import com.souls.starcraft.network.StarlightManaPayload;
import com.souls.starcraft.spell.ConstellationType;
import com.souls.starcraft.spell.SpellInterpreter;
import com.souls.starcraft.spell.SpellReader;
import com.souls.starcraft.spell.TeleportSpellHandler;
import com.souls.starcraft.spell.TimedFlightManager;
import com.souls.starcraft.spell.TimedLightManager;
import com.souls.starcraft.spell.TimedMaxHealthManager;
import com.souls.starcraft.spell.TimedReachManager;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class StarWandItem extends Item {
    public StarWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use (
        Level level,
        Player player,
        InteractionHand hand
    ) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(new SimpleMenuProvider((containerId, inventory, p) ->
                    new StarWandMenu(containerId, inventory, hand),
                    Component.literal("Star Wand")),
                    buf -> buf.writeEnum(hand)
                );
            }
            
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        List<ConstellationType> components = SpellReader.readSpell(stack, 0);

        SpellInterpreter.SpellResult spell = SpellInterpreter.interpret(components);

        //teleport stuff
        double teleportDistance = SpellInterpreter.getTeleportDistance(spell.operations());
        Vec3 teleportDestination = null;

        if (teleportDistance > 0.0 && player instanceof ServerPlayer serverPlayer) {
            teleportDestination = TeleportSpellHandler.findDestination(serverPlayer, teleportDistance);
        }

        //big spell checker
        boolean canCast = spell.projectile() || spell.flight() || spell.speedLevel() > 0 || spell.lightSource() || spell.maxHealthLevels() > 0
            || spell.invisibility() || spell.strengthLevel() > 0 || teleportDestination != null || spell.absorptionLevel() > 0 || spell.reachLevel() > 0;

        if (!canCast) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.isClientSide()) {
            StarlightMana mana = player.getData(ModDataAttachments.STARLIGHT_MANA);

            int manaCost = SpellReader.getManaCost(stack, 0);

            if (mana.consumeMana(manaCost)) {
                System.out.println("STAR WAND! Mana remaining: " + mana.getMana());

                if (player instanceof ServerPlayer serverPlayer) {
                    //flight
                    if (spell.flight()) {
                        TimedFlightManager.grantFlight(serverPlayer, spell.durationTicks());
                    }

                    //speed
                    if (spell.speedLevel() > 0) {
                        serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, spell.durationTicks(), spell.speedLevel() - 1));
                    }

                    //light
                    if (spell.lightSource()) {
                        TimedLightManager.grantLight(serverPlayer, spell.durationTicks());
                    }

                    //max health
                    if (spell.maxHealthLevels() > 0) {
                        TimedMaxHealthManager.grantMaxHealth(serverPlayer, spell.maxHealthLevels(), spell.durationTicks());
                    }

                    //invisibility
                    if (spell.invisibility()) {
                        serverPlayer.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, spell.durationTicks(), 0));
                    }

                    //projectile
                    int aoeLevel = SpellInterpreter.getProjectileAoeLevel(spell.operations());
                    int bombCount = SpellInterpreter.getProjectileBombCount(spell.operations());

                    if (spell.projectile()) {
                        StarlightBoltProjectile projectile = new StarlightBoltProjectile(level, player);

                        projectile.setSpellDamage(spell.damage());
                        projectile.setSpellRange(spell.range());
                        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 0.0F);
                        projectile.setAoeRadius(aoeLevel);
                        projectile.setBombCount(bombCount);
                        level.addFreshEntity(projectile);

                        PacketDistributor.sendToPlayer(serverPlayer, new StarlightManaPayload(mana.getMana(), mana.getMaxMana()));
                    }

                    //strength
                    if (spell.strengthLevel() > 0) {
                        serverPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, spell.durationTicks(), spell.strengthLevel() - 1));
                    }

                    //teleport
                    if (teleportDestination != null) {
                        TeleportSpellHandler.teleport(serverPlayer, teleportDestination);
                    }

                    //absorption
                    if (spell.absorptionLevel() > 0) {
                        serverPlayer.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, spell.durationTicks(), spell.absorptionLevel() - 1));
                    }

                    //reach
                    if (spell.reachLevel() > 0) {
                        TimedReachManager.grantReach(serverPlayer, spell.reachLevel(), spell.durationTicks());
                    }
                }
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
