package com.org.rjankowski.ms.payment.resource;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CartResourceTest {
    private final MockMvc mockMvc;

    @Autowired
    CartResourceTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.matchesRegex(Pattern.compile("^\\[.*]$"))));
    }
}
