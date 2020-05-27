$(function () {
    searchCustomDataPickLog();
});

//表格列字段和数据库字段的映射关系
var fieldMap4CustomDataPickLog={};
fieldMap4CustomDataPickLog['id']='id';
fieldMap4CustomDataPickLog['logTime']='log_time';
fieldMap4CustomDataPickLog['serviceName']='service_name';
fieldMap4CustomDataPickLog['machineAddress']='machine_address';
fieldMap4CustomDataPickLog['traceId']='trace_id';
fieldMap4CustomDataPickLog['userKey']='user_key';
fieldMap4CustomDataPickLog['pickData']='pick_data';

/**
 * 点击表格列头排序
 * @param {type} sort
 * @param {type} order
 * @returns {undefined}
 */
function onSortColumn4CustomDataPickLog(sort, order){
    $('#customdatapicklogSearchForm input[name="page.sortColumn"]').val(fieldMap4CustomDataPickLog[sort]);
    $('#customdatapicklogSearchForm input[name="page.orderBy"]').val(order);
    searchCustomDataPickLog(); 
}

function onClickRow4CustomDataPickLog(index, row){
}

/**
 * 表格数据加载成功后，刷新分页栏信息
 * @param {type} data
 * @returns {undefined}
 */
function onLoadSuccess4CustomDataPickLog(data){
	var pager = $('#customdatapicklogtable').datagrid('getPager'); 
	var pager_option=pager.pagination('options');
	var t_size=10;
	var t_number=1;
	if(pager_option){
		t_size=pager_option.pageSize;
		t_number=pager_option.pageNumber;
	}
	
	pager.pagination({  
		pageSize:t_size,  //每页显示的记录条数，默认为10  
		pageNumber:t_number,  //在分页控件创建的时候显示的页数。 
                beforePageText: '第',//页数文本框前显示的汉字  
                afterPageText: '页    共 {pages} 页',  
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
                onBeforeRefresh:function(pageNumber, pageSize){ 
                    $(this).pagination('loading'); 
                    $('#customdatapicklogSearchForm input[name="page.pageNo"]').val(pageNumber);
                    $('#customdatapicklogSearchForm input[name="page.length"]').val(pageSize);
                    $(this).pagination('loaded'); 
                },
                onChangePageSize:function(pageSize){
                    $(this).pagination('loading'); 
                    $('#customdatapicklogSearchForm input[name="page.length"]').val(pageSize);
                    $(this).pagination('loaded'); 
                },
                onSelectPage:function(pageNumber, pageSize){ 
                    $(this).pagination('loading'); 
                    $('#customdatapicklogSearchForm input[name="page.pageNo"]').val(pageNumber);
                    $('#customdatapicklogSearchForm input[name="page.length"]').val(pageSize);
                    searchCustomDataPickLog(); 
                    $(this).pagination('loaded'); 
                }
    });
}

/**
 * 重新查询表格数据（显示进度条）
 * @returns {undefined}
 */
function searchCustomDataPickLog() {
    var win = $.messager.progress({
        title: '请稍候',
        msg: '搜索中，请稍候……'
    });
    setTimeout(searchCustomDataPickLogAsync, 0);
}

/**
 * 重新查询表格数据
 * @returns {undefined}
 */
function searchCustomDataPickLogAsync() {
    var myData = $("#customdatapicklogSearchForm").serializeArray();
    //alert(JSON.stringify(myData));
    $.ajax({
        type: "post",
        url: "querycustomdatapicklogpage",
        complete: function () {
            $.messager.progress('close');
        },
        data: myData,
        async: false,
        dataType: "json",
        success: function (data1) {
            $('#customdatapicklogtable').datagrid('loadData', data1);
            //alert(data1.length);
        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}



//添加表单
function openAddCustomDataPickLogDialog() {
    showWindow({
        title: '添加表单',
        href: 'gotocustomdatapicklogedit',
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//添加表单
function openUpdateCustomDataPickLogDialog() {
    var row = $('#customdatapicklogtable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要修改的记录！', 'info');
        return;
    }
    showWindow({
        title: '添加表单',
        href: 'gotocustomdatapicklogedit?id='+row.id,
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//提交表单数据
function submitCustomDataPickLogInfo() {
    //alert($('#customdatapicklogform').form('validate'));
    if($('#customdatapicklogform').form('validate')==false){
        return;
    }
    var pjData = $("#customdatapicklogform").serializeArray();

    //alert(JSON.stringify(pjData));
    $.ajax({
        type: "post",
        url: "addcustomdatapicklog",
        dataType: "json",
        data: pjData,
        async: false,
        success: function (data1) {
            if (data1 && data1.id) {
                $('#customdatapicklogtable').datagrid('load', {

                });
                //关闭新增/修改窗口
                closeWindow();
                //刷新查询列表
                searchCustomDataPickLog();
            } else {
                $.messager.alert('提示', '表单添加失败！', 'info');
            }
        },
        error: function (data, type) {
            $.messager.alert('提示', '表单添加失败:' + data + type, 'info');
        }
    });
}

/**
 * 删除一条记录
 * @returns {undefined}
 */
function deleteCustomDataPickLog(){
    var row = $('#customdatapicklogtable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要删除的记录！', 'info');
        return;
    }
    $.messager.confirm('?','您确定要删除本业务数据采集汇总表吗?',function(r) {
		if (r) {
			$.ajax({
				type : "post",
				url : "deletecustomdatapicklog",
				data : {
					id:row.id
				},
				async : false,
				success : function(data1) {
					if(data1==true){
						 //$.messager.alert('提示','id为['+row.id+ ']的业务数据采集汇总表删除成功！', 'info');
                                                 searchCustomDataPickLog()
					}else{
						 $.messager.alert('提示', '业务数据采集汇总表删除失败:' + data1, 'info');
					}
				},
				error : function(data, type) {
					$.messager.alert('提示', '业务数据采集汇总表删除失败:'+data + type, 'info');
				}
			});
		}
	});
}

//******************************************************
//                    子表列表区域 
//******************************************************
