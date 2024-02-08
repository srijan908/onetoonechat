package com.work.onetoonechat.config;

import com.work.onetoonechat.chat.ChatMessage;
import com.work.onetoonechat.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ) {
        var headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        var username = headerAccessor.getSessionAttributes().get("username").toString();

        if (username != null) {
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder().type(MessageType.LEAVE).sender(username).build();

            // will inform all the user that a certain user has left the chat
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
