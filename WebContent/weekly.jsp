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
		url:"<%=root%>/beforeParentServlet",
		method:'post',
		pageSize:50,
		fit: true,
		toolbar:"#tb",
		singleSelect: true,
		autoRowHeight: false, //自动行高
		border:false,
		pagination:true, //分页栏
		pagePosition:'bottom', //分页栏位置
		rownumbers:true,//显示行数
	    fitColumns:true,//自适应列宽
	    striped:true,//显示条纹
	    showFooter:true, //显示统计行
        remoteSort:false,//是否通过远程服务器对数据排序
	    sortName:'id',//默认排序字段
		sortOrder:'asc',//默认排序方式 'desc' 'asc'
		idField : 'id',
		columns : [ [ 
		    {field : 'id',title : 'ID',align : 'left',width : 45}, 
			{field : 'pic',title : '照片',width : 100}, 
			{field : 'week',title : '孩子的时长',width : 100}, 
			{field : 'outline',title : '卷首语',width : 150}, 
			{field : 'mamiRead',title : '本周孕妈妈必读',width : 150}, 
			{field : 'parentKnow',title : '准爸妈要知道',width : 200}, 
			{field : 'focus',title : '本周特别关注',width : 200}, 
			{field : 'cookbook',title : '温馨食谱',width : 200}, 
			{field : 'taegyo',title : '本周胎教',width : 200}, 
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
	            //edit(rowIndex, rowData);
	        	var imgStr = "<img src=/weixinImage/"+rowData['pic']+" width='100' height='100'></img>";
	        	$("#datagrid_img").html(imgStr);
	        	$("#datagrid_img").show();
	        },       
			
		});
});

function add(){
	editDialog("添加图文消息","#add_from",datagrid,"setbefore.jsp",550,600);
}

function removeit(){
	delData(datagrid,"<%=root%>/beforeParentServlet?method=del");
}

//删除数据
function delData(datagrid,actUrl){
	postData(actUrl,true,'数据删除后无法恢复，确定要删除数据吗？',datagrid,true);
}

function edit11(rowIndex,rowData){
	editData(rowIndex,rowData,"编辑",'#edit_form',datagrid,"editbefore.jsp",550,600);
}

function editData(rowIndex,rowData,title,formId,datagrid,actUrl,width,height,field,maximized){
	if(field==undefined || field=="") field="id";
    if(rowIndex != undefined) {
    	fieldValue = eval("rowData."+field);
    	return editDialog(title,formId,datagrid,setUrl(actUrl,field,fieldValue),width,height,maximized);        
    }
    //选中的所有行
	var rows = datagrid.datagrid('getSelections');
	//选中的行（第一次选择的行）
	var row = datagrid.datagrid('getSelected');
	if (row){
		if(rows.length>1){
			row = rows[rows.length-1];
			$.messager.alert("提示信息","您选择了多个操作对象，默认操作最后一次被选中的记录！");
		}
		fieldValue = eval("row."+field);
		return editDialog(title,formId,datagrid,setUrl(actUrl,field,fieldValue),width,height,maximized);
	}else{
		$.messager.alert("提示信息","请选择要操作的对象！");
	}
}

function setUrl(url, ref, value) {
	if(url==undefined) url='';
    var str = "";
    if (url.indexOf('?') != -1)
        str = url.substr(url.indexOf('?') + 1);
    else
        return url + "?" + ref + "=" + value;
    var returnurl = "";
    var setparam = "";
    var arr;
    var modify = "0";
    if (str.indexOf('&') != -1) {
        arr = str.split('&');
        for (i in arr) {
            if (arr[i].split('=')[0] == ref) {
                setparam = value;
                modify = "1";
            }
            else {
                setparam = arr[i].split('=')[1];
            }
            returnurl = returnurl + arr[i].split('=')[0] + "=" + setparam + "&";
        }
        returnurl = returnurl.substr(0, returnurl.length - 1);
        if (modify == "0")
            if (returnurl == str)
                returnurl = returnurl + "&" + ref + "=" + value;
    }
    else {
        if (str.indexOf('=') != -1) {
            arr = str.split('=');
            if (arr[0] == ref) {
                setparam = value;
                modify = "1";
            }
            else {
                setparam = arr[1];
            }
            returnurl = arr[0] + "=" + setparam;
            if (modify == "0")
                if (returnurl == str)
                    returnurl = returnurl + "&" + ref + "=" + value;
        }
        else
            returnurl = ref + "=" + value;
    }
    return url.substr(0, url.indexOf('?')) + "?" + returnurl;
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

</script>
</head>
<body>
	<table id="dg">
		 <div id="tb" style="height:auto">
	        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add()">添加</a>
	        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit()">编辑</a>
	        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">移除</a>
	    </div>
	    <div id="datagrid_img" class="datagrid_img" style="display: block;position: absolute;width: 100px;height: 100px;">
    	</div>
    </table>
</body>
</html>