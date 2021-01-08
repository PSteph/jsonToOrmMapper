package com.bopuniv.server.unitTest;

import com.bopuniv.server.website.ManagementController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestingWebApplicationTests {

    @Autowired
    ManagementController managementController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(managementController).isNotNull();
    }

}
