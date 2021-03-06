package com.vervloet.msgservices.repository;

import com.vervloet.msgservices.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByUserId(Long userId);
}
