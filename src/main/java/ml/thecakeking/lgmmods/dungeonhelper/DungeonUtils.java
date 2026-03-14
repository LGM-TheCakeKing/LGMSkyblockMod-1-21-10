package ml.thecakeking.lgmmods.dungeonhelper;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.*;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public class DungeonUtils
{
    public static Boolean currentlyInBossFight = false;
    public static String bossName = "";
    //public static Boolean currentlyInDungeon = false;
    public static String currentDungeonFloor;

    private final static String dungeonSymbol = "⏣";
    private final static String dungeonTeamName = "team_7";

    public static void  initializeDungeonSession()
    {
        LGMSkyblockMod.LOGGER.info("[DungeonUtils] [initializeDungeonSession] Starting");
        BlazeSolver.reset();
        MiniMapRender.reset();
        currentDungeonFloor = "";
        currentlyInBossFight = false;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.getNetworkHandler() == null || mc.world == null) return;
        Scoreboard sb = mc.world.getScoreboard();

        //TODO Need a lot of Exception Handling, this is not great but it works for now
        try {
            Team team = sb.getTeam(dungeonTeamName);

            if (team == null)
            {
                LGMSkyblockMod.LOGGER.info("[DungeonUtils] [initializeDungeonSession] Team not found");
            }
            if (team.getPrefix().getString().contains(dungeonSymbol)) {
                currentDungeonFloor = team.getSuffix().getString().substring(10, 12);
                LGMSkyblockMod.LOGGER.info("currentDungeonFloor: " + currentDungeonFloor);
            }
            else
            {
                for (ScoreboardObjective obj : sb.getObjectives())
                {
                    for (Team t : obj.getScoreboard().getTeams())
                    {
                        if (t.getPrefix().getString().contains(dungeonSymbol))
                        {
                            currentDungeonFloor = t.getSuffix().getString().substring(10, 12);
                            LGMSkyblockMod.LOGGER.info("currentDungeonFloor: " +  currentDungeonFloor + " We Found this the hard way");
                        }
                    }
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            LGMSkyblockMod.LOGGER.warn("DungeonUtils initializeDungeonSession could not find the dungeon symbol inside the team prefix:  " + sb.getTeam(dungeonTeamName).getPrefix());
        }
    }


    public static void inBossFight(String name)
    {
        LGMSkyblockMod.LOGGER.info("[DungeonUtils] You are now in a boss fight with: " + name);
        currentlyInBossFight = true;
        bossName = name;

        if (bossName.equals("Livid")) {
            LividHelper.initialize();
        }
    }

    public static boolean isInBlazeRoom(MinecraftClient client)
    {
        if (client.world == null || client.player == null) return false;
        return client.world.getEntitiesByClass(BlazeEntity.class, client.player.getBoundingBox().expand(32), e -> true).size() >= 2;
    }

    public static void CheckDungeonEveryTick(MinecraftClient client) //Better name please
    {
        if (isInBlazeRoom(client)) {
           BlazeSolver.Update(client);
        }

        if (LividHelper.bossFightStarted)
        {
            LividHelper.findRightLivid(client);
        }

        if (currentDungeonFloor.equals("M7")) {
            DragonHelper.DisplayDragonHeath(client.world);
        }
    }

    public static void CheckDungeonStuffLessOften(MinecraftClient client) //does stuff every second /20 ticks
    {


    }
}
