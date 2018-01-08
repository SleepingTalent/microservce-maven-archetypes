#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.service;

import ${package}.api.model.domain.Message;
import ${package}.api.model.repositories.MessageRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageService.class)
@EntityScan("${package}.api.model.domain")
@ActiveProfiles("integration")
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @MockBean
    MessageRepository messageRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void getMessages_returnsEmptyList() {
        when(messageRepository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(0,messageService.getMessages().size());

        verify(messageRepository, times(1)).findAll();
    }

    @Test
    public void getMessages_returnsMessages_asExpected() {
        String message = "message";

        List<Message> messageList = new ArrayList();
        messageList.add(new Message(message));

        when(messageRepository.findAll()).thenReturn(messageList);

        List actual = messageService.getMessages();

        assertEquals(1,actual.size());
        assertEquals("message", actual.get(0));

        verify(messageRepository, times(1)).findAll();
    }


}