#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.controller;

import ${package}.api.model.domain.Message;
import ${package}.api.service.MessageService;
import org.junit.Before;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageController.class)
@EntityScan("${package}.api.model.domain")
@ActiveProfiles("integration")
public class MessageControllerTest {

    @Autowired
    private MessageController messageController;

    @MockBean
    private MessageService messageService;

    private List<String> allMessages;

    @Before
    public void setUp() throws Exception {
        allMessages = new ArrayList<>();
        allMessages.add("message");
    }

    @Test
    public void getMessages() {
        when(messageService.getMessages()).thenReturn(allMessages);

        List<String> actual = messageController.getMessages();

        assertEquals(1,actual.size());
        assertEquals("message", actual.get(0));

        verify(messageService, times(1)).getMessages();
    }

}