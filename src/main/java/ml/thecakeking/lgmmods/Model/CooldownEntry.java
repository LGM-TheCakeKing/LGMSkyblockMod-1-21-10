package ml.thecakeking.lgmmods.Model;

public record CooldownEntry(String name, long expiryTime, float maxDuration)
{
    public float getRemaining(long currentTime) {
        return Math.max(0, (expiryTime - currentTime) / 1000f);
    }
}
