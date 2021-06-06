package bt.gov.oag.activiti.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bt.gov.oag.activiti.pojo.APIResponse;
import bt.gov.oag.activiti.service.TaskInstanceService;

@RestController
@RequestMapping("/task")
public class TaskInstanceController {

	@Autowired
	private TaskInstanceService taskInstanceService;
	
	@GetMapping("/active")
	public APIResponse getActiveTaskInstances(@RequestParam(required = true) String assignee) {
		return taskInstanceService.getActiveTaskInstances(assignee);
	}
	
	@GetMapping("/active/candidate")
	public APIResponse getActiveTaskInstancesByCandidateUsers(@RequestParam(required = true) String candidateUser) {
		return taskInstanceService.getActiveTaskInstancesByCandidateUsers(candidateUser);
	}
	
	@GetMapping("/completed")
	public APIResponse getCompletedTaskInstances(@RequestParam(required = true) String assignee) {
		return taskInstanceService.getCompletedtaskInstances(assignee);
	}
	
	@PostMapping("/{taskInstanceId}/complete")
	public APIResponse completeTask(@PathVariable("taskInstanceId") String taskInstanceId, @RequestBody Map<String, Object> taskInstanceVariables) {
		return taskInstanceService.completeTask(taskInstanceId, taskInstanceVariables);
	}
	
	@PostMapping("/{taskInstanceId}/complete/organizationflow")
	public APIResponse completeTaskOrganizationFlow(@PathVariable("taskInstanceId") String taskInstanceId, @RequestBody Map<String, Object> taskInstanceVariables) {
		return taskInstanceService.completeTaskAG(taskInstanceId, taskInstanceVariables);
	}
	
	@GetMapping("/{taskInstanceId}/variables")
	public APIResponse getTaskVariables(@PathVariable("taskInstanceId") String taskInstanceId) {
		return taskInstanceService.getTaskVariables(taskInstanceId);
	}
}
