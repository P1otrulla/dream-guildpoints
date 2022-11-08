package cc.dreamcode.guildpoints.config;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.Arrays;
import java.util.List;

public class PluginConfig extends OkaeriConfig {

    @Comment({
            "## dreamGuildPoints (Main-Config) ##",
            "",
            "# Wiadomość przy kliknięciu"
    })
    public List<BukkitNotice> message = Arrays.asList(
            new BukkitNotice(NoticeType.CHAT, "&7Zabijając tego gracza otrzymasz: &a{KILL-POINTS} &7pkt."),
            new BukkitNotice(NoticeType.CHAT, "&7Ginąc przez tego gracza stracisz: &c{DEATH-POINTS} &7pkt.")
    );

    @Comment({ "", "# Cooldown pomiedzy kliknieciem (w sekundach)" })
    public int cooldown = 5;
}
