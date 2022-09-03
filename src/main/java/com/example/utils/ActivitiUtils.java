package com.example.utils;

import com.example.configuration.ActivitiConfiguration;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/8/14 16:21
 * activiti 引擎配置
 */
@Component
public class ActivitiUtils {

    @Qualifier("engineConfiguration")
    private static ProcessEngineConfiguration processEngineConfiguration;

    public static final String ORDER_OF_BUY_GOODS = "orderOfBuyGoods.bpmn20.xml";

    /**
     * 生成流程引擎
     * @return ProcessEngine
     */
    public static ProcessEngine getProcessEngine() {

        return processEngineConfiguration.buildProcessEngine();
    }


    public static void deployMap(String mapName, String deployName, String category, String key) {
        // 部署流程图
        RepositoryService repositoryService = ActivitiUtils.getProcessEngine().getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("activiti/map/"+mapName+".bpmn20.xml")
                .name(deployName).category(category).key(key)
                .disableSchemaValidation().deploy();
    }

    /**
     * 定义流程变量并启动流程
     */
    public static void startProcessInstance(Map<String, Object> variables, String definitionKey) {
        System.out.println("active process definition!");
        ProcessEngine processEngine = ActivitiUtils.getProcessEngine();



        // 业务号 和 instanceName
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("orderOfBuyGoods", variables);

        System.out.println("processdefinitionID : "+processInstance.getProcessDefinitionId());

        TaskService taskService = processEngine.getTaskService();

        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();

        System.out.println("task 队列节点: "+list.size());

        for (Task task : list) {
            System.out.println("name: "+task.getName());
        }
    }

    /**
     * 打印流程图
     */
    public static void printProcessMap(String destPath, String photoFormat, ProcessInstance processInstance) throws IOException {
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        int len;
        byte[] buf = new byte[1024];

        List<String> active = new ArrayList<>();
        active.add("auto1");
        active.add("auto2");
        active.add("auto3");
        active.add("line1");
        active.add("line2");
        active.add("line3");
        active.add("start");
        active.add("end");

        InputStream inputStream = diagramGenerator.generateDiagram(ActivitiUtils.getProcessEngine()
                .getRepositoryService().getBpmnModel(processInstance.getProcessDefinitionId()), "png", active);


        OutputStream outputStream =
                new FileOutputStream(new File(destPath+"\\"+ORDER_OF_BUY_GOODS.substring(0, ORDER_OF_BUY_GOODS.indexOf(".")+1)+"."+photoFormat));
        while ((len = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }

        System.out.println("print successfully!");
        outputStream.close();
        inputStream.close();
    }

    /**
     * 获取所有流程实例
     */
    public static void getAllInstance(String definitionKey) {
        ProcessEngine engine = ActivitiUtils.getProcessEngine();
        List<ProcessInstance> processInstances = engine.getRuntimeService().createProcessInstanceQuery().processDefinitionKey(definitionKey).list();

        for (ProcessInstance processInstance : processInstances) {
            System.out.println(processInstance.getId());
        }
    }

    /**
     * 执行节点一
     */
    public static void executeNode(String definitionKey) {
        ProcessEngine engine = ActivitiUtils.getProcessEngine();
        TaskService taskService = engine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(definitionKey).list();

        for (Task task : taskList) {
            taskService.complete(task.getId());
        }
    }
}
