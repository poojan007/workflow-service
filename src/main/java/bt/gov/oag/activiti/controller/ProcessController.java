package bt.gov.oag.activiti.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bt.gov.oag.activiti.pojo.APIResponse;
import bt.gov.oag.activiti.service.ProcessService;

@RestController
@RequestMapping("/process")
public class ProcessController {

	@Autowired
	private ProcessService processService;
	
	@PostMapping("/start")
	public APIResponse startProcess(@RequestHeader("processKey") String processKey, @RequestBody Map<String, Object> processVariables) {
		return processService.startProcess(processKey, processVariables);
	}
	
	@GetMapping("/active-instances")
	public APIResponse listActiveProcesses() {
		return processService.listInProgressInstances();
	}
	
	@GetMapping("/hello")
	public String Hello() {
		return "Work Flow";
	}
}
