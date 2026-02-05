package ml.thecakeking.lgmmods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;



/// BlockPerSecondTracker
public class BpsTracker {

    private static int blocksThisSecond = 0;

    public static void onBlockBroken() {
        blocksThisSecond++;    }

    public static void tick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        if (client.world.getTime() % 20 == 0) {
            client.player.sendMessage(
                    Text.literal("BPS: " + blocksThisSecond),
                    true
            );
            blocksThisSecond = 0;
        }
    }
}
