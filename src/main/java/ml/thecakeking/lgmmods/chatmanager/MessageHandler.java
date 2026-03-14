package ml.thecakeking.lgmmods.chatmanager;

@FunctionalInterface
public interface MessageHandler {
    void onMessageReceived(String message);
}
