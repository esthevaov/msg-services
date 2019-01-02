package com.vervloet.msgservices.mapper;

import com.vervloet.msgservices.domain.model.Message;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.vo.UserVo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

  public UserMapper() {
  }

  public static User mapVoToDomain(UserVo userVo){
    return User.builder()
        .withId(userVo.getId())
        .withEmail(userVo.getEmail())
        .withPassword(userVo.getPassword())
        .build();
  }

  public static UserVo mapDomainToVo(User user){
    return UserVo.builder()
        .withId(user.getId())
        .withEmail(user.getEmail())
        .withPassword(user.getPassword())
        .withMessagesIds(mapMessagesToMessagesIds(user.getMessages()))
        .build();
  }

  public  static List<Long> mapMessagesToMessagesIds(List<Message> messages) {
    System.out.println(messages.getClass().getName());
    if(!messages.isEmpty()) {
      return messages.stream().map(Message::getId).collect(Collectors.toList());
    } else {
      return new ArrayList<Long>();
    }
  }
}
