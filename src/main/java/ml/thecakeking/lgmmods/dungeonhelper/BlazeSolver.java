package ml.thecakeking.lgmmods.dungeonhelper;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import net.minecraft.client.MinecraftClient;
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
    public static ArmorStandEntity lowestBlaze =  null;
    public static ArmorStandEntity highestBlaze = null;

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

            lowestBlaze = tags.getFirst();
            highestBlaze = tags.getLast();


            String lname = lowestBlaze.getCustomName().getString();
            String hname = highestBlaze.getCustomName().getString();
            int lhp = Integer.parseInt(lname.substring(lname.indexOf("Blaze ")+6,  lname.indexOf("/")).replace(",", ""));
            int hhp = Integer.parseInt(hname.substring(hname.indexOf("Blaze ")+6,  hname.indexOf("/")).replace(",", ""));
            for (int x = 0; x < 10; x++) {
                for (int z = 0; z < 10; z++) {
                    for (int y = 0; y < 20; y++) {
                        world.addParticleClient(ParticleTypes.HAPPY_VILLAGER, lowestBlaze.getX()+0.50-(double)x/10, lowestBlaze.getY()-(double) y/10, lowestBlaze.getZ()+0.50-(double) z/10, 0.0D, 0.0D, 0.0D);
                        world.addParticleClient(ParticleTypes.FLAME, highestBlaze.getX()+0.50-(double)x/10, highestBlaze.getY()-(double) y/10, highestBlaze.getZ()+0.50-(double) z/10, 0.0D, 0.0D, 0.0D);
                    }
                }
            }

        }
    }
}
