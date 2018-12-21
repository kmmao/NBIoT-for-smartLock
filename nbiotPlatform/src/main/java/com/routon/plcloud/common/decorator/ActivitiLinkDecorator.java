package com.routon.plcloud.common.decorator;

import org.activiti.engine.repository.ProcessDefinition;
import org.displaytag.decorator.TableDecorator;

public class ActivitiLinkDecorator extends TableDecorator {
	
	public String getActivitiXML(){
		
		ProcessDefinition t = (ProcessDefinition) getCurrentRowObject();
		String processDefinitionId = t.getId();
		//String resourceName = t.getName();
		return "<a target=\"_blank\" href=\"/AMS/activiti/getProcessImageAndXml.do?processDefinitionId=" +
				processDefinitionId + "&resourceType=xml\">查看</a>";
	}
	
	public String getActivitiProcessImage(){
		ProcessDefinition t = (ProcessDefinition) getCurrentRowObject();
		String processDefinitionId = t.getId();
		return "<a target=\"_blank\" href=\"/AMS/activiti/getProcessImageAndXml.do?processDefinitionId=" + 
				processDefinitionId + "&resourceType=image\">查看</a>";
	}
}
