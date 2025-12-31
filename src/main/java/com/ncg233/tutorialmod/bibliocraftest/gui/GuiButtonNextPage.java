package com.ncg233.tutorialmod.bibliocraftest.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonNextPage extends Button {

    private final boolean nextPage;

    public static final ResourceLocation CUSTOM_BOOK_TEXTURES =  ResourceLocation.parse("textures/gui/book_custom.png");

    public GuiButtonNextPage( int x, int y, boolean nextPage) {

        super(
                x, y, 23, 13,
                net.minecraft.network.chat.Component.empty(),
                (button) -> {}

        );
        this.nextPage = nextPage;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (this.visible) {
            Minecraft mc = Minecraft.getInstance();
            TextureManager textureManager = mc.getTextureManager();

            boolean isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, CUSTOM_BOOK_TEXTURES);

            int u = 0;
            int v = 192;
            if (isHovered) {
                u += 23;
            }
            if (!this.nextPage) {
                v += 13;
            }

            blit(poseStack, this.x, this.y, (float)u, (float)v, this.width, this.height, 256, 256);

        }
    }

}