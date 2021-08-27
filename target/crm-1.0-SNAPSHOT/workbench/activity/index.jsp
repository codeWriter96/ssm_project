<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basepath = request.getScheme() + "://"
			+ request.getServerName() +
			":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
	<base href="<%=basepath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

	<script type="text/javascript">
	$(function(){
		pageList(1,5)
		securityCheck()
		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});
		$(":text").blur(function () {
			trimSpace(this)
		})
		$("#addBtn").click(function () {
			$("#createActivityModal").modal("show")
		})
		$("#saveBtn").click(function () {
			$("#selectAll").prop('checked',false)
			save()
		})
		$("#searchBtn").click(function () {
			$("#selectAll").prop('checked',false)
			$("#hidden-name").val($("#search-name").val())
			$("#hidden-owner").val($("#search-owner").val())
			$("#hidden-startDate").val($("#search-startDate").val())
			$("#hidden-endDate").val($("#search-endDate").val())
			pageList(1,5);
			$("#msg").show()
		})


		$("#selectAll").click(function () {
			$("input[name=oneCheckBox]").prop("checked",this.checked)
		})

		$("#show-Activity").on("click",$("input[name=oneCheckBox]"),function () {
			$("#selectAll").prop("checked",$("input[name=oneCheckBox]").length==$("input[name=oneCheckBox]:checked").length)
		})

		$("#deleteBtn").click(function () {
			deleteActivity()
		})

		$("#editBtn").click(function () {
			var $num = $("input[name=oneCheckBox]:checked").length
			if(0 ==$num){
				alert("未选择需要修改的活动")
				$("#editActivityModal").modal("show")
			}else if($num>1){
				alert("请勿多项选择")
			}else {
				var $id =$("input[name=oneCheckBox]:checked").val()
				$.ajax({
					url:"workbench/activity/get1.do",
					type:"post",
					data:{"id":$id},
					dataType:"json",
					success:function (data) {
						if(data.success){
							$("#editActivityModal").modal("show")
						}else {
							alert(data.msg)
						}
					}
				})

			}

		})
	})

	function deleteActivity() {
		var $deleteDate = $("input[name=oneCheckBox]:checked")
		if($deleteDate.length==0){
			alert("未选择任何活动记录")
		}else{
			if (confirm("确定删除选中的活动记录吗？")){
				var param = ''
				for(var i=0;i<$deleteDate.length;i++){
					param += 'id='+$deleteDate[i].value
					if(i<$deleteDate.length-1){
						param += '&'
					}
				}
				$.ajax({
					url:"workbench/activity/delete.do",
					type:"post",
					data:param,
					dataType:"json",
					success:function (data) {
						if(data.success){
							alert("删除成功")
							pageList(1,5)
						}else {
							alert(data.msg)
							pageList(1,5)
						}
					}
				})
			}
		}
	}
	function securityCheck() {
		var param ={"owner":'${user.id}'}
		$.ajax({
			url:"setting/user/authorityCheck.do",
			data:param,
			datatype:"json",
			type:"get",
			success:function (data) {
				if(data.success){
					$("#deleteBtn").prop("style", "display:block;");
				}
			}


		})
	}


	function pageList(pageNo,pageSize) {
		$("#search-name").val($("#hidden-name").val())
		$("#search-owner").val($("#hidden-owner").val())
		$("#search-startDate").val($("#hidden-startDate").val())
		$("#search-endDate").val($("#hidden-endDate").val())
		$.ajax({
			url:"workbench/activity/pagelist.do",
			data:{"pageNo":pageNo,
				  "pageSize":pageSize,
				  "name":$("#search-name").val(),
				  "owner":$("#search-owner").val(),
				  "startDate":$("#search-startDate").val(),
				  "endDate":$("#search-endDate").val()
			},
			type:"get",
			dataType:"json",
			success:function (data) {
				if(data.success){
					$("#msg").html("查询成功，共"+data.total+"条数据")
					var html = '';
					$.each(data.activityList,function (i,n) {
						html += '<tr class="active">'
						html += '<td><input type="checkbox" value='+n.id+' name="oneCheckBox" /></td>'
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.jsp\';">'+n.name+'</a></td>'
						html += '<td>'+n.userName+'</td>'
						html += '<td>'+n.startDate+'</td>'
						html += '<td>'+n.endDate+'</td>'
						html += '<td>'+n.createTime+'</td>'
						html += '</tr>'
					})
					$("#show-Activity").html(html)
					var totalP =data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
					$("#activityPage").bs_pagination({
						currentPage: pageNo, // 页码
						rowsPerPage: pageSize, // 每页显示的记录条数
						maxRowsPerPage: 20, // 每页最多显示的记录条数
						totalPages:totalP, // 总页数
						totalRows: data.total, // 总记录条数
						visiblePageLinks: 5, // 显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,

						onChangePage : function(event, data){
							pageList(data.currentPage , data.rowsPerPage);
							$("#selectAll").prop('checked',false)
						}
					});

				}else{
					$("#msg").html(data.msg)
				}
			}
		})
	}

	function trimSpace(obj){
		$(obj).val($.trim($(obj).val()))
	}

	function save() {
		$.ajax({
			url:"workbench/activity/save.do",
			data:{
				"owner":$("#create-owner").val(),
				"name":$("#create-name").val(),
				"startDate":$("#create-startDate").val(),
				"endDate":$("#create-endDate").val(),
				"cost":$("#create-cost").val(),
				"description":$("#create-description").val(),
			},
			type:"post",
			dataType:"json",
			success:function (data) {
				if(data.success){
					alert("创建成功")
					$("#createActivityModal").modal("hide")
					$("#createActivityForm")[0].reset()
					pageList(1,5)
				}else{
					alert(data.msg)
				}
			}
		})
	}
	
</script>
</head>
<body>
	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>


	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="createActivityForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner" readonly="true">
								  <option>${user.name}</option>
								</select>
							</div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label"><span style="font-size: 15px; color: red;">*</span>开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate">
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本(人民币)</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="saveBtn" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner" readonly="true">
								  <option>${user.name}</option>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input id="search-name" class="form-control" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input id="search-owner" class="form-control" type="text">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" id="searchBtn" class="btn btn-primary">查询</button>
					<button type="reset" class="btn btn-primary">重置</button>
				</form>
				<center>
					<p hidden id="msg" style="color: red"></p>
				</center>

			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">

				  <button type="button" class="btn btn-primary" id="addBtn" ><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" style="display:none" id="deleteBtn" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="selectAll" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
							<td>创建日期</td>
						</tr>
					</thead>
					<tbody id="show-Activity">

					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id ="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>