package cc.dreamcode.guildpoints.cooldown;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.WeakHashMap;

// https://github.com/FunnyGuilds/FunnyGuilds/blob/master/plugin/src/main/java/net/dzikoysk/funnyguilds/shared/Cooldown.java
public final class Cooldown<T> {

    private final Map<T, Instant> cooldowns = new WeakHashMap<>(32);

    public boolean isOnCooldown(T key) {
        Instant cooldown = this.cooldowns.get(key);
        if (cooldown == null) {
            return false;
        }

        if (cooldown.isAfter(Instant.now())) {
            return true;
        }

        this.cooldowns.remove(key);
        return false;
    }

    public void putOnCooldown(T key, Duration duration) {
        this.cooldowns.put(key, Instant.now().plus(duration));
    }

    public boolean cooldown(T key, int cooldown) {
        if (this.isOnCooldown(key)) {
            return true;
        }

        this.putOnCooldown(key, Duration.ofSeconds(cooldown));
        return false;
    }

}