package com.routon.plcloud.admin.nb.service;

import java.util.List;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.Position;

public interface PositionService {
	
	//显示所有位置信息
	public List<Position> getAllPosition();
	
	//删除位置信息
	public int delPosition(Integer id);
	
	//添加位置信息
	public int insertPosition(Position position);
	
	//修改位置信息
	public int updatePosition(Position position);

	
	//查询单条位置信息
	public Position selectBydeviceid(String deviceid);

	
	public Position queryPositionbyDeviceid(String deviceid);
	
	//分页
	public PagingBean<Position> paging(int startIndex, int pageSize, String sortCriterion,
			String sortDirection, Long loginUserId, boolean exportflag);


}
