package cc.dreamcode.guildpoints.config;

import cc.dreamcode.notice.okaeri_serdes.BukkitNoticeSerdes;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;

import java.io.File;

public class ConfigService {

    private final File dataFolder;

    public ConfigService(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public PluginConfig loadConfig() {
        return ConfigManager.create(PluginConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(), new SerdesCommons());
            it.withBindFile(new File(this.dataFolder, "config.yml"));
            it.withSerdesPack(registry -> registry.register(new BukkitNoticeSerdes()));
            it.saveDefaults();
            it.load(true);
        });
    }
}