package bt.gov.oag.activiti.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component("oagExecutionEventListener")
public class OAGExecutionListener {

	public void initiateDepartmentalProcess(DelegateExecution execution) {
		System.out.println("Event name : "+execution.getEventName());
		System.out.println("Execution id : "+execution.getId());
	}

}
