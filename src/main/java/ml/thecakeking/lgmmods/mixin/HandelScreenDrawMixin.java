package ml.thecakeking.lgmmods.mixin;

import ml.thecakeking.lgmmods.InventoryHighlighter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MinecartItem;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandelScreenDrawMixin {
    @Inject(method = "drawSlot", at = @At("TAIL"))
    private void highlightItemInInventories(DrawContext context, Slot slot, CallbackInfo ci) {

        //check if terminal helper is on
        //if (!LGMSkyblockMod.TerminalHelp) return;

        ItemStack stack = slot.getStack();
        if (stack.isEmpty()) return;

        int x = slot.x;
        int y = slot.y;

        if (InventoryHighlighter.colorTerminal) {
            if (stack.getItemName().getString().toLowerCase().contains(InventoryHighlighter.lookForColor)) {
                context.fill(x,y, x+16,y+16,0x66FFFF00);
            }
        }

        if (InventoryHighlighter.nameTerminal) {
            if (stack.getItemName().getString().toLowerCase().startsWith(InventoryHighlighter.lookForLetter)) {
                context.fill(x,y, x+16,y+16,0x66FFFF00);
            }
        }

        if (InventoryHighlighter.orderTerminal) {
            if (stack.isOf(Items.LIME_STAINED_GLASS_PANE)) {
                if (stack.getCount() == InventoryHighlighter.nextIndexOfOrder)
                {
                    InventoryHighlighter.nextIndexOfOrder = stack.getCount()+1;
                }
            }
            if (stack.isOf(Items.RED_STAINED_GLASS_PANE)) {
                if (stack.getCount() == InventoryHighlighter.nextIndexOfOrder) {
                    context.fill(x,y, x+16,y+16,0x66FFFF00);
                }
                if (stack.getCount() == InventoryHighlighter.nextIndexOfOrder+1) {
                    context.fill(x,y, x+16,y+16,0x66FFAA00);
                }
            }

        }
    }
}
