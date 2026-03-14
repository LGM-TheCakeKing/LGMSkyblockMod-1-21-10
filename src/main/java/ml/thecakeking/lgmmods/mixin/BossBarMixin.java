package ml.thecakeking.lgmmods.mixin;

import ml.thecakeking.lgmmods.Model.WorldAreasNames;
import ml.thecakeking.lgmmods.WorldTransferrerHandler;
import ml.thecakeking.lgmmods.dungeonhelper.DungeonUtils;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BossBar.class)
public class BossBarMixin
{
    @Unique
    private final String[] bossNames = { "Bonzo","Scarf", "The Professor", "Thorn", "Livid", "Sadan", "Maxor", "Storm", "Goldor", "Necron"};

    @Inject(method = "getName", at = @At("TAIL"))
    public void BossBarName(CallbackInfoReturnable<Text> cir)
    {
        if (!WorldTransferrerHandler.GetCurrentWorld().equals(WorldAreasNames.Dungeon)) return;
        if (DungeonUtils.currentlyInBossFight) return; //We got the boss bar name so we don't need to check for it twice.

        for (String bossName : bossNames) {
            if (cir.getReturnValue().getLiteralString().contains(bossName)) {
                DungeonUtils.inBossFight(bossName);
            }
        }
    }
}
