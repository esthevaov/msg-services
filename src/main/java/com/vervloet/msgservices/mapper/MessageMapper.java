package com.vervloet.msgservices.mapper;

import com.vervloet.msgservices.domain.model.Message;
import com.vervloet.msgservices.domain.vo.MessageVo;

public class MessageMapper {

  public MessageMapper() {
  }

  public static Message mapVoToDomain(MessageVo messageVo) {
    return Message.builder()
        .withId(messageVo.getId())
        .withTitle(messageVo.getTitle())
        .withContent(messageVo.getContent())
        .withCreated(messageVo.getCreated())
        .withVotes(messageVo.getVotes())
        .build();
  }

  public static MessageVo mapDomainToVo(Message message) {
    return MessageVo.builder()
        .withId(message.getId())
        .withTitle(message.getTitle())
        .withContent(message.getContent())
        .withCreated(message.getCreated())
        .withVotes(message.getVotes())
        .withUserId(message.getUser().getId())
        .build();
  }
}

