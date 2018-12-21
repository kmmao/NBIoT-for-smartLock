package com.routon.plcloud.admin.nb.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.admin.nb.service.PositionService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.Position;
import com.routon.plcloud.common.persistence.PositionMapper;

@Service("positionService")
public class PositionServiceImpl implements PositionService {
	
	@Resource(name = "pagingDaoMybatis")
    private PagingDaoMybatis pagingDao;
	
	@Autowired
	private PositionMapper positionMapper;

	@Override
	public List<Position> getAllPosition() {
		// TODO Auto-generated method stub
		return positionMapper.getAllPosition();
	}

	@Override
	public int delPosition(Integer id) {
		// TODO Auto-generated method stub
		return positionMapper.delPosition(id);
	}

	@Override
	public int insertPosition(Position position) {
		// TODO Auto-generated method stub
		return positionMapper.insertPosition(position);
	}

	@Override
	public int updatePosition(Position position) {
		// TODO Auto-generated method stub
		return positionMapper.updatePosition(position);
	}


	@Override
	public Position selectBydeviceid(String deviceid) {
		// TODO Auto-generated method stub
		return positionMapper.selectBydeviceid(deviceid);
	}


	@Override
	public Position queryPositionbyDeviceid(String deviceid) {
		return positionMapper.queryPositionbyDeviceid(deviceid);
	}

	@Override
	public PagingBean<Position> paging(int startIndex, int pageSize, String sortCriterion, String sortDirection,
			Long loginUserId, boolean exportflag) {
		String pagingQueryLanguage = "select a.* from position a";
		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);
		String[] sortCriterions = null;
		if(sortCriterion != null){
			sortCriterions = new String[] { "a." + sortCriterion };
		}
		PagingSortDirection[] sortDirections =null;
		if(sortDirection != null){
			sortDirections = new PagingSortDirection[] { "desc"
					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
					: PagingSortDirection.ASC };
		}
		PagingBean<Position> pagingPosition = pagingDao.query(positionMapper, sbHQL.toString(), 
				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);
		return pagingPosition;
	}
	
	


}
