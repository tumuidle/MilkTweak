package me.h3xadecimal.milktweak.utils;

import org.lwjgl.opengl.GL11;

public class OpenGL {
    public static void glBlendFunc(int sFactorRGB, int dFactorRGB){
        GL11.glBlendFunc(sFactorRGB, dFactorRGB);
    }
}
