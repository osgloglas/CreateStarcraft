package com.souls.starcraft.client;

import com.souls.starcraft.menu.StarWandMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class StarWandScreen extends AbstractContainerScreen<StarWandMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
        "starcraft", "textures/gui/starlight_wand.png");

    public StarWandScreen(StarWandMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        this.imageWidth = 256;
        this.imageHeight = 256;

        //shift inventory label down
        this.inventoryLabelX = 8;
        this.inventoryLabelY = 96;
    }

    @Override 
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override 
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
