package com.tyrellplayz.zlib.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tyrellplayz.zlib.client.screen.component.Component;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class ContainerScreen<T extends Container> extends net.minecraft.client.gui.screen.inventory.ContainerScreen<T> {

    protected final ResourceLocation GUI_TEXTURE;
    protected List<Component> components = new ArrayList<>();

    public ContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn, ResourceLocation guiTexture) {
        super(screenContainer, inv, titleIn);
        this.GUI_TEXTURE = guiTexture;
    }

    @Override
    protected void init() {
        super.init();
        components.forEach(component -> {
            component.updatePos(guiLeft,guiTop);
        });
    }

    @Override
    public void tick() {
        super.tick();
        components.forEach(Component::tick);
    }

    @Override
    protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String text = title.getFormattedText();
        this.font.drawString(text, (float)(this.xSize / 2 - this.font.getStringWidth(text) / 2), 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);

        components.forEach(component -> component.render(mouseX,mouseY,0));
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        components.forEach(component -> component.onMouseClicked(mouseX,mouseY,button));
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double distanceX, double distanceY) {
        components.forEach(component -> component.onMouseDragged(mouseX, mouseY, button, distanceX, distanceY));
        return super.mouseDragged(mouseX, mouseY, button, distanceX, distanceY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        components.forEach(component -> component.onMouseScrolled(mouseX,mouseY,delta));
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        components.forEach(component -> component.onMouseReleased(mouseX,mouseY,button));
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        components.forEach(component -> component.onMouseMoved(mouseX,mouseY));
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        components.forEach(component -> component.onKeyReleased(keyCode,scanCode,modifiers));
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        components.forEach(component -> component.onKeyPressed(keyCode,scanCode,modifiers));
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char character, int modifiers) {
        components.forEach(component -> component.onCharTyped(character,modifiers));
        return true;
    }

}
