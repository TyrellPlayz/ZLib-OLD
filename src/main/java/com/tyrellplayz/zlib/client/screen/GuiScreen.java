package com.tyrellplayz.zlib.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public abstract class GuiScreen extends Screen {

    /** The X size of the inventory window in pixels. */
    protected final int xSize;
    /** The Y size of the inventory window in pixels. */
    protected final int ySize;
    /** Starting X position for the Gui. Inconsistent use for Gui backgrounds. */
    protected int left;
    /** Starting Y position for the Gui. Inconsistent use for Gui backgrounds. */
    protected int top;

    public GuiScreen(ITextComponent titleIn, int xSize, int ySize) {
        super(titleIn);
        this.xSize = xSize;
        this.ySize = ySize;
    }

    @Override
    protected void init() {
        this.left = (this.width - xSize) / 2;
        this.top = (this.height - ySize) / 2;
    }
    
    @Override
    public abstract void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks);

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }
}