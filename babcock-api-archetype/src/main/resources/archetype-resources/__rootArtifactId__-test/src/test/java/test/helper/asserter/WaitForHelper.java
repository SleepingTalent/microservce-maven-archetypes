package ${package}.test.helper.asserter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.fail;

@Component
@TestPropertySource("classpath:test.properties")
public class WaitForHelper {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${symbol_dollar}{service.url}")
    String baseUrl;

    private static boolean serviceUnavailable = false;

    public void waitForServices() throws InterruptedException {
        WaitForService waitForService = new WaitForService(baseUrl + "/info", restTemplate);
        waitForService.setMaxWaitTime(720000);

        if(serviceUnavailable) {
            fail("docker environment unavailable");
        }

        System.out.println("waiting for service : " + baseUrl + "/info");

        try {
            waitForService.performAssertion();
        }catch (WaitUntilAssertionError wae) {
            serviceUnavailable = true;
            fail("docker environment unavailable");
        }
    }

    public void waitForOneRowInDB(String query) throws InterruptedException {
        WaitForOneRowInDB waitForOneRowInDB = new WaitForOneRowInDB(query, jdbcTemplate);
        waitForOneRowInDB.setMaxWaitTime(60000);
        waitForOneRowInDB.performAssertion();
    }

}
