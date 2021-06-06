package bt.gov.oag.activiti.listeners;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class OAGTaskListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		String assignee = delegateTask.getVariable("assignee") == null ? null : delegateTask.getVariable("assignee").toString();
		delegateTask.setVariable("assignee", assignee);
	}

}
