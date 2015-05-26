<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String root = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.5/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.5/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(function() {
	datagrid = $('#dg').datagrid({
		url:"<%=root%>/questionServlet?method='getdata'",
		method:'get',
		pageSize:30,
		fit: true,
		toolbar:"#tb",
		singleSelect: true,
		autoRowHeight: false, //自动行高
		border:false,
		pagination:true, //分页栏
		pagePosition:'bottom', //分页栏位置
		rownumbers:true,//显示行数
	    //fitColumns:true,//自适应列宽
	    striped:true,//显示条纹
	    showFooter:true, //显示统计行
        remoteSort:false,//是否通过远程服务器对数据排序
	    sortName:'id',//默认排序字段
		sortOrder:'asc',//默认排序方式 'desc' 'asc'
		idField : 'id',
		columns : [ [ 
		    {field : 'id',title : 'ID',align : 'left',width : 45}, 
			{field : 'name',title : '昵称',width : 150}, 
			{field : 'statusDis',title : '状态',width : 150}, 
			{field : 'question',title : '问题',width : 200}, 
			{field : 'answer',title : '答案',width : 400}, 
			] ],
			onLoadSuccess:function(){
		    	$(this).datagrid('clearSelections');//取消所有的已选择项
		    	$(this).datagrid('unselectAll');//取消全选按钮为全选状态
			},		
		    onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#datagrid_menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
	        onDblClickRow:function(rowIndex, rowData){
	            edit(rowIndex, rowData);
	        },       
			
		});
});

function add(){
	editDialog("添加图文消息","#add_from",datagrid,"single.jsp",350,230);
}

function removeit(){
	delData(datagrid,"<%=root%>/imageTextServlet?method=del");
}

//删除数据
function delData(datagrid,actUrl){
	postData(actUrl,true,'数据删除后无法恢复，确定要删除数据吗？',datagrid,true);
}

//提交数据
function postData(actUrl,isShowMsg,confirmText,datagrid,isGetRow,field){
	if(field==undefined || field=="") field="id";	
	var rows = "1";
	var params = '';
	if(datagrid!=undefined && isGetRow==true){
		rows = datagrid.datagrid('getSelections');
		var ids = new Object();
		for(var i=0;i<rows.length;i++){
			ids[i] = eval("rows[i]."+field);
		}		
		//params = eval("{'" + field + "':ids}");
		params = ids[0];
	}
	if(rows.length >0){
		if(confirmText == undefined || confirmText==''){
			post(actUrl,isShowMsg,datagrid,params);
		}else{
			$.messager.confirm('确认提示！',confirmText,function(r){
				if(r){
					post(actUrl,isShowMsg,datagrid,params);
				}
			});
		}
	}else{
		$.messager.alert('提示信息',"请选择要操作的对象！");
	}
}


function post(actUrl,isShowMsg,datagrid,params){
	if(params==undefined) params = '';
	$.get(actUrl+"&params="+params,params,function(data){
		if (data.status==1){			
			if(isShowMsg==undefined || isShowMsg) $.messager.alert('提示信息',textDecode(textDecode(data.info)));
			if(datagrid!=undefined) datagrid.datagrid('load');	// reload the user data
		} else {
			$.messager.alert('错误信息',textDecode(textDecode(data.info)));
		}
	},'json');	
}

function editDialog(title,formId,datagrid,actUrl,width,height,maximized){
	if(datagrid==undefined || datagrid=='') datagrid==null;
	if(width==undefined) width=400;
	if(height==undefined) width=300;
	if(formId==undefined || formId==null) formId="";
	var the_form = null;
	var the_dialog;	
	the_dialog = $("<div/>").dialog({
		title: title,
		width: width,
		height: height,
		href: actUrl,
		modal: true,
		maximizable: true,
		maximized: (maximized != undefined ? maximized : false),
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				document.addfrom.submit();
				$('#dg').datagrid('reload');
				//if(the_form==null){
				//	the_dialog.dialog('destroy');
				//}else{
				//	the_form.submit();
				//}
			}
		},{
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				the_dialog.dialog('destroy');
			}
		}],		
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function(data){
			//判断是否返回JSON格式数据（主要用于权限处理）
			//var pattern = /<form[^>]*>((.|[\n\r])*)<\/form>/im;
			//var matches = pattern.exec(data);
			if(isJson(data)){
				var json = $.parseJSON(data);
				$.messager.alert('提示信息',textDecode(json.info));
				the_dialog.dialog('destroy');//销毁对话框 
				return;
			}
			if(formId=="") return;
			//初始化表单
			the_form = $(formId).form({
				url: actUrl,
				onSubmit: function(param){ 
					$.messager.progress({ title : '提示信息', text : '数据处理中，请稍候....'	});
			    },
				success: function(data){
					$.messager.progress('close');
					var json = $.parseJSON(data); 
					
					if (json.status == 1){							
						if(datagrid!=null) datagrid.datagrid('reload');	// reload the user data
						$.messager.alert('提示信息',textDecode(json.info));//操作结果提示						
					}else {
						$.messager.alert('错误信息',textDecode(json.info));//操作结果提示
					}
					the_dialog.dialog('destroy');//销毁对话框 
				}
			    
			});
			
		},
	}).dialog('open');
	return the_dialog;
}

	function CurentTime() {
		var now = new Date();
		var year = now.getFullYear(); //年
		var month = now.getMonth() + 1; //月
		var day = now.getDate(); //日
		var hh = now.getHours(); //时
		var mm = now.getMinutes(); //分
		var clock = year + "-";
		if (month < 10)
			clock += "0";
		clock += month + "-";
		if (day < 10)
			clock += "0";
		clock += day + " ";
		if (hh < 10)
			clock += "0";
		clock += hh + ":";
		if (mm < 10)
			clock += '0';
		clock += mm;
		return (clock);
	}
</script>
</head>
<body>
	<table id="dg">
		 <div id="tb" style="height:auto">
	        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add()">添加</a>
	        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="accept()">编辑</a>
	        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">移除</a>
	    </div> 
    </table>
</body>
</html>