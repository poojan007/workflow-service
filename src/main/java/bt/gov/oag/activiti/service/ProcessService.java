package bt.gov.oag.activiti.service;

import java.util.Map;

import bt.gov.oag.activiti.pojo.APIResponse;

public interface ProcessService {

	public APIResponse startProcess(String processKey, Map<String, Object> processVariables);
	public APIResponse listInProgressInstances();
}
