package cc.dreamcode.guildpoints;

import cc.dreamcode.guildpoints.config.PluginConfig;
import com.google.common.collect.ImmutableMap;
import net.dzikoysk.funnyguilds.config.PluginConfiguration;
import net.dzikoysk.funnyguilds.rank.RankSystem;
import net.dzikoysk.funnyguilds.user.User;
import net.dzikoysk.funnyguilds.user.UserManager;
import org.bukkit.entity.Player;
import panda.std.Option;

public class GuildPointsService {

    private final PluginConfiguration fgConfig;
    private final UserManager userManager;
    private final PluginConfig config;
    private RankSystem rankSystem;

    public GuildPointsService(PluginConfiguration fgConfig, UserManager userManager, PluginConfig config) {
        this.fgConfig = fgConfig;
        this.userManager = userManager;
        this.config = config;
    }

    public void apply(Player player, Player target) {
        if (this.rankSystem == null) {
            this.rankSystem = this.initialize();
        }

        Option<User> userOption = this.userManager.findByPlayer(player);

        if (userOption.isEmpty()) {
            return;
        }

        User user = userOption.get();

        Option<User> targetOption = this.userManager.findByPlayer(target);

        if (targetOption.isEmpty()) {
            return;
        }

        User targetUser = targetOption.get();

        RankSystem.RankResult rankResultAdd = this.rankSystem.calculate(this.fgConfig.rankSystem, user.getRank().getPoints(), targetUser.getRank().getPoints());

        RankSystem.RankResult rankResultRemove = this.rankSystem.calculate(this.fgConfig.rankSystem, targetUser.getRank().getPoints(), user.getRank().getPoints());

        int pointsAdd = rankResultAdd.getAttackerPoints();

        int pointsRemove = rankResultRemove.getVictimPoints();

        this.config.message.forEach(notice -> notice.send(player, ImmutableMap.of(
                "KILL-POINTS", String.valueOf(pointsAdd),
                "DEATH-POINTS", String.valueOf(pointsRemove)
        )));
    }

    private RankSystem initialize() {
        return RankSystem.create(this.fgConfig);
    }
}
