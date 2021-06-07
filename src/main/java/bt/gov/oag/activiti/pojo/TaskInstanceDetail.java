package bt.gov.oag.activiti.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class TaskInstanceDetail {

	private String id;
	private String name;
	private String assignee;
	private Date startTime;
	private Date endTime;
	private int priority;
	private int status;
	private Boolean isTaskApproved;
	private String comment;
	private String processDefinitionId;
	private String processInstanceId;
	private String formKey;
	private Object taskVariables;
}
