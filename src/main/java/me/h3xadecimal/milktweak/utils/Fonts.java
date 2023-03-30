package me.h3xadecimal.milktweak.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.util.ResourceLocation;

public class Fonts {
    private static FontRenderer loadFont(String name) {
        Font f = new Font(Minecraft.getInstance().getTextureManager(), new ResourceLocation("milktweak",  "fonts/" + name + ".ttf"));
        FontRenderer fr = new FontRenderer((rl) -> f);
        return fr;
    }
}
