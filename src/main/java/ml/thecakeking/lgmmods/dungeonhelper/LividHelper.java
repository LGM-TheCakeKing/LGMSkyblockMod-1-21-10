package ml.thecakeking.lgmmods.dungeonhelper;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import ml.thecakeking.lgmmods.chatmanager.ChatManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LividHelper
{
    private static final Map<Block, String> mapBlocks = Map.of(
            Blocks.WHITE_WOOL, "Vendetta",
            Blocks.MAGENTA_WOOL, "Crossed",
            Blocks.YELLOW_WOOL, "Arcade",
            Blocks.LIME_WOOL, "Smile",
            Blocks.GRAY_WOOL, "Doctor",
            Blocks.PURPLE_WOOL, "Purple",
            Blocks.GREEN_WOOL, "Frog",
            Blocks.BLUE_WOOL, "Scream",
            Blocks.RED_WOOL, "Hockey"
    );

    private static final String LividDialogWhenStartingBossFight ="[BOSS] Livid: I respect you for making it to here, but I'll be your undoing.";
    private static final String LividDialogWhenEndingBossFight ="[BOSS] Purple Livid: If you think this is the end, you are so wrong!";
    public static boolean bossFightStarted = false;
    public static void initialize()
    {
        ChatManager.register(LividDialogWhenStartingBossFight, (msg) -> {bossFightStarted = true; LGMSkyblockMod.LOGGER.info("WOWOWOWOWOOWOW, Livid initialize register chat message has been found!");});
        ChatManager.register(LividDialogWhenEndingBossFight, (msg) -> {
            bossFightStarted = false;
            LGMSkyblockMod.LOGGER.info("WOWOWOWOWOOWOW, Livid has been defiected, it should now not look for him anymore?!\"");
            ChatManager.RemoveRegister(LividDialogWhenStartingBossFight);
            ChatManager.RemoveRegister(LividDialogWhenEndingBossFight);
        });
    }


    public static void findRightLivid(MinecraftClient client)
    {
        BlockPos targetBlockPos = new BlockPos(5, 108, 43);
        Block block = client.world.getBlockState(targetBlockPos).getBlock();

        String targetLividName = mapBlocks.get(block);
        if (targetLividName == null)
        {
            LGMSkyblockMod.LOGGER.info("[LividHelper] [findRightLivid] Target Livid Name is null?, Block: " + block.getName().toString());
            return; // If we are target a block there is not in the map, fx when we are done with the boss fight
        }

        for (Entity e : client.world.getEntities()) {
            if (e instanceof PlayerLikeEntity playerLikeEntity) {

                if (playerLikeEntity.getName() == null) continue;
                if (playerLikeEntity.getName().getString() == null) continue;
                if (playerLikeEntity.getName().getString().contains(targetLividName)) {
                    SpawnParticleOverLivid(client, playerLikeEntity);
                    return;
                }
            }
        }
    }

    private static final List<Particle> lividParticles = new ArrayList<>();
    private static void SpawnParticleOverLivid(MinecraftClient client, PlayerLikeEntity livid)
    {
        for (Particle  particle : lividParticles)
            particle.markDead();

        livid.setGlowing(true);
        BlockPos poc = livid.getBlockPos();

        for (int x = 0; x < 10; x+=2) {
            for (int z = 0; z < 10; z+=2) {
                for (int y = 0; y < 20; y+=2) {
                    lividParticles.add(client.particleManager.addParticle(ParticleTypes.HAPPY_VILLAGER, poc.getX()+0.50-(double)x/10, poc.getY()+2-(double) y/10, poc.getZ()+0.50-(double) z/10, 0.0D, 0.0D, 0.0D));
                }
            }
        }

        for  (int y = 0; y < 10; y++) {
            lividParticles.add(client.particleManager.addParticle(ParticleTypes.WAX_ON, poc.getX(), poc.getY() + y, poc.getZ(), 0.0D, 0.0D, 0.0D));
        }
    }
}
