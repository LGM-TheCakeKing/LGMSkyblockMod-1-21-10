package ml.thecakeking.lgmmods.dungeonhelper;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BlazeSolver
{
    public static ArmorStandEntity target =  null;
    public static ArmorStandEntity nextTarget = null;

    public static boolean highToLow = false;
    public static boolean lowToHigh = false;

    private static final List<Particle> targetParticles = new ArrayList<>();
    private static final List<Particle> nextTargetParticles = new ArrayList<>();

    public static void Update(ClientWorld world) {
        List<ArmorStandEntity> tags = new ArrayList<>();
        MinecraftClient client = MinecraftClient.getInstance();

        for (Entity e : client.world.getEntities()) {
            if (e instanceof ArmorStandEntity armorstand) {
                if (armorstand.getCustomName() == null) continue;
                if (!armorstand.getCustomName().getString().contains("Blaze")) continue;
                tags.add(armorstand);
            }
        }

        if (!tags.isEmpty()) {

            tags.sort(Comparator.comparingInt(armorstand -> {
                try {
                    String name = armorstand.getCustomName().getString();
                    String hp = name.substring(name.indexOf("Blaze ")+6,  name.indexOf("/")).replace(",", "");
                    return Integer.parseInt(hp.trim());
                } catch (Exception e) {
                    return 0;
                }
            }));

            if (!highToLow && !lowToHigh) {
                double yAvg = 0;
                for (ArmorStandEntity armorstand : tags) {
                    yAvg += armorstand.getY();
                }

                if (yAvg / tags.size() > 69)
                    lowToHigh = true;
                else
                    highToLow = true;
            }


            try {
                if (highToLow) {
                    target = tags.getLast();
                    nextTarget = tags.get(tags.size() - 2);
                } else {
                    target = tags.getFirst();
                    nextTarget = tags.get(1);
                }
            } catch (Exception e) {}


            for (Particle  particle : targetParticles)
                particle.markDead();
            for (Particle  particle : nextTargetParticles)
                particle.markDead();

            for (int x = 0; x < 10; x+=2) {
                for (int z = 0; z < 10; z+=2) {
                    for (int y = 0; y < 20; y+=2) {
                        targetParticles.add(client.particleManager.addParticle(ParticleTypes.HAPPY_VILLAGER, target.getX()+0.50-(double)x/10, target.getY()-(double) y/10, target.getZ()+0.50-(double) z/10, 0.0D, 0.0D, 0.0D));
                        nextTargetParticles.add(client.particleManager.addParticle(ParticleTypes.WAX_ON, nextTarget.getX()+0.50-(double)x/10, nextTarget.getY()-(double) y/10, nextTarget.getZ()+0.50-(double) z/10, 0.0D, 0.0D, 0.0D));
                    }
                }
            }

        }
    }

    public static void reset() {
        highToLow = false;
        lowToHigh = false;
    }
}
