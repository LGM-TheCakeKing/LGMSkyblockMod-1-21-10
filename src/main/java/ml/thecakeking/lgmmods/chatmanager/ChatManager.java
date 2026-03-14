package ml.thecakeking.lgmmods.chatmanager;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class ChatManager
{
    private static final Map<String, MessageHandler> registry = new HashMap<>();



    public static void register(String trigger, MessageHandler handler) {
        registry.put(trigger, handler);
        LGMSkyblockMod.LOGGER.info("[ChatManager] Registered trigger '" + trigger + "'");
    }

    public static void onChatMessage(Text text, boolean b)
    {
        String cleanMessage = text.getString();
        if (cleanMessage.contains("❤") && (cleanMessage.contains("✎") || cleanMessage.contains("❈"))) return;

        if (LGMSkyblockMod.modtestOn)
            LGMSkyblockMod.LOGGER.info("[ChatManager] Clean message: " + cleanMessage);

        if (registry.containsKey(cleanMessage))
        {
            registry.get(cleanMessage).onMessageReceived(cleanMessage);
        }

    }

    public static void RemoveRegister(String trigger)
    {
        registry.remove(trigger);
        LGMSkyblockMod.LOGGER.info("[ChatManager] Registered has now been removed! '" + trigger + "'");
    }
}
