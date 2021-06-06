package bt.gov.oag.activiti.pojo;

import lombok.Data;

@Data
public class OAGProcessInstance {

	private String id;
	private String name;
	private String processDefinitionId;
	private String processDefinitionName;
}
