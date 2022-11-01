package cc.dreamcode.guildpoints.notice;

import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.UUID;

public final class NoticeService {

    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    public NoticeService(AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    public void sendMessage(UUID uniqueId, String message) {
        this.audienceProvider.player(uniqueId).sendMessage(this.miniMessage.deserialize(message));
    }
}
