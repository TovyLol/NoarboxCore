package projects.tovy.github.PlayerUsage.DeathStashes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DsMain {
    private final Map<UUID, Boolean> stashEnabled = new HashMap<>();

    public void setStashEnabled(UUID playerUUID, boolean enabled) {
        stashEnabled.put(playerUUID, enabled);
    }

    public boolean isStashEnabled(UUID playerUUID) {
        return stashEnabled.getOrDefault(playerUUID, false);
    }
}
