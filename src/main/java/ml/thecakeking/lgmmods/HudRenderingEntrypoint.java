package ml.thecakeking.lgmmods;

import ml.thecakeking.lgmmods.Model.CooldownEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderTickCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class HudRenderingEntrypoint
{
    private static final Map<String, CooldownEntry> COOLDOWNS = new ConcurrentHashMap<>();


    public static void render(DrawContext context, RenderTickCounter renderTickCounter)
    {
        //context.drawText(MinecraftClient.getInstance().textRenderer, "Yellow Dragon HP: 00000" , 900, 500, 0xFFFFFF00, false);
        //context.drawText(MinecraftClient.getInstance().textRenderer, "Yellow Dragon HP: 11111" , 900, 408, 0xFFFFFF00, false);
        //context.drawText(MinecraftClient.getInstance().textRenderer, "Yellow Dragon HP: 22222" , 800, 350, 0xFFFFFF00, false);
        //context.drawText(MinecraftClient.getInstance().textRenderer, "Yellow Dragon HP: 33333" , 500, 500, 0xFFFFFF00, false);
        //context.drawText(MinecraftClient.getInstance().textRenderer, "Yellow Dragon HP: 44444" , 400, 308, 0xFFFFFF00, false);
        //context.drawText(MinecraftClient.getInstance().textRenderer, "Yellow Dragon HP: 55555" , 250, 258, 0xFFFFFF00, false);

        if (LGMSkyblockMod.modtestOn) {
            double x = MinecraftClient.getInstance().mouse.getX();
            double y = MinecraftClient.getInstance().mouse.getY();

            context.drawText(MinecraftClient.getInstance().textRenderer, "X: "+ x+" Y: "+y , 200, 208, 0xFFFFFF00, false);
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || COOLDOWNS.isEmpty()) return;

        int x = 1000; // Starting X position
        int y = 64; // Starting Y position (adjust based on other HUD elements) Need to be able to chance it inside a config screen
        long now = System.currentTimeMillis();

        COOLDOWNS.entrySet().removeIf(entry -> entry.getValue().getRemaining(now) <= 0);


        for (CooldownEntry entry : COOLDOWNS.values()) {
            float remaining = entry.getRemaining(now);
            String text = String.format("%s: %.1fs", entry.name(), remaining);

            context.drawText(client.textRenderer, text, x, y, 0xFF0000FF, false);

            y += 12; // Move down for the next entry
        }


    }

    /**
     * @param id A unique key for this cooldown (e.g., "Spirit_Mask")
     * @param displayName What we display on the hud
     * @param durationSeconds How long the cooldown lasts
     */
    public static void addEntryToCooldownDisplay(String id, String displayName, float durationSeconds) {
        long expiry = System.currentTimeMillis() + (long)(durationSeconds * 1000);
        COOLDOWNS.put(id, new CooldownEntry(displayName, expiry, durationSeconds));
        LGMSkyblockMod.LOGGER.info("int x: " + MinecraftClient.getInstance().getWindow().getWidth()/2 + " y: " + MinecraftClient.getInstance().getWindow().getHeight()/2);
    }
}
