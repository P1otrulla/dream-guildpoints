package cc.dreamcode.guildpoints;

import cc.dreamcode.guildpoints.config.PluginConfig;
import cc.dreamcode.guildpoints.cooldown.Cooldown;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.UUID;

public class GuildPointsController implements Listener {

    private final Cooldown<UUID> cooldown = new Cooldown<>();
    private final GuildPointsService guildPointsService;
    private final PluginConfig pluginConfig;

    public GuildPointsController(GuildPointsService guildPointsService, PluginConfig pluginConfig) {
        this.guildPointsService = guildPointsService;
        this.pluginConfig = pluginConfig;
    }

    @EventHandler
    void onInteract(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();

        if (!(entity instanceof Player)) {
            return;
        }

        Player player = event.getPlayer();
        Player target = (Player) entity;

        boolean hasCooldown = this.cooldown.cooldown(player.getUniqueId(), this.pluginConfig.cooldown);

        if (hasCooldown) {
            return;
        }

        this.guildPointsService.apply(player, target);
    }
}
