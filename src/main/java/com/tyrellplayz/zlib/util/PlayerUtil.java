package com.tyrellplayz.zlib.util;

import com.tyrellplayz.zlib.util.helpers.ServerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class PlayerUtil {

    private PlayerUtil() {}

    public static void sendMessage(PlayerEntity player, String message){
        if(ServerHelper.isServerWorld(player.world)) player.sendMessage(new StringTextComponent(message));
    }

    /**
     * Displays a screen the the user.
     * @param player The player to make sure it gets ran on the client.
     * @param screen The screen to be displayed
     */
    public static void displayScreen(PlayerEntity player, Screen screen){
        if(ServerHelper.isClientWorld(player.world)){
            Minecraft.getInstance().displayGuiScreen(screen);
        }
    }

}
