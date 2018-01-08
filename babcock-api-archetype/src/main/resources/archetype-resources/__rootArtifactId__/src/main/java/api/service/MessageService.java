#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.service;

import ${package}.api.model.domain.Message;
import ${package}.api.model.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    public List<String> getMessages() {
        List<Message> messageList = messageRepository.findAll();

        List<String> messages = new ArrayList<>();

        for(Message message : messageList) {
            messages.add(message.getMessage());
        }

        return messages;
    }

}
