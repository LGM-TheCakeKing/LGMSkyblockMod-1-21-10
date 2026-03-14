package ml.thecakeking.lgmmods;

import ml.thecakeking.lgmmods.Model.WorldAreasNames;
import ml.thecakeking.lgmmods.chatmanager.ChatManager;
import ml.thecakeking.lgmmods.chatmanager.TriggerConfigHandler;
import ml.thecakeking.lgmmods.chatmanager.TriggerConfigScreen;
import ml.thecakeking.lgmmods.chatmanager.TriggerManager;
import ml.thecakeking.lgmmods.dungeonhelper.DragonHelper;
import ml.thecakeking.lgmmods.dungeonhelper.DungeonUtils;
import ml.thecakeking.lgmmods.dungeonhelper.MiniMapRender;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LGMSkyblockMod implements ModInitializer {
	public static final String MOD_ID = "lgmskyblockmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static boolean modtestOn = false;

    public static Boolean bpsTrackerOn = false;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

        TriggerConfigHandler.load();
        LOGGER.info(TriggerManager.triggers.size() + " triggers loaded");
        register();

        ChatManager.register("Second Wind Activated! Your Spirit Mask saved your life!", (msg) ->
        {
            LGMSkyblockMod.LOGGER.info("#Second Wind Activated! Your life has been saved, please show cooldown now!¤");
            HudRenderingEntrypoint.addEntryToCooldownDisplay("Spirit_Mask", "Spirit mask is on cooldown", 30);
        });
        ChatManager.register("Your ⚚ Bonzo's Mask saved your life!", (msg)  -> {
            HudRenderingEntrypoint.addEntryToCooldownDisplay("Bonzo_Mask", "Bonzo mask is on cooldown", 198);
        });
        ChatManager.register("Your Phoenix Pet saved you from certain death!", (msg)  -> {
            HudRenderingEntrypoint.addEntryToCooldownDisplay("Phoenix_Pet", "Phoenix pet is on cooldown", 60);
        });

        //TODO Make a class for this stuff so we can check more stuff in there Idk Like TickManager,
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null || client.player == null) return;

            WorldTransferrerHandler.NAMEHERECheckWorldTabLocationEveryTick(client);

            if (bpsTrackerOn) BpsTracker.tick();

            if(WorldTransferrerHandler.GetCurrentWorld().equals(WorldAreasNames.Dungeon))
            {
                DungeonUtils.CheckDungeonEveryTick(client);

                if (client.world.getTime() % 20 == 0)
                {
                    DungeonUtils.CheckDungeonStuffLessOften(client);
                }
            }
        });

        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of(LGMSkyblockMod.MOD_ID, "render"), HudRenderingEntrypoint::render);
        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of(LGMSkyblockMod.MOD_ID, "dragon"), DragonHelper::RenderDragonHeathHud);
        HudElementRegistry.addLast(Identifier.of(LGMSkyblockMod.MOD_ID, "mini/map/render"), MiniMapRender::RenderMiniMapHud);
        //HudRenderCallback.EVENT.register(new MapHudOverlay());
        ClientReceiveMessageEvents.GAME.register(ChatManager::onChatMessage);


        ClientEntityEvents.ENTITY_LOAD.register(WorldTransferrerHandler::onWorldChange);

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
            dispatcher.register(ClientCommandManager.literal("modtest").executes(context -> {
                modtestOn = !modtestOn;
                LGMSkyblockMod.LOGGER.warn("modtest is " + (modtestOn ? "ON" : "OFF"));
                return 1;
            }));
        });
    }
}