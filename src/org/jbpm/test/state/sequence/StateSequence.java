package org.jbpm.test.state.sequence;

import org.jbpm.api.Execution;
import org.jbpm.api.ProcessInstance;
import org.jbpm.test.JbpmTestCase;

public class StateSequence extends JbpmTestCase {
	String deploymentId;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		deploymentId = repositoryService.createDeployment()
			.addResourceFromClasspath("org/jbpm/test/stateSequence/process.jpdl.xml")
			.deploy();
	}

	@Override
	protected void tearDown() throws Exception {
		repositoryService.deleteDeploymentCascade(deploymentId);
		super.tearDown();
	}
	
	public void testWaitStateSequence(){
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("process");
		Execution executionInA = processInstance.findActiveExecutionIn("a");
		assertNotNull(executionInA);
		
		processInstance = executionService.signalExecutionById(executionInA.getId());
		Execution executionInB = processInstance.findActiveExecutionIn("b");
		assertNotNull(executionInB);
		
		processInstance = executionService.signalExecutionById(executionInB.getId());
		Execution executionInC = processInstance.findActiveExecutionIn("c");
		assertNotNull(executionInC);
		
		
		
	}
}
