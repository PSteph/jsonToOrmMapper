package com.bopuniv.server;

import com.bopuniv.server.entities.PTC;
import com.bopuniv.server.services.IPtcService;
import com.bopuniv.server.website.ManagementController;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc // This config will not start the server
@WebMvcTest(ManagementController.class) // As we are testing the web component we can choose only to start that.
// This will make testing even faster compare to @AutoConfigureMockMvc
public class ManagementControllerTest {

    @MockBean
    private IPtcService ptcService;

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void shouldReturnDefaultMessage() throws Exception {
//        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String expected = "Home management";
//        MatcherAssert.assertThat(expected, containsString(result.getResponse().getContentAsString()));
//    }

//    @Test
//    public void retrievePtc() throws Exception {
//
//        PTC mockPtc = new PTC();
//        mockPtc.setPtcId(1L);
//        mockPtc.setLongName("University Lumiere");
//        mockPtc.setEmail("email@email.com");
//
//        when(ptcService.findById(1L)).thenReturn(mockPtc);
//
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.get("/management/ptc/1").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//        String expected = "{\"ptc_id\":1,\"long_name\":\"University Lumiere\",\"email\":\"email@email.com\"}";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

//    }
}
