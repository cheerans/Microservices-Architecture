package com.ml.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MLMicSvcClient {
	

	@LoadBalanced
	private  RestTemplate restTemplate;
	
	public MLMicSvcClient(){

		RestTemplateBuilder builder = new RestTemplateBuilder();
		this.restTemplate = getRestTemplate(builder);		
	}
	
	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		
		this.restTemplate = restTemplate;
	}	
    
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {

       return builder.build();
    }
   
	/* 		
	private static String predictForData = "9\tJohnson,Mrs. Oscar W\t19 Main Street, St. Loius, Missouri\tFemale\t27\tSt. Loius, Missouri\tAtlanta,GA\t11.1333\tSecond\t\t";
	
	static {
		
		predictForData = "4\tFutrelle,Mrs. Jacques Heath\t14,Main Street,St. Loius,Missouri\tFemale\t18\tSt. Loius, Missouri\tAtlanta,GA\t53.1\tSecond\t\t";
		predictForData = "1\tBraund,Mr. Owen Harris\t34,VandeRbilt,St.Loius,Missouri\tMale\t22\tSt.Loius,Missouri\tAtlanta,GA\t47.25\tFirst\t\t";
		predictForData = "2\tCumings,Mrs.John Bradley\t12,Main Street,St.Loius,Missouri\tMale\t38\tSt.Loius,Missouri\tAtlanta,GA\t17.2833\tSecond\t\t";
	}

    @HystrixCommand(fallbackMethod = "callMLServiceFallback")
	public void callMLService() {
    	
    	String svcName = "MLService";    	
//    	ServiceInstance inst = loadBalancerClient.choose(svcName);
//    	if(null != inst){
//    		URI uri = inst.getUri();
//    		int port = inst.getPort();
//    		String host = inst.getHost();
//    		URI svcUri = URI.create(String.format("http://%s:%s/%s", inst.getHost(), inst.getPort(),svcName));
//            final String theOtherResponse = restTemplate.getForObject(svcUri, String.class);
//    	}
    }
    
    public void callMLServiceFallback() {
    	
    }
    
   
    @Test
    public void invokeMicroserviceAsRest() throws Exception{

    	JSONObject allMLData = createRequestObject(predictForData);    	
    	//rest is not finding the load balanced port    	
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.set("Content-Type", "application/json");
    	HttpEntity <String> httpEntity = new HttpEntity <String> (allMLData.toString(), httpHeaders);    	
    	
    	String decision = restTemplate.postForEntity(	"http://localhost:8080/getDecision", 
														allMLData.toString(),
														String.class).getBody();
    	System.out.println("Done");
    }
    
    //Convert my file to a Base64 String
    private JSONObject createRequestObject(String rowDataToPredictOn) throws IOException{

		String fileName = "data.csv";
		List<DataColumn> colsToLearnAbout = new ArrayList<DataColumn>();
		colsToLearnAbout.add(new DataColumn(4,"Age",ValueTypeEnum.INT_TYPE));
		colsToLearnAbout.add(new DataColumn(3,"Gender",ValueTypeEnum.STRING_TYPE));
		colsToLearnAbout.add(new DataColumn(7,"Fare",ValueTypeEnum.DOUBLE_TYPE));
		DataColumn pivotCol = new DataColumn(8,"Class",ValueTypeEnum.STRING_TYPE);
		String pivotValue = "First";
		
    	JSONObject allMLData = new JSONObject();
    	DecisionTreeLogic.readColumnIndexes(fileName, MLControllerTest.class, colsToLearnAbout,new DecisionPredicate(pivotValue,pivotCol));
    	List<DataRow> rows = DecisionTreeLogic.readFile(fileName, MLControllerTest.class, colsToLearnAbout);    	
    	ObjectMapper mapper = new ObjectMapper();
        try {
            allMLData.put("allLearningData",new String(Base64.getEncoder().encode(mapper.writeValueAsString(rows).getBytes())));
            String allrw = mapper.writeValueAsString(rows);
            allMLData.put("colsToLearnAbout",new String(Base64.getEncoder().encode(mapper.writeValueAsString(colsToLearnAbout).getBytes())));
            allMLData.put("pivotCol",new String(Base64.getEncoder().encode(mapper.writeValueAsString(pivotCol).getBytes())));
            allMLData.put("pivotValue",new String(Base64.getEncoder().encode(pivotValue.getBytes())));
            String rw = mapper.writeValueAsString(new DataRow.Builder().setRowData(DecisionTreeLogic.parseData(rowDataToPredictOn,DecisionTreeLogic.TABDELIMITER)).build(colsToLearnAbout));
            allMLData.put("predictForData",new String(Base64.getEncoder().encode(rowDataToPredictOn.getBytes())));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return allMLData;
    }  
*/
}