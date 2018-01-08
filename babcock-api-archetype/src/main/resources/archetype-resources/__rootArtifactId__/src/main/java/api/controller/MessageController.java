#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.controller;

import ${package}.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageController {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getMessages() {

        logger.info("getMessages() invoked");

        List<String> messages = messageService.getMessages();

        logger.info("getMessages() found: " + messages.size() + " messagess");

        return messages;
    }
}
