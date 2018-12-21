package com.routon.plcloud.common.decorator;

import java.util.Map;

import org.activiti.engine.task.Task;
import org.displaytag.decorator.TableDecorator;

import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.HardwareStation;
import com.routon.plcloud.common.model.Order;
import com.routon.plcloud.common.model.Software;

public class PageLinkDecorator extends TableDecorator {
	
	public String getName(){
		
		Map<String,String> t = (Map<String,String>) getCurrentRowObject();
		//String ProcessInstanceId = t.getProcessInstanceId();
		//String ProcessDefinitionId = t.getProcessDefinitionId();
		//String ProcessName = t.getName();
		String ProcessInstanceId = t.get("processInstanceId");
		String ProcessDefinitionId = t.get("processDefinitionId");
		String ProcessName = t.get("name");
		return "<a href=\"/AMS/diagram-viewer/index.html?processInstanceId=" +
				ProcessInstanceId + "&processDefinitionId="+ ProcessDefinitionId +"\">"+ ProcessName +"</a>";
	}
	
/*	public String getButton(){
		Map<String,String> t = (Map<String,String>) getCurrentRowObject();
		String ProcessName = t.get("name");
		if(ProcessName.contains("审核")){
			return "<button id=\"check\" class=\"btn btn-default btn-sm\" onclick=\"checkTask(this)\" >审核</button>&nbsp"
					+ "<button id=\"viewHistory2\" class=\"btn btn-default btn-sm\" onclick=\"viewHistory(this)\" >查看流程进展</button>";
		} else{
			return "<button id=\"startup\" class=\"btn btn-default btn-sm\" onclick=\"finisheTask(this)\" >我已经处理完毕</button>&nbsp"
					+ "<button id=\"viewHistory2\" class=\"btn btn-default btn-sm\" onclick=\"viewHistory(this)\" >查看流程进展</button>";
		}
	}*/
	
	public String getButton2(){
		return "<button id=\"viewHistory3\" class=\"btn btn-default btn-sm\" onclick=\"viewHistory(this)\" >查看流程进展</button>";
	}
	
	public String getButtonRecv(){
		/*return "<button id=\"recvTask\" class=\"btn btn-default btn-sm\" onclick=\"recvTask(this)\" >签收</button>&nbsp"
				+ "<button id=\"viewHistory1\" class=\"btn btn-default btn-sm\" onclick=\"viewHistory(this)\" >查看流程进展</button>";*/
		
		Map<String,String> t = (Map<String,String>) getCurrentRowObject();
		String ProcessName = t.get("name");
		if(ProcessName.contains("审核")){
			return "<button id=\"check\" class=\"btn btn-default btn-sm\" onclick=\"checkTask(this)\" >审核</button>&nbsp"
					+ "<button id=\"viewHistory2\" class=\"btn btn-default btn-sm\" onclick=\"viewHistory(this)\" >查看流程进展</button>";
		} else{
			return "<button id=\"startup\" class=\"btn btn-default btn-sm\" onclick=\"finisheTask(this)\" >我已经处理完毕</button>&nbsp"
					+ "<button id=\"viewHistory2\" class=\"btn btn-default btn-sm\" onclick=\"viewHistory(this)\" >查看流程进展</button>";
		}
	}

	
	public String getOrderNum(){
		Map<String,String> t = (Map<String,String>) getCurrentRowObject();
		String orderNum = t.get("orderNum");
		//return "<a href=\"javascript:$('#detailBtn').click()\">" + orderNum + "</a>";
		return "<a onclick=\"openOrderInfo(this)\">" + orderNum + "</a>";
	}
	

	
/*	@Override
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		// TODO Auto-generated method stub
		List<Task> taskQuery = (List<Task>) pageContext.getRequest().getAttribute("pageList");
		return null;
	}*/
	
	public String getDetailofHardwareProduct(){
		
		Software t = (Software) getCurrentRowObject();
		long id = t.getId();
		return "<a href=\"#\" id=\"popoverA" + id + "\" class=\"process-id popover-show\" onmousemove=\"hoverEventA(this)\" "
				+ "title=\"适配硬件产品\" "
				+ "data-container=\"body\" "
				+ "data-toggle=\"popover\" "
				+ "data-trigger=\"hover\">详情</a>";
	}
	
	public String getDetailofHardwarePlatform(){
		Software t = (Software) getCurrentRowObject();
		long id = t.getId();
		return "<a href=\"#\" id=\"popoverB" + id + "\" class=\"process-id popover-show\" onmousemove=\"hoverEventB(this)\" "
				+ "title=\"适配硬件产品\" "
				+ "data-container=\"body\" "
				+ "data-toggle=\"popover\" "
				+ "data-trigger=\"hover\">详情</a>";
		
	}
	
	public String getDetailofOS(){
		Software t = (Software) getCurrentRowObject();
		long id = t.getId();
		return "<a href=\"#\" id=\"popoverC" + id + "\" class=\"process-id popover-show\" onmousemove=\"hoverEventC(this)\" "
				+ "title=\"适配硬件产品\" "
				+ "data-container=\"body\" "
				+ "data-toggle=\"popover\" "
				+ "data-trigger=\"hover\">详情</a>";
		
	}
	
	public String getDetailofOsnames(){
		HardwareStation h = (HardwareStation) getCurrentRowObject();
		long id = h.getId();
		return "<a href=\"#\" id=\"popoverD" + id + "\" class=\"process-id popover-show\" onmousemove=\"hoverEventD(this)\" "
				+ "title=\"适配操作系统\" "
				+ "data-container=\"body\" "
				+ "data-toggle=\"popover\" "
				+ "data-trigger=\"hover\">详情</a>";
		
	}
	

}
