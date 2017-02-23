package org.jbpm.test.state.choice;

import org.jbpm.api.ProcessInstance;
import org.jbpm.test.JbpmTestCase;

public class StateChoice extends JbpmTestCase {
	String deploymentId;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		deploymentId = repositoryService.createDeployment()
			.addResourceFromClasspath("org/jbpm/test/state/choice/process.jpdl.xml")
			.deploy();
	}

	@Override
	protected void tearDown() throws Exception {
		repositoryService.deleteDeploymentCascade(deploymentId);
		super.tearDown();
	}
	
	public void testStateChoiceAccept(){
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("process1");
		
		String executionId = processInstance.findActiveExecutionIn("wait from response").getId();
		
		processInstance = executionService.signalExecutionById(executionId, "accept");
		
		assertTrue(processInstance.isActive("submit document"));
	}
	
	public void testStateChoiceReject(){
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("process");
		
		String executionId = processInstance.findActiveExecutionIn("wait for response").getId();
		
		processInstance = executionService.signalExecutionById(executionId, "reject");
		
		assertTrue(processInstance.isActive("try again"));
		
	}
	
	
	
}
