package ml.thecakeking.lgmmods.chattrigger;

import com.mojang.authlib.GameProfile;
import ml.thecakeking.lgmmods.LGMSkyblockMod;
import ml.thecakeking.lgmmods.Model.ChatTrigger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TriggerManager {

    public static List<ChatTrigger> triggers = new ArrayList<>();

    public static void onChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        MinecraftClient client = MinecraftClient.getInstance();
        String messageContent = message.getString().toLowerCase();

        LGMSkyblockMod.LOGGER.info("Incoming Chat Message: " + messageContent);

        for (ChatTrigger trigger : triggers)
        {
            if (messageContent.contains(trigger.pattern))
            {
                LGMSkyblockMod.LOGGER.info("Incoming Chat has trigger a response");
                String action = trigger.response;


                assert client.player != null;
                if (action.startsWith("/")) //does this work?
                {

                    if (action.contains("[sender]"))
                    {


                    }
                    else  {
                        client.player.networkHandler.sendChatCommand(action.substring(1));
                    }
                }
                else if (action.startsWith("chat.say")) //does this work with commands?
                {

                    if (action.contains("[coords]"))
                    {
                        String coords = String.format("X:%d Y:%d Z:%d", (int)client.player.getX(), (int)client.player.getY(), (int)client.player.getZ());
                        String chatResponse = action.substring("chat.say ".length()).replace("[coords]", coords);
                        client.player.networkHandler.sendChatMessage(chatResponse);
                    }
                    else
                    {
                        client.player.networkHandler.sendChatMessage(action.substring("chat.say ".length()));
                        client.player.sendMessage(Text.of(action.substring("chat.say ".length())), true);
                    }

                }
            }
        }
    }

}
