package bt.gov.oag.activiti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class WorkFlowController {

	@Autowired
	private RestTemplate template;
	
	@Autowired
	private Environment env;
	
	@HystrixCommand(fallbackMethod = "LegalFallBack")
	@GetMapping("/mongar")
	public String HelloWorld() {
		//String response = template.getForObject("http://prosecution-service", String.class);
		return "Hello From Legal Service";
				//+ "\n" + response;
	}
	
	@GetMapping("legalWithOuthytrix")
	public String LegalFallBack() {
		return "Legal Service without Hytrix";
	}
	
	//Legal Fall Back Message: If Draft server get down: Given message will be sent back
	public String legalFallBack() {
		return "Legal Service Not Resposnding ...";
	}
	
	@RequestMapping("/")
	public String home() { 
		return "Work FLow running at port: " + env.getProperty("local.server.port");
	}
}
