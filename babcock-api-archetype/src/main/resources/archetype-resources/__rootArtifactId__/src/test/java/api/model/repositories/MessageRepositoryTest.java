#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.model.repositories;

import ${package}.api.model.domain.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = Message.class)
public class MessageRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private Message message1;
    private Message message2;

    @Before
    public void setUp() throws Exception {
        message1 = new Message("message1");
        message2 = new Message("message2");

        entityManager.persist(message1);
        entityManager.persist(message2);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.remove(message1);
        entityManager.remove(message2);
    }

    @Test
    public void countPermissions() {
        assertEquals(2, messageRepository.count());
    }

    @Test
    public void getAll() {
        List<Message> messages = messageRepository.findAll();

        assertEquals(2, messages.size());
        assertEquals("message1", messages.get(0).getMessage());
        assertEquals("message2", messages.get(1).getMessage());

    }

}