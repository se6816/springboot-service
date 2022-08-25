package com.example.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class UserTest {
	
	private final Logger logger= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mp;
	
	@DisplayName("1. user은 user에 접근")
	@Test
	@WithMockUser(username="user", roles= {"USER"})
	public void test_user() throws UnsupportedEncodingException, Exception {
		String reps=mvc.perform(get("/user"))
						.andExpect(status().isOk())
						.andReturn().getResponse().getContentAsString();
		
		logger.info(reps);
	}
	@DisplayName("1. user은 user에 접근")
	@Test
	public void test_not_user() throws UnsupportedEncodingException, Exception {
		String reps=mvc.perform(get("/admin"))
						.andExpect(status().isOk())
						.andReturn().getResponse().getContentAsString();
		
		logger.info(reps);
	}
}
