package ml.thecakeking.lgmmods;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LGMSkyblockMod implements ModInitializer {
	public static final String MOD_ID = "lgmskyblockmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");



        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null) {
                BpsTracker.tick();
            }
        });
	}
}