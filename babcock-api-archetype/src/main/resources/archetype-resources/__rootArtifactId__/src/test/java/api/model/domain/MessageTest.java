#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.model.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    private Message message = new Message();

    @Test
    public void getId() {
        message.setId(32L);
        assertEquals(32L, message.getId().longValue());
    }

    @Test
    public void getMessage() {
        message.setMessage("document:delete");
        assertEquals("document:delete", message.getMessage());
    }

}