package ml.thecakeking.lgmmods.mixin;

import ml.thecakeking.lgmmods.dungeonhelper.InventoryHighlighter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandelScreenDrawMixin {

    private static int HighlightColor = 0x66FFFF00;

    @Inject(method = "drawSlot", at = @At("TAIL"))
    private void highlightItemInInventories(DrawContext context, Slot slot, CallbackInfo ci) {

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        //context.drawText(renderer, String.valueOf(slot.id), slot.x, slot.y, 0x6600FF00, false); //For debugging

        //check if terminal helper is on
        //if (!LGMSkyblockMod.TerminalHelp) return;
        //if in floor 7
        //if InventoryHighlighter on

        ItemStack stack = slot.getStack();
        if (stack.isEmpty()) return;
        if (slot.id >= 45) return;
        if (stack.getEnchantments().getSize() > 0) return;

        int x = slot.x;
        int y = slot.y;

        if (InventoryHighlighter.colorTerminal) {
            if (InventoryHighlighter.COLOR_GROUPS.get(InventoryHighlighter.lookForColor).contains(stack.getItem())) {
                context.fill(x,y, x+16,y+16,HighlightColor);
                return;
            }
        }

        if (InventoryHighlighter.nameTerminal) {
            if (stack.getItemName().getString().toLowerCase().startsWith(InventoryHighlighter.lookForLetter)) {
                context.fill(x,y, x+16,y+16,HighlightColor);
                return;
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
                    context.fill(x,y, x+16,y+16,HighlightColor);
                }
                if (stack.getCount() == InventoryHighlighter.nextIndexOfOrder+1) {
                    context.fill(x,y, x+16,y+16,HighlightColor);
                    return;
                }
            }

        }
    }
}
