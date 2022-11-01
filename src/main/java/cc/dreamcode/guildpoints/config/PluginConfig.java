package cc.dreamcode.guildpoints.config;

import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PluginConfig implements ReloadableConfig {

    @Description({
            "## dreamGuildPoints (Main-Config) ##",
            "",
            "# Wiadomość przy kliknięciu"
    })
    public List<String> message = Arrays.asList(
            "&7Zabijając tego gracza otrzymasz: &a{KILL-POINTS} &7pkt.",
            "&7Ginąc przez tego gracza stracisz: &c{DEATH-POINTS} &7pkt."
    );

    @Description({ "", "# Cooldown pomiedzy kliknieciem (w sekundach)" })
    public int cooldown = 5;

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }
}
