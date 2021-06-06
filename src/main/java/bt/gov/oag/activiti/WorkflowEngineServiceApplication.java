package bt.gov.oag.activiti;

import java.util.List;

import javax.annotation.PostConstruct;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication 
@ComponentScan("bt.gov.oag")
public class WorkflowEngineServiceApplication {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RuntimeService runtimeService;

	public static void main(String[] args) {
		SpringApplication.run(WorkflowEngineServiceApplication.class, args);
	}

	@PostConstruct
	public void listProcessInstances() {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
		LOGGER.info("processInstances size : {}", processInstances.size());
		for (ProcessInstance processInstance : processInstances) {
			LOGGER.info("Process instance id : {}", processInstance.getId());
		}
	}
}

@Configuration
class RestTemplateConfig {

	// Create a bean for restTemplate to call services
	@Bean
	@LoadBalanced // Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
