package cc.dreamcode.guildpoints;

import cc.dreamcode.guildpoints.config.ConfigService;
import cc.dreamcode.guildpoints.config.PluginConfig;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import com.google.common.base.Stopwatch;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class GuildPoints extends JavaPlugin {

    private ConfigService configService;
    private PluginConfig config;

    private GuildPointsService guildPointsService;

    @Override
    public void onEnable() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        BukkitNoticeProvider.create(this);

        this.configService = new ConfigService(this.getDataFolder());
        this.config = this.configService.loadConfig();

        FunnyGuilds guilds = FunnyGuilds.getInstance();

        this.guildPointsService = new GuildPointsService(guilds.getPluginConfiguration(), guilds.getUserManager(), this.config);

        this.getServer().getPluginManager().registerEvents(new GuildPointsController(this.guildPointsService, this.config), this);

        Metrics metrics = new Metrics(this, 16775);

        this.getLogger().info("Successfully loaded in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }

    public ConfigService getConfigService() {
        return this.configService;
    }

    public PluginConfig getPluginConfig() {
        return this.config;
    }

    public GuildPointsService getGuildPointsService() {
        return this.guildPointsService;
    }
}
