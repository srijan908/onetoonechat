package com.work.onetoonechat.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessage {

    private String content;

    private String sender;

    private MessageType type;
}
