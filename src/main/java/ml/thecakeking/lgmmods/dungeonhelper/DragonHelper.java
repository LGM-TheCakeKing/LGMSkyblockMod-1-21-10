package ml.thecakeking.lgmmods.dungeonhelper;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import ml.thecakeking.lgmmods.Model.WorldAreasNames;
import ml.thecakeking.lgmmods.WorldTransferrerHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;

import java.util.ArrayList;
import java.util.List;

public class DragonHelper
{
    private static float blueHp = 491924;
    private static float redHp = 0;
    private static float purpleHp = 491913524;
    private static float greenHp = 67523;
    private static float yellowHp = 154515;

    public static void DisplayDragonHeath(ClientWorld world) {
        List<ArmorStandEntity> dragons = new ArrayList<>();

        for (Entity e : world.getEntities()) {
            if (e instanceof ArmorStandEntity armorstand) {
                if (armorstand.getCustomName() == null) continue;
                if (!armorstand.getCustomName().getString().contains("Withered Dragon")) continue; //Dragon symbol

                String name = armorstand.getCustomName().getString();
                String dragonHp = name.substring(name.indexOf("Withered Dragon ")+16,  name.indexOf("/")).replace(",", "");

            }
        }
    }

    public static void RenderDragonHeathHud(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (!WorldTransferrerHandler.GetCurrentWorld().equals(WorldAreasNames.Dungeon)) return;
        if (!DungeonUtils.currentDungeonFloor.equals("M7")) return;

        int blueColor = 0xFF0000FF;
        int redColor = 0xFFFF0000;
        int purpleColor = 0xFFFF00FF;
        int greenColor = 0xFF00FF00;
        int yellowColor = 0xFFFFFF00;

        drawContext.drawText(MinecraftClient.getInstance().textRenderer, "Blue Dragon HP: " + blueHp, 25, 25, 0xFF0000FF, false);
        drawContext.drawText(MinecraftClient.getInstance().textRenderer, "Red Dragon HP: " + redHp, 25, 35, 0xFFFF0000, false);
        drawContext.drawText(MinecraftClient.getInstance().textRenderer, "Purple Dragon HP: " + purpleHp, 25, 45, 0xFFFF00FF, false);
        drawContext.drawText(MinecraftClient.getInstance().textRenderer, "Green Dragon HP: " + greenHp, 25, 55, 0xFF00FF00, false);
        drawContext.drawText(MinecraftClient.getInstance().textRenderer, "Yellow Dragon HP: " + yellowHp, 25, 65, 0xFFFFFF00, false);
    }
}
