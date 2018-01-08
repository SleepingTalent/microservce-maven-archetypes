package ${package}.test.helper.db;

import ${package}.test.helper.db.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

@Component
@TestPropertySource("classpath:test.properties")
public class DatabaseHelper {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void insertMessage(String message){
        jdbcTemplate.update("INSERT INTO messages (MESSAGE) VALUES(?)", message);
    }

    public Message findMessage(String message){
        String query = "SELECT * FROM messages WHERE MESSAGE = ?";
        return (Message) jdbcTemplate.queryForObject(query, new Object[]{message}, new BeanPropertyRowMapper(Message.class));
    }

    public void deleteMessage(String message){
        jdbcTemplate.update("DELETE FROM messages WHERE MESSAGE = ?", message);
    }

}
