package com.souls.starcraft.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.souls.starcraft.StarCraft;
import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.mana.StarlightMana;
import com.souls.starcraft.spell.SpellCastingItem;
import com.souls.starcraft.spell.SpellReader;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class StarlightManaHud {
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_BAR, ResourceLocation.fromNamespaceAndPath("starcraft", "starlight_mana"), StarlightManaHud::render);
    }

    private static final ResourceLocation MANA_GLASS =
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/gui/starlight_mana_glass.png");
    
    private static final ResourceLocation MANA_FILL =
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/gui/starlight_mana_fill.png");

    private static final int BAR_WIDTH = 96;
    private static final int BAR_HEIGHT = 14;

    private static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        int x = 10;
        int y = screenHeight - 20;

        StarlightMana starlightMana = minecraft.player.getData(ModDataAttachments.STARLIGHT_MANA);
        float mana = starlightMana.getMana();
        float maxMana = starlightMana.getMaxMana();

        ItemStack heldStack = minecraft.player.getMainHandItem();

        String spellText = "";

        if (heldStack.getItem() instanceof SpellCastingItem spellItem) {
            int selectedSpell = spellItem.getSelectedSpell(heldStack);
            int spellCount = spellItem.getSpellCount(heldStack);
            int manaCost = SpellReader.getManaCost(heldStack, selectedSpell);

            spellText = "Spell " + (selectedSpell + 1) + "/" + spellCount + " - " + manaCost + " Mana";
        }

        String manaText = (int) mana + "/" + (int) maxMana;

        float percentage = maxMana > 0.0F ? mana / maxMana : 0.0F;
        int filledWidth = (int) (BAR_WIDTH * percentage);

        //render the bar
        RenderSystem.enableBlend();

        guiGraphics.blit(MANA_GLASS, x, y, 0, 0, BAR_WIDTH, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);

        if (filledWidth > 0) {
            guiGraphics.enableScissor(x, y, x + filledWidth, y + BAR_HEIGHT);

            guiGraphics.blit(MANA_FILL, x, y, 0, 0, BAR_WIDTH, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);

            guiGraphics.disableScissor();
        }

        if (!spellText.isEmpty()) {
            int spellTextX = x + (BAR_WIDTH - minecraft.font.width(spellText)) / 2;

            guiGraphics.drawString(minecraft.font, spellText, spellTextX, y - 20, 0xFFFFFF, true);
        }

        int manaTextX = x + (BAR_WIDTH - minecraft.font.width(manaText)) / 2;

        guiGraphics.drawString(minecraft.font, manaText, manaTextX, y - 10, 0xFFFFFF, true);

        RenderSystem.disableBlend();
    }
}
