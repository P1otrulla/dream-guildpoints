package cc.dreamcode.guildpoints;

import cc.dreamcode.guildpoints.config.ConfigService;
import cc.dreamcode.guildpoints.config.PluginConfig;
import cc.dreamcode.guildpoints.legacy.LegacyColorProcessor;
import cc.dreamcode.guildpoints.notice.NoticeService;
import com.google.common.base.Stopwatch;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class GuildPoints extends JavaPlugin {

    private ConfigService configService;
    private PluginConfig config;

    private AudienceProvider audienceProvider;
    private MiniMessage miniMessage;

    private NoticeService noticeService;

    private GuildPointsService guildPointsService;

    @Override
    public void onEnable() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        this.configService = new ConfigService(this.getDataFolder());
        this.config = this.configService.load(new PluginConfig());

        this.audienceProvider = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();

        this.noticeService = new NoticeService(this.audienceProvider, this.miniMessage);

        FunnyGuilds guilds = FunnyGuilds.getInstance();

        this.guildPointsService = new GuildPointsService(guilds.getPluginConfiguration(), this.noticeService, guilds.getUserManager(), this.config);

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

    public AudienceProvider getAudienceProvider() {
        return this.audienceProvider;
    }

    public MiniMessage getMiniMessage() {
        return this.miniMessage;
    }

    public NoticeService getNoticeService() {
        return this.noticeService;
    }

    public GuildPointsService getGuildPointsService() {
        return this.guildPointsService;
    }
}
