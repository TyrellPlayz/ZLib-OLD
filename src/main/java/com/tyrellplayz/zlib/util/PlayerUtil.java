package com.tyrellplayz.zlib.util;

import com.tyrellplayz.zlib.util.helpers.ServerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

    public static void sendGameInfoMessage(PlayerEntity player, ITextComponent textComponent) {
        if(player instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) player).connection.sendPacket(new SChatPacket(textComponent, ChatType.GAME_INFO));
        }
    }

}
