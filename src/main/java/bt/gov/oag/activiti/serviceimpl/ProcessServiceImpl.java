package bt.gov.oag.activiti.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bt.gov.oag.activiti.enums.Status;
import bt.gov.oag.activiti.pojo.APIResponse;
import bt.gov.oag.activiti.pojo.OAGProcessInstance;
import bt.gov.oag.activiti.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RuntimeService runtimeService;

	@Override
	@Transactional
	public APIResponse startProcess(String processKey, Map<String, Object> requestVariables) {
		APIResponse apiResponse = new APIResponse();
		ProcessInstance processInstance = null;
		
		try {
			Map<String, Object> processVariables = new HashMap<String, Object>();
			processVariables.put("status", Status.INPROGRESS);
			processVariables.put("assignee", requestVariables.get("assignee").toString());
			
			processInstance = runtimeService.startProcessInstanceByKey(processKey, processVariables);
			
		} catch (Exception e) {
			LOGGER.error("Exception occurred while starting process: ", e);
			apiResponse.setMessage("Process start failed");
			apiResponse.setException(e.getMessage());
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			
			return apiResponse;
		}
		
		apiResponse.setMessage("Process started successfully");
		apiResponse.setStatus(HttpStatus.OK);
		apiResponse.setData(processInstance.getId());
		
		return apiResponse;
	}

	@Override
	@Transactional
	public APIResponse listInProgressInstances() {
		APIResponse apiResponse = new APIResponse();
		List<OAGProcessInstance> instancePojos = new ArrayList<OAGProcessInstance>();
		try {
			List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().active().list();
			
			LOGGER.info("Process Instance size: {}", processInstances.size());
			
			for(ProcessInstance processInstance : processInstances) {
				OAGProcessInstance pojo = new OAGProcessInstance();
				pojo.setId(processInstance.getId());
				pojo.setName(processInstance.getName());
				pojo.setProcessDefinitionId(processInstance.getProcessDefinitionId());
				pojo.setProcessDefinitionName(processInstance.getProcessDefinitionName());
				
				instancePojos.add(pojo);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred while listing active processes: ", e);
			apiResponse.setMessage("Processes listing failed");
			apiResponse.setException(e.getMessage());
			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			
			return apiResponse;
		}
		
		apiResponse.setMessage("Active process instances retrieved successfully");
		apiResponse.setStatus(HttpStatus.OK);
		apiResponse.setData(instancePojos);
		
		return apiResponse;
	}

}
