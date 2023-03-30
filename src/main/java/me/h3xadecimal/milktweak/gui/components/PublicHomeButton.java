package me.h3xadecimal.milktweak.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

public class PublicHomeButton extends Button {
    public PublicHomeButton(int x, int y, ITextComponent title, String homeName) {
        super(x, y, 100, 20, title, (b) -> {
            try {
                Objects.requireNonNull(Minecraft.getInstance().player).sendChatMessage("/phome " + homeName);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
