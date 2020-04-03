package com.tyrellplayz.zlib.client.screen.component;

import com.tyrellplayz.zlib.ZLib;
import com.tyrellplayz.zlib.client.screen.IGuiEventListener;
import com.tyrellplayz.zlib.util.helpers.RenderHelper;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a component in an application.
 */
public abstract class Component implements IGuiEventListener {

    protected static final ResourceLocation COMPONENT_TEXTURES = new ResourceLocation(ZLib.MOD_ID,"textures/gui/components.png");

    private double xPos;
    private double yPos;
    private int left;
    private int top;

    private int width;
    private int height;
    private boolean visible;
    private boolean enabled;
    private boolean hovering;

    private OnTick onTick;

    public Component(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.visible = true;
        this.enabled = true;
    }

    public void tick() {
        if(onTick != null) onTick.tick(this);
    }

    public Component setOnTick(OnTick onTick) {
        this.onTick = onTick;
        return this;
    }

    public abstract void render(int mouseX, int mouseY, float partialTicks);

    @Override
    public void onMouseClicked(double mouseX, double mouseY, int mouseButton) { }

    @Override
    public void onMouseReleased(double mouseX, double mouseY, int mouseButton) { }

    @Override
    public void onMouseMoved(double mouseX, double mouseY) {
        hovering = isMouseInside(mouseX,mouseY);
    }

    @Override
    public void onMouseScrolled(double mouseX, double mouseY, double delta) { }

    @Override
    public void onMouseDragged(double mouseX, double mouseY, int mouseButton, double distanceX, double distanceY) { }

    @Override
    public void onKeyPressed(int keyCode, int scanCode, int modifiers) { }

    @Override
    public void onKeyReleased(int keyCode, int scanCode, int modifiers) { }

    @Override
    public void onCharTyped(char character, int modifiers) { }

    @Override
    public void onFocusChanged(boolean lostFocus) {
        if(lostFocus) hovering = false;
    }

    /**
     * Gets called when the components layer changes. i.e. becomes the current layer.
     */
    public void onLayerChanged() {
        hovering = false;
    }

    public final int getLeft() {
        return left;
    }

    public final int getTop() {
        return top;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final double getXPos() {
        return xPos;
    }

    public final double getYPos() {
        return yPos;
    }

    public final void setVisible(boolean visible) {
        this.visible = visible;
    }

    public final boolean isVisible() {
        return visible;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final boolean isEnabled() {
        if(!isVisible()) return false;
        return enabled;
    }

    public final boolean isHovering() {
        return hovering;
    }

    public final boolean isMouseInside(double mouseX, double mouseY) {
        return RenderHelper.isMouseInside((int) mouseX, (int) mouseY, xPos, yPos, xPos + width, yPos + height);
    }

    /**
     * Updates the position of the component
     * @param xPos The xPos of the window.
     * @param yPos The yPos of the window
     */
    public void updatePos(double xPos, double yPos) {
        this.xPos = xPos+getLeft();
        this.yPos = yPos+getTop();
    }

    public interface OnTick {
        void tick(Component component);
    }

}
