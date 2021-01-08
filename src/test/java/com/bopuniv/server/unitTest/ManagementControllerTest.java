package com.bopuniv.server.unitTest;

import com.bopuniv.server.dto.PTCDto;
import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.entities.User;
import com.bopuniv.server.services.IDepartmentService;
import com.bopuniv.server.services.IUserService;
import com.bopuniv.server.website.ManagementController;
import jdk.net.SocketFlow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.assertj.core.api.Assertions.assertThat;
// https://spring.io/guides/gs/testing-web/
@RunWith(SpringRunner.class)
@WebMvcTest(ManagementController.class)
public class ManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IUserService userService;

    @MockBean
    IDepartmentService departmentService;

    private String urlPrefix = "/management/api/v1";

    @Test
    public void getPTCShouldReturnAPtc() throws Exception{
        PTC ptc = new PTC(1L, "Universite de Yaounde 1", "email@email.com", "0000000000", "Cameroon", LocalDateTime.now());
        User user = new User();
        user.setId(1L);
        user.setPtc(ptc);
        user.setEmail("email@email.com");

        when(userService.findUserByEmail("email@email.com")).thenReturn(user);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(urlPrefix+"/ptc/"+ptc.getPtcId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        PTCDto expected = new PTCDto(ptc);

        assertThat(result.getResponse()).isEqualTo(expected);
    }
}
