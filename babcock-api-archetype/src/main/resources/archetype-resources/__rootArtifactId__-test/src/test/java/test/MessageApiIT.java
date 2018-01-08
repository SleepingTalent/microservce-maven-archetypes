package ${package}.test;

import ${package}.test.helper.asserter.WaitForHelper;
import ${package}.test.helper.db.DatabaseHelper;
import ${package}.test.application.TestApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= TestApplication.class)
@TestPropertySource("classpath:test.properties")
public class MessageApiIT {

    private static Logger logger = LoggerFactory.getLogger(MessageApiIT.class);

    @Autowired
    private WaitForHelper waitForHelper;

    @Autowired
    @Qualifier("oauthRestTemplate")
    private RestTemplate oAuthRestTemplate;

    @Autowired
    DatabaseHelper databaseHelper;

    @Value("${symbol_dollar}{service.url}")
    String serviceUrl;

    String message;

    @Before
    public void before() throws InterruptedException {
        waitForHelper.waitForServices();

        String uniqueStr = getUniqueString();
        message = "msg" + uniqueStr;

        databaseHelper.insertMessage(message);
    }

    @After
    public void after() throws InterruptedException {
        databaseHelper.deleteMessage(message);
    }

    @Test
    public void getMessage() throws IOException {
        ResponseEntity<List> response = oAuthRestTemplate.exchange(serviceUrl + "/message/messages", HttpMethod.GET,null,List.class);

        List<String> messageList = response.getBody();
        Assert.assertEquals(3,messageList.size());Assert.assertEquals(3,messageList.size());

        Assert.assertEquals("demo message", messageList.get(0));
        Assert.assertEquals("demo message 2", messageList.get(1));
        Assert.assertEquals(message, messageList.get(2));

    }

    private String getUniqueString() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
