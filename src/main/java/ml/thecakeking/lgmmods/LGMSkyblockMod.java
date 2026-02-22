package ml.thecakeking.lgmmods;

import ml.thecakeking.lgmmods.Model.ChatTrigger;
import ml.thecakeking.lgmmods.chattrigger.TriggerConfigHandler;
import ml.thecakeking.lgmmods.chattrigger.TriggerConfigScreen;
import ml.thecakeking.lgmmods.chattrigger.TriggerManager;
import ml.thecakeking.lgmmods.dungeonhelper.DungeonUtils;
import ml.thecakeking.lgmmods.dungeonhelper.MapHudOverlay;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LGMSkyblockMod implements ModInitializer {
	public static final String MOD_ID = "lgmskyblockmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Boolean bpsTrackerOn = false;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

        TriggerConfigHandler.load();
        LOGGER.info(TriggerManager.triggers.size() + " triggers loaded");
        register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null || client.player == null) return;

            if (bpsTrackerOn) BpsTracker.tick();

            if(DungeonUtils.IsInDungeon());
            {
                DungeonUtils.CheckDungeonStuff(client);
            }

        });

        HudRenderCallback.EVENT.register(new MapHudOverlay());
        ClientReceiveMessageEvents.GAME.register(TriggerManager::onChatMessage);



	}

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("modsettings")
                    .executes(context -> {
                        // We must delay the screen opening by one tick to avoid chat UI conflicts
                        MinecraftClient client = MinecraftClient.getInstance();
                        client.send(() -> client.setScreen(new TriggerConfigScreen()));
                        return 1;
                    }));
        });
    }
}