package me.h3xadecimal.milktweak.mixin.client.gui.screen;

import me.h3xadecimal.milktweak.gui.GuiFunctions;
import me.h3xadecimal.milktweak.gui.GuiPrivateMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class MixinScreen extends FocusableGui {
    /**
     * @author H3xadecimal
     * @reason hook
     */
    @Overwrite
    public void sendMessage(String text, boolean addToChat) {
        if (GuiPrivateMessage.getSpecified()) {
            text = GuiPrivateMessage.handleMessage(text);
        }

        text = net.minecraftforge.event.ForgeEventFactory.onClientSendMessage(text);
        if (text.isEmpty()) return;
        if (addToChat) {
            Minecraft.getInstance().ingameGUI.getChatGUI().addToSentMessages(text);
        }

        Minecraft.getInstance().player.sendChatMessage(text);
    }
}
