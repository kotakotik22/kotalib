package com.kotakotik.kotalib;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

/**
 * A class for testing whether Kotalib works correctly
 */
public class KotalibTesting {
    public static void testPrint() {
        System.out.println("kotalib test print");
    }

    public static void testPlayerMessage(PlayerEntity playerEntity) {
        playerEntity.sendStatusMessage(new StringTextComponent("you are using kotalib!!!"), false);
    }
}
