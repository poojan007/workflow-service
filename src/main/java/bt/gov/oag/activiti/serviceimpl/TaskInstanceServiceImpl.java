package bt.gov.oag.activiti.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bt.gov.oag.activiti.pojo.APIResponse;
import bt.gov.oag.activiti.pojo.TaskInstanceDetail;
import bt.gov.oag.activiti.service.TaskInstanceService;

@Service
public class TaskInstanceServiceImpl implements TaskInstanceService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;

	@Override
	@Transactional
	public APIResponse getActiveTaskInstances(String assignee) {
		APIResponse apiResponse = new APIResponse();
		List<TaskInstanceDetail> taskInstanceDetails = new ArrayList<TaskInstanceDetail>();
		
		try {
			List<Task> tasks = taskService.createTaskQuery().active().taskAssignee(assignee).orderByTaskCreateTime().asc().list();
			
			for(Task task : tasks) {
				TaskInstanceDetail taskInstanceDetail = new TaskInstanceDetail();
				taskInstanceDetail.setId(task.getId());
				taskInstanceDetail.setName(task.getName());
				taskInstanceDetail.setAssignee(task.getAssignee());
				taskInstanceDetail.setStartTime(task.getCreateTime());
				taskInstanceDetail.setFormKey(task.getFormKey());
				taskInstanceDetail.setProcessDefinitionId(task.getProcessDefinitionId());
				taskInstanceDetail.setProcessInstanceId(task.getProcessInstanceId());
				
				taskInstanceDetails.add(taskInstanceDetail);
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while listing active task for assignee {} -> {}", assignee, e);
			apiResponse.setMessage("Error occurred while listing active tasks by assignee");
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setException(e.getMessage());
			return apiResponse;
		}
		
		apiResponse.setData(taskInstanceDetails);
		apiResponse.setMessage("Active tasks retrieved successfully");
		apiResponse.setStatus(HttpStatus.OK);
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse getActiveTaskInstancesByCandidateUsers(String candidateUser) {
		APIResponse apiResponse = new APIResponse();
		List<TaskInstanceDetail> taskInstanceDetails = new ArrayList<TaskInstanceDetail>();
		
		try {
			List<Task> tasks = taskService.createTaskQuery().active().taskCandidateUser(candidateUser).orderByTaskCreateTime().desc().list();
			
			for(Task task : tasks) {
				TaskInstanceDetail taskInstanceDetail = new TaskInstanceDetail();
				taskInstanceDetail.setId(task.getId());
				taskInstanceDetail.setName(task.getName());
				taskInstanceDetail.setAssignee(task.getAssignee());
				taskInstanceDetail.setStartTime(task.getCreateTime());
				taskInstanceDetail.setProcessDefinitionId(task.getProcessDefinitionId());
				taskInstanceDetail.setProcessInstanceId(task.getProcessInstanceId());
				
				taskInstanceDetails.add(taskInstanceDetail);
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while listing active task for candidate user {} -> {}", candidateUser, e);
			apiResponse.setMessage("Error occurred while listing active tasks by candidate user");
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setException(e.getMessage());
			return apiResponse;
		}
		
		apiResponse.setData(taskInstanceDetails);
		apiResponse.setMessage("Active tasks retrieved successfully");
		apiResponse.setStatus(HttpStatus.OK);
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse getCompletedtaskInstances(String assignee) {
		APIResponse apiResponse = new APIResponse();
		List<TaskInstanceDetail> taskInstanceDetails = new ArrayList<TaskInstanceDetail>();
		
		try {
			List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().finished().orderByTaskCreateTime().asc().list();
			
			for(HistoricTaskInstance task : tasks) {
				TaskInstanceDetail taskInstanceDetail = new TaskInstanceDetail();
				taskInstanceDetail.setId(task.getId());
				taskInstanceDetail.setName(task.getName());
				taskInstanceDetail.setAssignee(task.getAssignee());
				taskInstanceDetail.setStartTime(task.getCreateTime());
				taskInstanceDetail.setEndTime(task.getEndTime());
				taskInstanceDetail.setProcessDefinitionId(task.getProcessDefinitionId());
				taskInstanceDetail.setProcessInstanceId(task.getProcessInstanceId());
				
				taskInstanceDetails.add(taskInstanceDetail);
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while listing completed tasks for {} -> {}", assignee, e);
			apiResponse.setMessage("Error occurred while listing completed tasks");
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setException(e.getMessage());
			return apiResponse;
		}
		
		apiResponse.setData(taskInstanceDetails);
		apiResponse.setMessage("Completed tasks retrieved successfully");
		apiResponse.setStatus(HttpStatus.OK);
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse completeTask(String taskInstanceId, Map<String, Object> taskInstanceVariables) {
		APIResponse apiResponse = new APIResponse();
		
		try {
			taskService.complete(taskInstanceId, taskInstanceVariables, Boolean.FALSE);
		} catch (Exception e) {
			LOGGER.error("Error occurred while completing task {}", e);
			apiResponse.setMessage("Error occurred while completing task, can you check if task might be completed or escalated");
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setException(e.getMessage());
			return apiResponse;
		}
		
		apiResponse.setMessage("Task completed successfully");
		apiResponse.setStatus(HttpStatus.OK);
		return apiResponse;
	}
	
	@Override
	@Transactional
	public APIResponse completeTaskAG(String taskInstanceId, Map<String, Object> taskInstanceVariables) {
		APIResponse apiResponse = new APIResponse();
		
		try {
			taskService.complete(taskInstanceId, taskInstanceVariables, Boolean.FALSE);
		} catch (Exception e) {
			LOGGER.error("Error occurred while completing task {}", e);
			apiResponse.setMessage("Error occurred while completing task, can you check if task might be completed or escalated");
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setException(e.getMessage());
			return apiResponse;
		}
		
		apiResponse.setMessage("Task completed successfully");
		apiResponse.setStatus(HttpStatus.OK);
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse getTaskVariables(String taskInstanceId) {
		APIResponse apiResponse = new APIResponse();
		Map<String, VariableInstance> taskVariables = null;
		
		try {
			taskVariables = taskService.getVariableInstances(taskInstanceId);
		} catch (Exception e) {
			LOGGER.error("Error occurred while retrieving task variables {}", e);
			apiResponse.setMessage("Error occurred while retrieving task variables");
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			apiResponse.setException(e.getMessage());
			return apiResponse;
		}
		
		apiResponse.setData(taskVariables);
		apiResponse.setMessage("Task variables retrieved successfully");
		apiResponse.setStatus(HttpStatus.OK);
		return apiResponse;
	}
}
