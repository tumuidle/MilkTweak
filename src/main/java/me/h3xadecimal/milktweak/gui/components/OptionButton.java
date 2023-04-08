package me.h3xadecimal.milktweak.gui.components;

import me.h3xadecimal.milktweak.MilkTweak;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class OptionButton extends Button {
    private final String displayName;
    private boolean state;

    public OptionButton(int x, int y, int width, int height, String displayName, IPressable press) {
        this(x, y, width, height, displayName, press, Button.EMPTY_TOOLTIP);
        setMessage(new StringTextComponent(displayName + ": " + getConfigStateString()));
    }

    public OptionButton(int x, int y, int width, int height, String displayName, IPressable press, ITooltip tooltip) {
        super(x, y, width, height, new StringTextComponent(displayName), press, tooltip);
        this.displayName = displayName;
        setMessage(new StringTextComponent(displayName + ": " + getConfigStateString()));
    }

    public OptionButton(int x, int y, int width, int height, String displayName) {
        this(x, y, width, height, displayName, (b) -> {});
    }

    @Override
    public void onPress() {
        state = !state;
        setMessage(new StringTextComponent(displayName + ": " + getConfigStateString()));
        super.onPress();
    }

    public boolean getConfigState() {
        return state;
    }

    public void setConfigState(boolean state) {
        if (this.state!= state) {onPress();}
    }

    private String getConfigStateString() {
        if (getConfigState()) {
            return "§a启用";
        } else {
            return "§c禁用";
        }
    }
}
