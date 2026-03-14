package ml.thecakeking.lgmmods;

import ml.thecakeking.lgmmods.Model.WorldAreasNames;
import ml.thecakeking.lgmmods.dungeonhelper.DungeonUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;

import java.util.Collection;

public class WorldTransferrerHandler {

    private static WorldAreasNames worldName = WorldAreasNames.PrivateIsland;
    public static WorldAreasNames GetCurrentWorld() {
        return worldName;
    }

    public static void onWorldChange(Entity entity, ClientWorld world)
    {
        if (entity instanceof ClientPlayerEntity player && player.isMainPlayer())
        {
            if (world.getRegistryKey().getValue().toString().equals("hypixel:dimension_switch"))
            {
                LGMSkyblockMod.LOGGER.info("[WorldTransferrerHandler] onWorldChange: " + world.getRegistryKey().getRegistry().toString());
                WorldChange = true;
            }
        }
    }

    private static boolean WorldChange = false;
    private static final int TICK_TO_CHECK_FOR_UPDATE = 200;
    private static final int TICK_TO_WAIT = 20;
    private static int tickCounter = 0;
    //TODO Change name of function to fit better
    public static void NAMEHERECheckWorldTabLocationEveryTick(MinecraftClient client)
    {
        if (!WorldChange) return;

        tickCounter++;
        if (tickCounter < TICK_TO_WAIT) return;

        if (tickCounter > TICK_TO_CHECK_FOR_UPDATE) {
            WorldChange = false;
            LGMSkyblockMod.LOGGER.warn("[WorldTransferrerHandler] TIMEOUT: " + tickCounter);
            tickCounter = 0;
        }

        Collection<PlayerListEntry> tabList = client.getNetworkHandler().getPlayerList(); //Checking tab for stuff

        WorldAreasNames newWorld = null;
        for (PlayerListEntry playerListEntry : tabList)
        {
            if (playerListEntry.getDisplayName() == null) continue;


            if (playerListEntry.getDisplayName().getString().contains("Dungeon Stats")) {
                newWorld =  WorldAreasNames.Dungeon;
                DungeonUtils.initializeDungeonSession();
                LGMSkyblockMod.LOGGER.info("[WorldTransferrerHandler] You have now join a Dungeon");
                break;
            }
            if (playerListEntry.getDisplayName().getString().contains("Area:")) {
                String areaName = playerListEntry.getDisplayName().getString().substring(6);
                newWorld = WorldAreasNames.valueOf(areaName.replace("'", "").replace(" ", "").trim());
                LGMSkyblockMod.LOGGER.info("[WorldTransferrerHandler] Area name: " + newWorld);
                break;
            }
        }

        if (newWorld != null) {
            worldName = newWorld;
            WorldChange = false;
            tickCounter = 0;
        }
    }
}
