package me.h3xadecimal.milktweak.mixin.client.gui.screen.inventory;

import me.h3xadecimal.milktweak.gui.GuiFunctions;
import net.minecraft.client.gui.screen.inventory.AbstractRepairScreen;
import net.minecraft.client.gui.screen.inventory.AnvilScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.play.client.CRenameItemPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public class MixinAnvilScreen extends AbstractRepairScreen<RepairContainer> {
    public MixinAnvilScreen(RepairContainer container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation guiTexture) {
        super(container, playerInventory, title, guiTexture);
    }

    /**
     * @reason TextColor
     * @author H3xadecimal
     */
    @Overwrite
    private void renameItem(String name) {
        if (!name.isEmpty()) {
            String s = name;
            if (GuiFunctions.getTextColor()) s = GuiFunctions.Companion.handleTextColor(s);
            Slot slot = this.container.getSlot(0);
            if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && name.equals(slot.getStack().getDisplayName().getString())) {
                s = "";
            }

            this.container.updateItemName(s);
            this.minecraft.player.connection.sendPacket(new CRenameItemPacket(s));
        }
    }
}
