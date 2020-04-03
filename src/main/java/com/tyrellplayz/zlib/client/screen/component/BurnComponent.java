package com.tyrellplayz.zlib.client.screen.component;

import com.tyrellplayz.zlib.util.helpers.RenderHelper;
import net.minecraft.client.Minecraft;

public class BurnComponent extends Component {

    private int burnTime;
    private int currentBurnTime;

    public BurnComponent(int left, int top) {
        super(left, top, 14, 14);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if(burnTime > 0) {
            Minecraft.getInstance().getTextureManager().bindTexture(COMPONENT_TEXTURES);
            int k = getBurnLeftScaled();
            RenderHelper.drawRectWithTexture(getXPos(), getYPos()+12-k, 0, 12-k, 14, k+1, 14, k+1);
        }
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public void setCurrentBurnTime(int currentBurnTime) {
        this.currentBurnTime = currentBurnTime;
    }

    public int getBurnLeftScaled() {
        int i = currentBurnTime;
        if (i == 0) i = 200;
        return burnTime * 13 / i;
    }

}
