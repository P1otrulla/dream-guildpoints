package cc.dreamcode.guildpoints;

import cc.dreamcode.guildpoints.config.PluginConfig;
import cc.dreamcode.guildpoints.notice.NoticeService;
import net.dzikoysk.funnyguilds.config.PluginConfiguration;
import net.dzikoysk.funnyguilds.rank.RankSystem;
import net.dzikoysk.funnyguilds.user.User;
import net.dzikoysk.funnyguilds.user.UserManager;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class GuildPointsService {

    private final PluginConfiguration fgConfig;
    private final NoticeService noticeService;
    private final UserManager userManager;
    private final PluginConfig config;
    private RankSystem rankSystem;

    public GuildPointsService(PluginConfiguration fgConfig, NoticeService noticeService, UserManager userManager, PluginConfig config) {
        this.fgConfig = fgConfig;
        this.userManager = userManager;
        this.noticeService = noticeService;
        this.config = config;
    }

    public void apply(Player player, Player target) {
        if (this.rankSystem == null) {
            this.rankSystem = this.initialize();
        }

        if (this.userManager.findByPlayer(player).isEmpty()) {
            return;
        }

        User user = this.userManager.findByPlayer(player).get();

        if (this.userManager.findByPlayer(target).isEmpty()) {
            return;
        }

        User targetUser = this.userManager.findByPlayer(target).get();

        RankSystem.RankResult rankResultAdd = this.rankSystem.calculate(this.fgConfig.rankSystem, user.getRank().getPoints(), targetUser.getRank().getPoints());

        RankSystem.RankResult rankResultRemove = this.rankSystem.calculate(this.fgConfig.rankSystem, targetUser.getRank().getPoints(), user.getRank().getPoints());

        int pointsAdd = rankResultAdd.getAttackerPoints();

        int pointsRemove = rankResultRemove.getVictimPoints();

        this.config.message.stream()
                .map(line -> line.replace("{KILL-POINTS}", String.valueOf(pointsAdd)).replace("{DEATH-POINTS}", String.valueOf(pointsRemove)))
                .collect(Collectors.toList())
                .forEach(line -> this.noticeService.sendMessage(player.getUniqueId(), line));
    }

    private RankSystem initialize() {
        return RankSystem.create(this.fgConfig);
    }
}
