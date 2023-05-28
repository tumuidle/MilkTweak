package me.h3xadecimal.milktweak.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import static com.mojang.blaze3d.platform.GlStateManager.*;

public class TextureUtil {
    private static final Logger logger = LogManager.getLogger();
    private static final IntBuffer dataBuffer = GLAllocation.createDirectByteBuffer(4194304).asIntBuffer();
    public static final DynamicTexture missingTexture = new DynamicTexture(16, 16, false);
    private static final int[] mipmapBuffer;

    public TextureUtil() {
    }

    public static int glGenTextures() {
        return GL11.glGenTextures();
    }

    public static void deleteTexture(int p_deleteTexture_0_) {
        RenderSystem.deleteTexture(p_deleteTexture_0_);
    }

    public static int uploadTextureImage(int p_uploadTextureImage_0_, BufferedImage p_uploadTextureImage_1_) {
        return uploadTextureImageAllocate(p_uploadTextureImage_0_, p_uploadTextureImage_1_, false, false);
    }

    public static void uploadTexture(int p_uploadTexture_0_, int[] p_uploadTexture_1_, int p_uploadTexture_2_, int p_uploadTexture_3_) {
        bindTexture(p_uploadTexture_0_);
        uploadTextureSub(0, p_uploadTexture_1_, p_uploadTexture_2_, p_uploadTexture_3_, 0, 0, false, false, false);
    }

    public static int[][] generateMipmapData(int p_generateMipmapData_0_, int p_generateMipmapData_1_, int[][] p_generateMipmapData_2_) {
        int[][] aint = new int[p_generateMipmapData_0_ + 1][];
        aint[0] = p_generateMipmapData_2_[0];
        if (p_generateMipmapData_0_ > 0) {
            boolean flag = false;

            int l1;
            for(l1 = 0; l1 < p_generateMipmapData_2_.length; ++l1) {
                if (p_generateMipmapData_2_[0][l1] >> 24 == 0) {
                    flag = true;
                    break;
                }
            }

            for(l1 = 1; l1 <= p_generateMipmapData_0_; ++l1) {
                if (p_generateMipmapData_2_[l1] != null) {
                    aint[l1] = p_generateMipmapData_2_[l1];
                } else {
                    int[] aint1 = aint[l1 - 1];
                    int[] aint2 = new int[aint1.length >> 2];
                    int j = p_generateMipmapData_1_ >> l1;
                    int k = aint2.length / j;
                    int l = j << 1;

                    for(int i1 = 0; i1 < j; ++i1) {
                        for(int j1 = 0; j1 < k; ++j1) {
                            int k1 = 2 * (i1 + j1 * l);
                            aint2[i1 + j1 * j] = blendColors(aint1[k1 + 0], aint1[k1 + 1], aint1[k1 + 0 + l], aint1[k1 + 1 + l], flag);
                        }
                    }

                    aint[l1] = aint2;
                }
            }
        }

        return aint;
    }

    private static int blendColors(int p_blendColors_0_, int p_blendColors_1_, int p_blendColors_2_, int p_blendColors_3_, boolean p_blendColors_4_) {
        if (!p_blendColors_4_) {
            int i1 = blendColorComponent(p_blendColors_0_, p_blendColors_1_, p_blendColors_2_, p_blendColors_3_, 24);
            int j1 = blendColorComponent(p_blendColors_0_, p_blendColors_1_, p_blendColors_2_, p_blendColors_3_, 16);
            int k1 = blendColorComponent(p_blendColors_0_, p_blendColors_1_, p_blendColors_2_, p_blendColors_3_, 8);
            int l1 = blendColorComponent(p_blendColors_0_, p_blendColors_1_, p_blendColors_2_, p_blendColors_3_, 0);
            return i1 << 24 | j1 << 16 | k1 << 8 | l1;
        } else {
            mipmapBuffer[0] = p_blendColors_0_;
            mipmapBuffer[1] = p_blendColors_1_;
            mipmapBuffer[2] = p_blendColors_2_;
            mipmapBuffer[3] = p_blendColors_3_;
            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.0F;

            int i2;
            for(i2 = 0; i2 < 4; ++i2) {
                if (mipmapBuffer[i2] >> 24 != 0) {
                    f += (float)Math.pow((double)((float)(mipmapBuffer[i2] >> 24 & 255) / 255.0F), 2.2D);
                    f1 += (float)Math.pow((double)((float)(mipmapBuffer[i2] >> 16 & 255) / 255.0F), 2.2D);
                    f2 += (float)Math.pow((double)((float)(mipmapBuffer[i2] >> 8 & 255) / 255.0F), 2.2D);
                    f3 += (float)Math.pow((double)((float)(mipmapBuffer[i2] >> 0 & 255) / 255.0F), 2.2D);
                }
            }

            f /= 4.0F;
            f1 /= 4.0F;
            f2 /= 4.0F;
            f3 /= 4.0F;
            i2 = (int)(Math.pow((double)f, 0.45454545454545453D) * 255.0D);
            int j = (int)(Math.pow((double)f1, 0.45454545454545453D) * 255.0D);
            int k = (int)(Math.pow((double)f2, 0.45454545454545453D) * 255.0D);
            int l = (int)(Math.pow((double)f3, 0.45454545454545453D) * 255.0D);
            if (i2 < 96) {
                i2 = 0;
            }

            return i2 << 24 | j << 16 | k << 8 | l;
        }
    }

    private static int blendColorComponent(int p_blendColorComponent_0_, int p_blendColorComponent_1_, int p_blendColorComponent_2_, int p_blendColorComponent_3_, int p_blendColorComponent_4_) {
        float f = (float)Math.pow((double)((float)(p_blendColorComponent_0_ >> p_blendColorComponent_4_ & 255) / 255.0F), 2.2D);
        float f1 = (float)Math.pow((double)((float)(p_blendColorComponent_1_ >> p_blendColorComponent_4_ & 255) / 255.0F), 2.2D);
        float f2 = (float)Math.pow((double)((float)(p_blendColorComponent_2_ >> p_blendColorComponent_4_ & 255) / 255.0F), 2.2D);
        float f3 = (float)Math.pow((double)((float)(p_blendColorComponent_3_ >> p_blendColorComponent_4_ & 255) / 255.0F), 2.2D);
        float f4 = (float)Math.pow((double)(f + f1 + f2 + f3) * 0.25D, 0.45454545454545453D);
        return (int)((double)f4 * 255.0D);
    }

    public static void uploadTextureMipmap(int[][] p_uploadTextureMipmap_0_, int p_uploadTextureMipmap_1_, int p_uploadTextureMipmap_2_, int p_uploadTextureMipmap_3_, int p_uploadTextureMipmap_4_, boolean p_uploadTextureMipmap_5_, boolean p_uploadTextureMipmap_6_) {
        for(int i = 0; i < p_uploadTextureMipmap_0_.length; ++i) {
            int[] aint = p_uploadTextureMipmap_0_[i];
            uploadTextureSub(i, aint, p_uploadTextureMipmap_1_ >> i, p_uploadTextureMipmap_2_ >> i, p_uploadTextureMipmap_3_ >> i, p_uploadTextureMipmap_4_ >> i, p_uploadTextureMipmap_5_, p_uploadTextureMipmap_6_, p_uploadTextureMipmap_0_.length > 1);
        }

    }

    private static void uploadTextureSub(int p_uploadTextureSub_0_, int[] p_uploadTextureSub_1_, int p_uploadTextureSub_2_, int p_uploadTextureSub_3_, int p_uploadTextureSub_4_, int p_uploadTextureSub_5_, boolean p_uploadTextureSub_6_, boolean p_uploadTextureSub_7_, boolean p_uploadTextureSub_8_) {
        int i = 4194304 / p_uploadTextureSub_2_;
        setTextureBlurMipmap(p_uploadTextureSub_6_, p_uploadTextureSub_8_);
        setTextureClamped(p_uploadTextureSub_7_);

        int l;
        for(int j = 0; j < p_uploadTextureSub_2_ * p_uploadTextureSub_3_; j += p_uploadTextureSub_2_ * l) {
            int k = j / p_uploadTextureSub_2_;
            l = Math.min(i, p_uploadTextureSub_3_ - k);
            int i1 = p_uploadTextureSub_2_ * l;
            copyToBufferPos(p_uploadTextureSub_1_, j, i1);
            GL11.glTexSubImage2D(3553, p_uploadTextureSub_0_, p_uploadTextureSub_4_, p_uploadTextureSub_5_ + k, p_uploadTextureSub_2_, l, 32993, 33639, dataBuffer);
        }

    }

    public static int uploadTextureImageAllocate(int p_uploadTextureImageAllocate_0_, BufferedImage p_uploadTextureImageAllocate_1_, boolean p_uploadTextureImageAllocate_2_, boolean p_uploadTextureImageAllocate_3_) {
        allocateTexture(p_uploadTextureImageAllocate_0_, p_uploadTextureImageAllocate_1_.getWidth(), p_uploadTextureImageAllocate_1_.getHeight());
        return uploadTextureImageSub(p_uploadTextureImageAllocate_0_, p_uploadTextureImageAllocate_1_, 0, 0, p_uploadTextureImageAllocate_2_, p_uploadTextureImageAllocate_3_);
    }

    public static void allocateTexture(int p_allocateTexture_0_, int p_allocateTexture_1_, int p_allocateTexture_2_) {
        allocateTextureImpl(p_allocateTexture_0_, 0, p_allocateTexture_1_, p_allocateTexture_2_);
    }

    public static void allocateTextureImpl(int p_allocateTextureImpl_0_, int p_allocateTextureImpl_1_, int p_allocateTextureImpl_2_, int p_allocateTextureImpl_3_) {
//        Class var4 = SplashProgress.class;
//        synchronized(SplashProgress.class) {
//            deleteTexture(p_allocateTextureImpl_0_);
//            bindTexture(p_allocateTextureImpl_0_);
//        }

        if (p_allocateTextureImpl_1_ >= 0) {
            GL11.glTexParameteri(3553, 33085, p_allocateTextureImpl_1_);
            GL11.glTexParameterf(3553, 33082, 0.0F);
            GL11.glTexParameterf(3553, 33083, (float)p_allocateTextureImpl_1_);
            GL11.glTexParameterf(3553, 34049, 0.0F);
        }

        for(int i = 0; i <= p_allocateTextureImpl_1_; ++i) {
            GL11.glTexImage2D(3553, i, 6408, p_allocateTextureImpl_2_ >> i, p_allocateTextureImpl_3_ >> i, 0, 32993, 33639, (IntBuffer)null);
        }

    }

    public static int uploadTextureImageSub(int p_uploadTextureImageSub_0_, BufferedImage p_uploadTextureImageSub_1_, int p_uploadTextureImageSub_2_, int p_uploadTextureImageSub_3_, boolean p_uploadTextureImageSub_4_, boolean p_uploadTextureImageSub_5_) {
        bindTexture(p_uploadTextureImageSub_0_);
        uploadTextureImageSubImpl(p_uploadTextureImageSub_1_, p_uploadTextureImageSub_2_, p_uploadTextureImageSub_3_, p_uploadTextureImageSub_4_, p_uploadTextureImageSub_5_);
        return p_uploadTextureImageSub_0_;
    }

    private static void uploadTextureImageSubImpl(BufferedImage p_uploadTextureImageSubImpl_0_, int p_uploadTextureImageSubImpl_1_, int p_uploadTextureImageSubImpl_2_, boolean p_uploadTextureImageSubImpl_3_, boolean p_uploadTextureImageSubImpl_4_) {
        int i = p_uploadTextureImageSubImpl_0_.getWidth();
        int j = p_uploadTextureImageSubImpl_0_.getHeight();
        int k = 4194304 / i;
        int[] aint = new int[k * i];
        setTextureBlurred(p_uploadTextureImageSubImpl_3_);
        setTextureClamped(p_uploadTextureImageSubImpl_4_);

        for(int l = 0; l < i * j; l += i * k) {
            int i1 = l / i;
            int j1 = Math.min(k, j - i1);
            int k1 = i * j1;
            p_uploadTextureImageSubImpl_0_.getRGB(0, i1, i, j1, aint, 0, i);
            copyToBuffer(aint, k1);
            GL11.glTexSubImage2D(3553, 0, p_uploadTextureImageSubImpl_1_, p_uploadTextureImageSubImpl_2_ + i1, i, j1, 32993, 33639, dataBuffer);
        }

    }

    private static void setTextureClamped(boolean p_setTextureClamped_0_) {
        if (p_setTextureClamped_0_) {
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
        } else {
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
        }

    }

    private static void setTextureBlurred(boolean p_setTextureBlurred_0_) {
        setTextureBlurMipmap(p_setTextureBlurred_0_, false);
    }

    private static void setTextureBlurMipmap(boolean p_setTextureBlurMipmap_0_, boolean p_setTextureBlurMipmap_1_) {
        if (p_setTextureBlurMipmap_0_) {
            GL11.glTexParameteri(3553, 10241, p_setTextureBlurMipmap_1_ ? 9987 : 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
        } else {
            GL11.glTexParameteri(3553, 10241, p_setTextureBlurMipmap_1_ ? 9986 : 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
        }

    }

    private static void copyToBuffer(int[] p_copyToBuffer_0_, int p_copyToBuffer_1_) {
        copyToBufferPos(p_copyToBuffer_0_, 0, p_copyToBuffer_1_);
    }

    private static void copyToBufferPos(int[] p_copyToBufferPos_0_, int p_copyToBufferPos_1_, int p_copyToBufferPos_2_) {
        int[] aint = p_copyToBufferPos_0_;
//        if (Minecraft.getInstance().gameSettings.anaglyph) {
//            aint = updateAnaglyph(p_copyToBufferPos_0_);
//        }

        dataBuffer.clear();
        dataBuffer.put(aint, p_copyToBufferPos_1_, p_copyToBufferPos_2_);
        dataBuffer.position(0).limit(p_copyToBufferPos_2_);
    }

    static void bindTexture(int p_bindTexture_0_) {
        RenderSystem.bindTexture(p_bindTexture_0_);
    }

    public static int[] readImageData(IResourceManager p_readImageData_0_, ResourceLocation p_readImageData_1_) throws IOException {
        BufferedImage bufferedimage = readBufferedImage(p_readImageData_0_.getResource(p_readImageData_1_).getInputStream());
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int[] aint = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
        return aint;
    }

    public static BufferedImage readBufferedImage(InputStream p_readBufferedImage_0_) throws IOException {
        BufferedImage bufferedimage;
        try {
            bufferedimage = ImageIO.read(p_readBufferedImage_0_);
        } finally {
            IOUtils.closeQuietly(p_readBufferedImage_0_);
        }

        return bufferedimage;
    }

    public static int[] updateAnaglyph(int[] p_updateAnaglyph_0_) {
        int[] aint = new int[p_updateAnaglyph_0_.length];

        for(int i = 0; i < p_updateAnaglyph_0_.length; ++i) {
            aint[i] = anaglyphColor(p_updateAnaglyph_0_[i]);
        }

        return aint;
    }

    public static int anaglyphColor(int p_anaglyphColor_0_) {
        int i = p_anaglyphColor_0_ >> 24 & 255;
        int j = p_anaglyphColor_0_ >> 16 & 255;
        int k = p_anaglyphColor_0_ >> 8 & 255;
        int l = p_anaglyphColor_0_ & 255;
        int i1 = (j * 30 + k * 59 + l * 11) / 100;
        int j1 = (j * 30 + k * 70) / 100;
        int k1 = (j * 30 + l * 70) / 100;
        return i << 24 | i1 << 16 | j1 << 8 | k1;
    }

    public static void processPixelValues(int[] p_processPixelValues_0_, int p_processPixelValues_1_, int p_processPixelValues_2_) {
        int[] aint = new int[p_processPixelValues_1_];
        int i = p_processPixelValues_2_ / 2;

        for(int j = 0; j < i; ++j) {
            System.arraycopy(p_processPixelValues_0_, j * p_processPixelValues_1_, aint, 0, p_processPixelValues_1_);
            System.arraycopy(p_processPixelValues_0_, (p_processPixelValues_2_ - 1 - j) * p_processPixelValues_1_, p_processPixelValues_0_, j * p_processPixelValues_1_, p_processPixelValues_1_);
            System.arraycopy(aint, 0, p_processPixelValues_0_, (p_processPixelValues_2_ - 1 - j) * p_processPixelValues_1_, p_processPixelValues_1_);
        }

    }

    static {
        int i = -16777216;
        int j = -524040;
        int[] aint = new int[]{-524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040};
        int[] aint1 = new int[]{-16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216};
        int k = aint.length;

        missingTexture.updateDynamicTexture();
        mipmapBuffer = new int[4];
    }
}
