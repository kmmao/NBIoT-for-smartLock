<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="${ctx}/js/pagination.js"></script>
<c:if  test="${task_no_received_maxpage > 0}">
 
<div class="panel-footer"  style="padding-top: 7px;padding-bottom: 3px;padding-left: 10px;background-color:#f5f5f5;border-bottom-right-radius: 3px;border-bottom-left-radius: 3px; " >
		
			<div class="btn-group">
				<div class="btn-group" style="margin-right: 6px;">
				  <ul class="pagination" style="margin-bottom: 0px;margin-top: 0px;">
					<c:choose> 
						<c:when test="${task_no_received_page > 1}">  
							<li><a href="javascript:void(0);" onclick="gotopage2(${task_no_received_page-1},${task_no_received_page},${task_no_received_maxpage})"  >&laquo;</a></li>
						</c:when>
						<c:otherwise>
							
							<li class="disabled" ><a href="javascript:void(0);" onclick="gotopage2(${task_no_received_page-1},${task_no_received_page},${task_no_received_maxpage})"  >&laquo;</a></li>
							
						</c:otherwise>
					</c:choose>
					
					<c:choose> 
						<c:when test="${task_no_received_maxpage - task_no_received_page >= 5}">  
							<c:set var="j" value="1"></c:set>
							<c:forEach var="i" begin="${task_no_received_page}" end="${task_no_received_maxpage}">
								<c:choose> 
									<c:when test="${j == 6}">  
									
										<li><span class="dot">...</span></li>
										<li><a href="javascript:void(0);" onclick="gotopage2(${task_no_received_maxpage},${task_no_received_page},${task_no_received_maxpage})" >${task_no_received_maxpage}</a></li>
									</c:when>
									<c:when test="${j < 6}">  
										<c:choose> 
											<c:when test="${j==1}">  
												<li class="active" ><a href="javascript:void(0);"  >${i}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="javascript:void(0);" onclick="gotopage2(${i},${task_no_received_page},${task_no_received_maxpage})" >${i}</a></li>
											</c:otherwise>
										</c:choose>
										
									</c:when>
								</c:choose>
								
								<c:set var="j" value="${j+1}"></c:set>  
							</c:forEach>  
						</c:when>
						<c:otherwise>
							<c:forEach var="i" begin="${(task_no_received_maxpage - 4)<=0?1:(task_no_received_maxpage - 4)}" end="${task_no_received_maxpage}">
								<c:choose> 
									<c:when test="${i==task_no_received_page}">  
										<li class="active"><a href="javascript:void(0);"  >${i}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="javascript:void(0);" onclick="gotopage2(${i},${task_no_received_page},${task_no_received_maxpage})" >${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>
						</c:choose>
						
						<c:choose> 
							<c:when test="${task_no_received_page == task_no_received_maxpage}">  
								<li class="disabled"><a href="javascript:void(0);"   >&raquo;</a></li>
								
							</c:when>
							<c:otherwise>
								<li><a href="javascript:void(0);" onclick="gotopage2(${task_no_received_page+1},${task_no_received_page},${task_no_received_maxpage})"  >&raquo;</a></li>
								
							</c:otherwise>
						</c:choose>
					
			   		</ul>
			   	</div>
			   	<div class="btn-group">
			   		<form class="form-inline" role="form" >  		
		  			<div class="btn-group">
		  				<label for="exampleInputEmail1">第</label>
		  			</div>
		  			<div class="btn-group input-group-sm">
		  				<input id="pagination_page" name="pagination_page" type="text" class="form-control"  style="width: 40px;">
		  			</div>
		  			<div class="btn-group">
		  				<label for="exampleInputEmail1">页</label> 
		  			</div>
		  			<div class="btn-group">
		  				<button type="button" class="btn btn-primary" onclick="gotopage2(null,${task_no_received_page},${task_no_received_maxpage})">确定</button>
		  			</div>
		  			<div class="btn-group">
		  				<label for="exampleInputEmail1">共${taskQuerySize}条记录</label>
		  			</div>
		  			</form>
			   	</div>	
			   	
			</div>
</div> 
 	</c:if>

  
