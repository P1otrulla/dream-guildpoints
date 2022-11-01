package cc.dreamcode.guildpoints.config;

import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;

import java.io.File;

public class ConfigService {

    private final Cdn cdn = CdnFactory
            .createYamlLike()
            .getSettings()
            .build();

    private final File dataFolder;

    public ConfigService(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public <T extends ReloadableConfig> T load(T config) {
        cdn.load(config.resource(this.dataFolder), config)
                .orThrow(RuntimeException::new);

        cdn.render(config, config.resource(this.dataFolder))
                .orThrow(RuntimeException::new);

        return config;
    }
}