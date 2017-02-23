package org.jbpm.examples.test;

import org.jbpm.api.Execution;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.jbpm.test.JbpmTestCase;

public class Test extends JbpmTestCase {
	String deploymentId; 
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		deploymentId = repositoryService.createDeployment()
			.addResourceFromClasspath("org/jbpm/examples/test/process.jpdl.xml")
			.deploy();
		
	}
	@Override
	protected void tearDown() throws Exception {
		repositoryService.deleteDeployment(deploymentId);
		super.tearDown();
	}
	
	
	public void testWaitProcess(){
		ProcessInstance processInstance = executionService
				.startProcessInstanceByKey("process");
		String pid = processInstance.getId();
		Execution executionState = processInstance.findActiveExecutionIn("state");
		assertNotNull(executionState);
		executionService.signalExecutionById(executionState.getId());
		processInstance = executionService.findProcessInstanceById(pid);
		Execution executionInTask = processInstance.findActiveExecutionIn("task");
		assertNotNull(executionInTask);
		Task task = taskService.findPersonalTasks("Alex").get(0);
		taskService.completeTask(task.getId());
		HistoryTask historyTask  = historyService.createHistoryTaskQuery()
				.taskId(task.getId()).uniqueResult();
		assertNotNull(historyTask);
		assertProcessInstanceEnded(pid);
		HistoryProcessInstance historyProcInst = historyService
			.createHistoryProcessInstanceQuery().processInstanceId(pid)
			.uniqueResult();
		
		
		
		
		
		
	}
	
}
