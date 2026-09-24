package com.souls.starcraft.client;

import com.mojang.math.Axis;
import com.souls.starcraft.StarCraft;
import com.souls.starcraft.constellation.Constellations;
import com.souls.starcraft.constellation.TelescopeConstellations;
import com.souls.starcraft.network.DiscoverConstellationPayload;
import com.souls.starcraft.network.RequestCelestialKnowledgePayload;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class TelescopeScreen extends Screen {
    private static final ResourceLocation TELESCOPE_FRAME = 
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/gui/telescope.png");
    private static final ResourceLocation TELESCOPE_SKY =
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/gui/telescope_full_background.png");
    private static final ResourceLocation BEAM = 
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/misc/constellation_beam.png");

    private static final int GUI_SIZE = 256;

    private static final int VIEW_X = 11;
    private static final int VIEW_Y = 33;
    private static final int VIEW_WIDTH = 464;
    private static final int VIEW_HEIGHT = 401;

    private static final int SKY_WIDTH = 1392;
    private static final int SKY_HEIGHT = 802;

    private double panX = 0;
    private double panY = 0;

    private float skyScale = 1.0F;

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
        guiGraphics.blit(TELESCOPE_SKY, viewLeft - (int) panX, viewTop - (int) panY, SKY_WIDTH, SKY_HEIGHT, 0.0F, 0.0F, SKY_WIDTH, SKY_HEIGHT, SKY_WIDTH, SKY_HEIGHT);

        var libellula = TelescopeConstellations.LIBELLULA;
        if (CelestialKnowledgeClient.hasDiscovered(libellula.cId())) {
            var constellation = Constellations.getById(libellula.cId());

            if (constellation != null) {
                for (var connection : constellation.connections()) {
                    var starA = libellula.stars().get(connection.from());
                    var starB = libellula.stars().get(connection.to());
                
                    float starAX = viewLeft + (starA.x() * skyScale) - (float) panX;
                    float starAY = viewTop + (starA.y() * skyScale) - (float) panY;
                    float starBX = viewLeft + (starB.x() * skyScale) - (float) panX;
                    float starBY = viewTop + (starB.y() * skyScale) - (float) panY;

                    drawConstellationBeam(guiGraphics, starAX, starAY, starBX, starBY);
                }
            }
        }

        guiGraphics.disableScissor();
        guiGraphics.blit(TELESCOPE_FRAME, left, top, GUI_SIZE, GUI_SIZE, 0.0F, 0.0F, 512, 512, 512, 512);
        guiGraphics.drawCenteredString(this.font, "Telescope", this.width / 2, top - 12, 0xFFFFFF);
    }

    @Override 
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button != 1) {
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

        int renderedSkyWidth = SKY_WIDTH ;
        int renderedSkyHeight = SKY_HEIGHT;

        panX = Math.max(0, Math.min(panX, renderedSkyWidth - viewWidth));
        panY = Math.max(0, Math.min(panY, renderedSkyHeight - viewHeight));

        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        int left = (this.width - GUI_SIZE) / 2;
        int top = (this.height - GUI_SIZE) / 2;

        float scale = GUI_SIZE / 512.0F;

        int viewLeft = left + Math.round(VIEW_X * skyScale);
        int viewTop = top + Math.round(VIEW_Y * skyScale);
        int viewWidth = Math.round(VIEW_WIDTH * skyScale);
        int viewHeight = Math.round(VIEW_HEIGHT * skyScale);

        //debug
        var libellula = TelescopeConstellations.LIBELLULA;
        var debugStar = libellula.stars().get(3);

        int starX = viewLeft + Math.round(debugStar.x() * skyScale) - (int) panX;
        int starY = viewTop + Math.round(debugStar.y() * skyScale) - (int) panY;

        int hitBoxRadius = 16;

        boolean insideViewport = mouseX >= viewLeft
            && mouseX <= viewLeft + viewWidth
            && mouseY >= viewTop
            && mouseY <= viewTop + viewHeight;

        boolean insideStar = mouseX >= starX - hitBoxRadius
            && mouseX <= starX + hitBoxRadius
            && mouseY >= starY - hitBoxRadius
            && mouseY <= starY + hitBoxRadius;

        if (insideViewport && insideStar) {
            PacketDistributor.sendToServer(new DiscoverConstellationPayload(libellula.cId()));

            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override 
    protected void init() {
        super.init();
        PacketDistributor.sendToServer(new RequestCelestialKnowledgePayload());
    }

    @Override 
    public boolean isPauseScreen() {
        return false;
    }

    //beam helper
    private void drawConstellationBeam(GuiGraphics guiGraphics, float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        float length = (float) Math.sqrt(dx * dx + dy * dy);
        float angle = (float) Math.atan2(dy, dx);

        guiGraphics.pose().pushPose();

        guiGraphics.pose().translate(x1, y1, 0);
        guiGraphics.pose().mulPose(Axis.ZP.rotation(angle));

        guiGraphics.blit(BEAM, 0, -1, Math.round(length), 2, 0.0F, 0.0F, 16, 16, 16, 16);

        guiGraphics.pose().popPose();
    }
}
