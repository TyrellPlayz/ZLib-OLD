package com.tyrellplayz.zlib.util.helpers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderHelper {

    public static void renderItem(int x, int y, ItemStack stack, boolean overlay) {
        RenderSystem.disableDepthTest();
        RenderSystem.enableLighting();
        //net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(stack, x, y);
        if(overlay) Minecraft.getInstance().getItemRenderer().renderItemOverlays(Minecraft.getInstance().fontRenderer, stack, x, y);
        RenderSystem.enableAlphaTest();
        RenderSystem.disableLighting();
    }

    public static void drawRectWithColour(double x, double y, int width, int height, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.color4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x, y + height, 0).endVertex();
        bufferbuilder.pos(x + width, y + height, 0).endVertex();
        bufferbuilder.pos(x + width, y, 0).endVertex();
        bufferbuilder.pos(x, y, 0).endVertex();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectWithTexture(double x, double y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        drawRectWithTexture(x, y, 0, u, v, width, height, textureWidth, textureHeight);
    }

    /**
     * Texture size must be 256x256
     */
    public static void drawRectWithTexture(double x, double y, double z, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float scale = 0.00390625F;
        RenderSystem.enableBlend();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, z).tex((u * scale), (v + textureHeight) * scale).endVertex();
        buffer.pos(x + width, y + height, z).tex((u + textureWidth) * scale, (v + textureHeight) * scale).endVertex();
        buffer.pos(x + width, y, z).tex((u + textureWidth) * scale, v * scale).endVertex();
        buffer.pos(x, y, z).tex(u * scale, v * scale).endVertex();
        tessellator.draw();
    }

    public static void drawRectWithFullTexture(double x, double y, int width, int height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, 0).tex(0, 1).endVertex();
        buffer.pos(x + width, y + height, 0).tex(1, 1).endVertex();
        buffer.pos(x + width, y, 0).tex(1, 0).endVertex();
        buffer.pos(x, y, 0).tex(0, 0).endVertex();
        tessellator.draw();
    }

    public static void drawRectWithTexture(double x, double y, float u, float v, int width, int height, float textureWidth, float textureHeight, int sourceWidth, int sourceHeight) {
        float scaleWidth = 1.0F / sourceWidth;
        float scaleHeight = 1.0F / sourceHeight;
        RenderSystem.enableBlend();
        //RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        //RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, 0).tex(u * scaleWidth, (v + textureHeight) * scaleHeight).endVertex();
        buffer.pos(x + width, y + height, 0).tex((u + textureWidth) * scaleWidth, (float) ((double)(v + textureHeight) * scaleHeight)).endVertex();
        buffer.pos(x + width, y, 0).tex((u + textureWidth) * scaleWidth, v * scaleHeight).endVertex();
        buffer.pos(x, y, 0).tex(u * scaleWidth, v * scaleHeight).endVertex();
        tessellator.draw();
    }

    public static boolean isMouseInside(int mouseX, int mouseY, int x1, int y1, int x2, int y2) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }

    public static boolean isMouseInside(double mouseX, double mouseY, double x1, double y1, double x2, double y2) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }

    public static boolean isMouseWithin(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public static boolean isMouseWithin(double mouseX, double mouseY, double x, double y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public static void drawString(String text, double x, double y, Color color) {
        Minecraft.getInstance().fontRenderer.drawString(text,(float) x,(float) y,color.getRGB());
    }

    public static void drawStringScaled(String text, double x, double y, int color, double xScale, double yScale) {
        RenderSystem.pushMatrix();
        RenderSystem.scaled(0.8,0.9,1);
        Minecraft.getInstance().fontRenderer.drawString(text,(float) x,(float) y,color);
        RenderSystem.popMatrix();
    }

    public static void drawStringClipped(String text, int x, int y, int width, int color, boolean shadow) {
        if(shadow) {
            Minecraft.getInstance().fontRenderer.drawStringWithShadow(clipStringToWidth(text, width) + TextFormatting.RESET, x, y, color);
        }else {
            Minecraft.getInstance().fontRenderer.drawString(clipStringToWidth(text, width) + TextFormatting.RESET, x, y, color);
        }
    }

    public static String clipStringToWidth(String text, int width) {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        String clipped = text;
        if(fontRenderer.getStringWidth(clipped) > width) {
            clipped = fontRenderer.trimStringToWidth(clipped, width - 8) + "...";
        }
        return clipped;
    }

    public static String[] clipStringToWidthArray(String text, int width) {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        List<String> clippedList = new ArrayList<>();
        String textTemp = text;
        boolean flag = true;

        while (flag) {
            if(fontRenderer.getStringWidth(textTemp) > width) {
                clippedList.add(fontRenderer.trimStringToWidth(textTemp,width,false));
                textTemp = fontRenderer.trimStringToWidth(textTemp,fontRenderer.getStringWidth(textTemp)-width,true);
            }else {
                clippedList.add(textTemp);
                flag = false;
            }
        }
        return clippedList.toArray(new String[]{});
    }

}
