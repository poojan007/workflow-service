package bt.gov.oag.activiti.service;

import java.util.Map;

import bt.gov.oag.activiti.pojo.APIResponse;

public interface TaskInstanceService {

	public APIResponse getActiveTaskInstances(String assignee);
	public APIResponse getActiveTaskInstancesByCandidateUsers(String candidateUser);
	public APIResponse getCompletedtaskInstances(String assignee);
	public APIResponse completeTask(String taskInstanceId, Map<String, Object> taskInstanceVariables);
	public APIResponse completeTaskAG(String taskInstanceId, Map<String, Object> taskInstanceVariables);
	public APIResponse getTaskVariables(String taskInstanceId);
}
