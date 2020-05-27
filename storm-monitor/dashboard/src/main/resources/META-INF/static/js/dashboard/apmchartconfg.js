$(function () {
    searchApmChartConfg();
});

//表格列字段和数据库字段的映射关系
var fieldMap4ApmChartConfg={};
fieldMap4ApmChartConfg['id']='id';
fieldMap4ApmChartConfg['chartName']='chart_name';
fieldMap4ApmChartConfg['description']='description';
fieldMap4ApmChartConfg['chartContent']='chart_content';
fieldMap4ApmChartConfg['createTime']='create_time';
fieldMap4ApmChartConfg['modifyTime']='modify_time';

/**
 * 点击表格列头排序
 * @param {type} sort
 * @param {type} order
 * @returns {undefined}
 */
function onSortColumn4ApmChartConfg(sort, order){
    $('#apmchartconfgSearchForm input[name="page.sortColumn"]').val(fieldMap4ApmChartConfg[sort]);
    $('#apmchartconfgSearchForm input[name="page.orderBy"]').val(order);
    searchApmChartConfg(); 
}

function onClickRow4ApmChartConfg(index, row){
}

/**
 * 表格数据加载成功后，刷新分页栏信息
 * @param {type} data
 * @returns {undefined}
 */
function onLoadSuccess4ApmChartConfg(data){
	var pager = $('#apmchartconfgtable').datagrid('getPager'); 
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
                    $('#apmchartconfgSearchForm input[name="page.pageNo"]').val(pageNumber);
                    $('#apmchartconfgSearchForm input[name="page.length"]').val(pageSize);
                    $(this).pagination('loaded'); 
                },
                onChangePageSize:function(pageSize){
                    $(this).pagination('loading'); 
                    $('#apmchartconfgSearchForm input[name="page.length"]').val(pageSize);
                    $(this).pagination('loaded'); 
                },
                onSelectPage:function(pageNumber, pageSize){ 
                    $(this).pagination('loading'); 
                    $('#apmchartconfgSearchForm input[name="page.pageNo"]').val(pageNumber);
                    $('#apmchartconfgSearchForm input[name="page.length"]').val(pageSize);
                    searchApmChartConfg(); 
                    $(this).pagination('loaded'); 
                }
    });
}

/**
 * 重新查询表格数据（显示进度条）
 * @returns {undefined}
 */
function searchApmChartConfg() {
    var win = $.messager.progress({
        title: '请稍候',
        msg: '搜索中，请稍候……'
    });
    setTimeout(searchApmChartConfgAsync, 0);
}

/**
 * 重新查询表格数据
 * @returns {undefined}
 */
function searchApmChartConfgAsync() {
    var myData = $("#apmchartconfgSearchForm").serializeArray();
    //alert(JSON.stringify(myData));
    $.ajax({
        type: "post",
        url: "queryapmchartconfgpage",
        complete: function () {
            $.messager.progress('close');
        },
        data: myData,
        async: false,
        dataType: "json",
        success: function (data1) {
            $('#apmchartconfgtable').datagrid('loadData', data1);
            //alert(data1.length);
        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}



//添加表单
function openAddApmChartConfgDialog() {
    showWindow({
        title: '添加表单',
        href: 'gotoapmchartconfgedit',
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//添加表单
function openUpdateApmChartConfgDialog() {
    var row = $('#apmchartconfgtable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要修改的记录！', 'info');
        return;
    }
    showWindow({
        title: '添加表单',
        href: 'gotoapmchartconfgedit?id='+row.id,
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//提交表单数据
function submitApmChartConfgInfo() {
    //alert($('#apmchartconfgform').form('validate'));
    if($('#apmchartconfgform').form('validate')==false){
        return;
    }
    var pjData = $("#apmchartconfgform").serializeArray();

    //alert(JSON.stringify(pjData));
    $.ajax({
        type: "post",
        url: "addapmchartconfg",
        dataType: "json",
        data: pjData,
        async: false,
        success: function (data1) {
            if (data1 && data1.id) {
                $('#apmchartconfgtable').datagrid('load', {

                });
                //关闭新增/修改窗口
                closeWindow();
                //刷新查询列表
                searchApmChartConfg();
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
function deleteApmChartConfg(){
    var row = $('#apmchartconfgtable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要删除的记录！', 'info');
        return;
    }
    $.messager.confirm('?','您确定要删除本图表配置吗?',function(r) {
		if (r) {
			$.ajax({
				type : "post",
				url : "deleteapmchartconfg",
				data : {
					id:row.id
				},
				async : false,
				success : function(data1) {
					if(data1==true){
						 //$.messager.alert('提示','id为['+row.id+ ']的图表配置删除成功！', 'info');
                                                 searchApmChartConfg()
					}else{
						 $.messager.alert('提示', '图表配置删除失败:' + data1, 'info');
					}
				},
				error : function(data, type) {
					$.messager.alert('提示', '图表配置删除失败:'+data + type, 'info');
				}
			});
		}
	});
}

//******************************************************
//                    子表列表区域 
//******************************************************
