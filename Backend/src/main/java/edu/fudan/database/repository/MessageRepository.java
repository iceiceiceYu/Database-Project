package edu.fudan.database.repository;

import edu.fudan.database.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Iterable<Message> findMessageByStaffAndMessageType(String staff, int messageType);
}
