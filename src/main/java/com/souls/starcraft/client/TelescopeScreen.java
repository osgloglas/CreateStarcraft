package com.souls.starcraft.client;

import com.souls.starcraft.StarCraft;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TelescopeScreen extends Screen {
    private static final ResourceLocation TELESCOPE_FRAME = 
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/gui/telescope.png");
    private static final ResourceLocation TELESCOPE_SKY =
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/gui/telescope_full_background.png");

    private static final int GUI_SIZE = 256;

    private static final int VIEW_X = 11;
    private static final int VIEW_Y = 33;
    private static final int VIEW_WIDTH = 464;
    private static final int VIEW_HEIGHT = 401;

    private static final int SKY_WIDTH = 1392;
    private static final int SKY_HEIGHT = 802;

    private double panX = 0;
    private double panY = 0;

    public TelescopeScreen() {
        super(Component.literal("Telescope"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        
        int left = (this.width - GUI_SIZE) / 2;
        int top = (this.height - GUI_SIZE) / 2;

        float scale = GUI_SIZE / 512.0F;

        int viewLeft = left + Math.round(VIEW_X * scale);
        int viewTop = top + Math.round(VIEW_Y * scale);
        int viewWidth = Math.round(VIEW_WIDTH * scale);
        int viewHeight = Math.round(VIEW_HEIGHT * scale);

        guiGraphics.enableScissor(viewLeft, viewTop, viewLeft + viewWidth, viewTop + viewHeight);
        guiGraphics.blit(TELESCOPE_SKY, viewLeft - (int) panX, viewTop - (int) panY, SKY_WIDTH / 2, SKY_HEIGHT / 2, 0.0F, 0.0F, SKY_WIDTH, SKY_HEIGHT, SKY_WIDTH, SKY_HEIGHT);
        guiGraphics.disableScissor();
        guiGraphics.blit(TELESCOPE_FRAME, left, top, GUI_SIZE, GUI_SIZE, 0.0F, 0.0F, 512, 512, 512, 512);
        guiGraphics.drawCenteredString(this.font, "Telescope", this.width / 2, top - 12, 0xFFFFFF);
    }

    @Override 
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button != 0) {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }

        int left = (this.width - GUI_SIZE) / 2;
        int top = (this.height - GUI_SIZE) / 2;

        float scale = GUI_SIZE / 512.0F;

        int viewLeft = left + Math.round(VIEW_X * scale);
        int viewTop = top + Math.round(VIEW_Y * scale);
        int viewWidth = Math.round(VIEW_WIDTH * scale);
        int viewHeight = Math.round(VIEW_HEIGHT * scale);

        boolean insideViewport = mouseX >= viewLeft 
            && mouseX <= viewLeft + viewWidth 
            && mouseY >= viewTop
            && mouseY <= viewTop + viewHeight;

        if (!insideViewport) {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }

        panX -= dragX;
        panY -= dragY;

        int renderedSkyWidth = SKY_WIDTH / 2;
        int renderedSkyHeight = SKY_HEIGHT / 2;

        panX = Math.max(0, Math.min(panX, renderedSkyWidth - viewWidth));
        panY = Math.max(0, Math.min(panY, renderedSkyHeight - viewHeight));

        return true;
    }

    @Override 
    public boolean isPauseScreen() {
        return false;
    }
}
