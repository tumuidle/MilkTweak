package me.h3xadecimal.milktweak.mixin.patch.camera;

import de.maxhenkel.camera.ClientImageUploadManager;
import de.maxhenkel.camera.ImageTools;
import de.maxhenkel.camera.Main;
import de.maxhenkel.camera.corelib.inventory.ScreenBase;
import de.maxhenkel.camera.gui.CameraScreen;
import de.maxhenkel.camera.net.MessageRequestUploadCustomImage;
import me.h3xadecimal.milktweak.MilkTweak;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * 仅尝试修复CameraMod右键下蹲崩端问题
 * 原报错信息：
 * java.lang.NullPointerException: Ticking screen
 *  at de.maxhenkel.camera.gui.CameraScreen.func_231023_e_(CameraScreen.java:93) ~[?:1.16.5-1.0.14] {re:classloading}
 *  ......
 */
@Mixin(value = CameraScreen.class, remap = false)
public abstract class MixinCameraScreen extends ScreenBase<Container> {
    @Shadow private Button upload;

    public MixinCameraScreen(ResourceLocation texture, Container container, PlayerInventory playerInventory, ITextComponent title) {
        super(texture, container, playerInventory, title);
    }

    @Inject(method = "func_231160_c_", at = @At("TAIL"), remap = false)
    public void func_231160_c_(CallbackInfo ci) {
        this.upload = addButton(new Button(this.guiLeft + this.xSize / 2 - 35, this.guiTop + this.ySize - 20 - 10, 70, 20, new TranslationTextComponent("button.camera.upload"), (button) -> {
            ImageTools.chooseImage((file) -> {
                try {
                    try {
                        UUID uuid = UUID.randomUUID();
                        BufferedImage image = ImageTools.loadImage(file);
                        ClientImageUploadManager.addImage(uuid, image);
                        Main.SIMPLE_CHANNEL.sendToServer(new MessageRequestUploadCustomImage(uuid));
                    } catch (IOException var4) {
                        this.minecraft.player.sendStatusMessage(new TranslationTextComponent("message.upload_error", new Object[]{var4.getMessage()}), true);
                        var4.printStackTrace();
                    }

                    this.minecraft.currentScreen = null;
                } catch (Throwable t) {
                    MilkTweak.LOGGER.error("Exception in patched mod", t);
                }
            });
        }));
    }

    /**
     * @author H3xadecimal
     * @reason Patch
     */
    @Overwrite(remap = false)
    public void func_231023_e_() {
        super.tick();
        if (this.upload != null) {
            this.upload.active = !ImageTools.isFileChooserOpen();
        }
    }
}
