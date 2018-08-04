package com.ml.server;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.controller.MLController;
import com.ml.dto.DataColumn;
import com.ml.dto.DataColumn.ValueTypeEnum;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes=MLController.class)
public class MLMVCTest{
	
    @Autowired
    private WebApplicationContext wac;

	private MockMvc mvc;	
	
	private static String predictForData = "9\tJohnson,Mrs. Oscar W\t19 Main Street, St. Loius, Missouri\tFemale\t27\tSt. Loius, Missouri\tAtlanta,GA\t11.1333\tSecond\t\t";
	
	static {
		
		predictForData = "4\tFutrelle,Mrs. Jacques Heath\t14,Main Street,St. Loius,Missouri\tFemale\t18\tSt. Loius, Missouri\tAtlanta,GA\t53.1\tSecond\t\t";
		predictForData = "1\tBraund,Mr. Owen Harris\t34,VandeRbilt,St.Loius,Missouri\tMale\t22\tSt.Loius,Missouri\tAtlanta,GA\t47.25\tFirst\t\t";
		predictForData = "2\tCumings,Mrs.John Bradley\t12,Main Street,St.Loius,Missouri\tMale\t38\tSt.Loius,Missouri\tAtlanta,GA\t17.2833\tSecond\t\t";
	}


	@Before
	public void setUp() throws Exception {
		
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@After
	public void tearDown() throws Exception {
		
	} 
	
    @Test
	public void testgetDecision2get() throws Exception{
    	
    	String content;
        mvc.perform(MockMvcRequestBuilders.get("/getDecision2")).andReturn();
    }   	

    //@Test
	public void testgetDecision1post() throws Exception{
    	
        mvc.perform(MockMvcRequestBuilders.post("/getDecision1").param("data", "Test"));
    }    
    
    //@Test
    public void invokeMvc() throws Exception{

		String fileName = "data.csv";
		List<DataColumn> colsToLearnAbout = new ArrayList<DataColumn>();
		colsToLearnAbout.add(new DataColumn(4,"Age",ValueTypeEnum.INT_TYPE));
		colsToLearnAbout.add(new DataColumn(3,"Gender",ValueTypeEnum.STRING_TYPE));
		colsToLearnAbout.add(new DataColumn(7,"Fare",ValueTypeEnum.DOUBLE_TYPE));
		DataColumn col = new DataColumn(8,"Class",ValueTypeEnum.STRING_TYPE);
		String decisionValue = "First";
		
		ObjectMapper mapper = new ObjectMapper();		
    	JSONObject allMLData = MLMicroServiceTest.createRequestObject(predictForData);
    	
        mvc.perform(MockMvcRequestBuilders.post("/getDecision")
        			.param("data", allMLData.toString())
	    			.accept(MediaType.APPLICATION_JSON))
	   				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
	   				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(org.hamcrest.Matchers.equalTo("Second")));
    	
    }
}