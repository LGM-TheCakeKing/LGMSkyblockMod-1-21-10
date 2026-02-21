package ml.thecakeking.lgmmods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static ml.thecakeking.lgmmods.LGMSkyblockMod.bpsTrackerOn;


/// BlockPerSecondTracker
public class BpsTracker {

    private static int blocksThisSecond = 0;

    public static void onBlockBroken() {
        blocksThisSecond++;    }

    public static void tick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null || !bpsTrackerOn) return;

        if (client.world.getTime() % 20 == 0) {
            client.player.sendMessage(
                    Text.literal("BPS: " + blocksThisSecond),
                    true
            );
            blocksThisSecond = 0;
        }
    }
}
