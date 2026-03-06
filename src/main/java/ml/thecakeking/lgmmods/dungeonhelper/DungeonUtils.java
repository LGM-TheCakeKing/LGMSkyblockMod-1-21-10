package ml.thecakeking.lgmmods.dungeonhelper;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.scoreboard.*;

import java.util.Collection;

public class DungeonUtils {
    private final static String dungeonSymbol = "⏣";
    private final static String dungeonTeamName = "team_7";

    public static Boolean currentlyInDungeon = false;
    public static String currentDungeonFloor = "";

    public static void  initializeDungeonSession() {
        if (currentlyInDungeon) return;
        LGMSkyblockMod.LOGGER.info("DungeonUtils initializeDungeonSession");
        currentlyInDungeon = true;
        BlazeSolver.reset();

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.getNetworkHandler() == null || mc.world == null) return;
        Scoreboard sb = mc.world.getScoreboard();



        //TODO Need a lot of Exception Handling, this is not great but it works for now
        try {
            Team team = sb.getTeam(dungeonTeamName);
            if (!team.getPrefix().getString().contains(dungeonSymbol)) throw new RuntimeException();

            currentDungeonFloor = team.getSuffix().getString().substring(10, 12);
            LGMSkyblockMod.LOGGER.info("currentDungeonFloor: " + currentDungeonFloor);
        }
        catch (Exception e)
        {
            LGMSkyblockMod.LOGGER.warn("DungeonUtils initializeDungeonSession could not find the dungeon symbol inside the team prefix:  " + sb.getTeam(dungeonTeamName).getPrefix());

            for (ScoreboardObjective obj : sb.getObjectives())
            {
                for (Team team : obj.getScoreboard().getTeams())
                {
                    if (team.getPrefix().getString().contains(dungeonSymbol))
                    {
                        currentDungeonFloor = team.getSuffix().getString().substring(10, 12);
                    }
                }
            }
        }
    }

    public static boolean isInBlazeRoom(MinecraftClient client)
    {
        if (client.world == null || client.player == null) return false;
        return client.world.getEntitiesByClass(BlazeEntity.class, client.player.getBoundingBox().expand(32), e -> true).size() >= 2;
    }

    public static void CheckDungeonStuff(MinecraftClient client) {
        if (isInBlazeRoom(client)) {
            if (client.world.getTime() % 2 == 0) {
                BlazeSolver.Update(client.world);
            }

        }

    }
}
