package me.h3xadecimal.milktweak.utils.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.Font;
import java.io.InputStream;

public abstract class FontLoaders {

	public static CFontRenderer Arial18 = new CFontRenderer(FontLoaders.getfonts(18,"arial",true), true, true);
	public static CFontRenderer Arial40 = new CFontRenderer(FontLoaders.getfonts(40,"arial", true), true, true);

	public static CFontRenderer Harmony18 = new CFontRenderer(FontLoaders.getfonts(18, "HarmonySans", true), true, true);
	public static CFontRenderer Harmony40 = new CFontRenderer(FontLoaders.getfonts(40, "HarmonySans", true), true, true);

	public static CFontRenderer Regular18 = new CFontRenderer(getfonts(18, "regular", true), true, true);
	public static CFontRenderer Regular40 = new CFontRenderer(getfonts(40, "regular", true), true, true);

	private static Font getfonts(int size,String fontname,boolean ttf) {
		Font font;
		try {
			InputStream is;
			if (ttf) {
				is = Minecraft.getInstance().getResourceManager()
						.getResource(new ResourceLocation("milktweak", "fonts/" + fontname + ".ttf")).getInputStream();
			}else {
				is = Minecraft.getInstance().getResourceManager()
						.getResource(new ResourceLocation("milktweak", "fonts/" + fontname + ".otf")).getInputStream();
			}
			font = Font.createFont(0, is);
			font = font.deriveFont(Font.PLAIN, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font " + fontname);
			font = new Font("default", 0, size);
		}
		return font;
	}
}
