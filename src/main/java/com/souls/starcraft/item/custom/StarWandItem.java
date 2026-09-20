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

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

        if (!spell.projectile()) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.isClientSide()) {
            StarlightMana mana = player.getData(ModDataAttachments.STARLIGHT_MANA);

            if (mana.consumeMana(10.0F)) {
                System.out.println("STAR WAND! Mana remaining: " + mana.getMana());

                //launch projectile
                StarlightBoltProjectile projectile = new StarlightBoltProjectile(level, player);

                projectile.setSpellDamage(spell.damage());
                projectile.setSpellRange(spell.range());
                projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 0.0F);
                level.addFreshEntity(projectile);

                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayer(serverPlayer, new StarlightManaPayload(mana.getMana(), mana.getMaxMana()));
                }
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
