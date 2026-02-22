package ml.thecakeking.lgmmods.dungeonhelper;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.BlazeEntity;

public class DungeonUtils {
    public static boolean isInBlazeRoom(MinecraftClient client)
    {
        if (client.world == null || client.player == null) return false;
        return client.world.getEntitiesByClass(BlazeEntity.class, client.player.getBoundingBox().expand(32), e -> true).size() > 2;
    }

    public static void CheckDungeonStuff(MinecraftClient client) {
        if (isInBlazeRoom(client)) {
            if (client.world.getTime() % 4 == 0) {
                BlazeSolver.Update(client.world);
            }

        }

    }

    public static boolean IsInDungeon() {
        return false;
    }
}
