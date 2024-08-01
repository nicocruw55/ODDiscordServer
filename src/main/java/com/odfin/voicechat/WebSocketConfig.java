package com.odfin.voicechat;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final VoiceChatWebSocketHandler voiceChatWebSocketHandler;

    public WebSocketConfig(VoiceChatWebSocketHandler voiceChatWebSocketHandler) {
        this.voiceChatWebSocketHandler = voiceChatWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(voiceChatWebSocketHandler, "/voicechat").setAllowedOrigins("*");
    }
}
