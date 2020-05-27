$(function () {
    searchApmBusinessCfg();
});

//表格列字段和数据库字段的映射关系
var fieldMap4ApmBusinessCfg={};
fieldMap4ApmBusinessCfg['id']='id';
fieldMap4ApmBusinessCfg['businessKey']='business_key';
fieldMap4ApmBusinessCfg['title']='title';
fieldMap4ApmBusinessCfg['description']='description';
fieldMap4ApmBusinessCfg['classMethodName']='class_method_name';
fieldMap4ApmBusinessCfg['parameters']='parameters';
fieldMap4ApmBusinessCfg['returnResult']='return_result';
fieldMap4ApmBusinessCfg['createTime']='create_time';
fieldMap4ApmBusinessCfg['modifyTime']='modify_time';

/**
 * 点击表格列头排序
 * @param {type} sort
 * @param {type} order
 * @returns {undefined}
 */
function onSortColumn4ApmBusinessCfg(sort, order){
    $('#apmbusinesscfgSearchForm input[name="page.sortColumn"]').val(fieldMap4ApmBusinessCfg[sort]);
    $('#apmbusinesscfgSearchForm input[name="page.orderBy"]').val(order);
    searchApmBusinessCfg(); 
}

function onClickRow4ApmBusinessCfg(index, row){
}

/**
 * 表格数据加载成功后，刷新分页栏信息
 * @param {type} data
 * @returns {undefined}
 */
function onLoadSuccess4ApmBusinessCfg(data){
	var pager = $('#apmbusinesscfgtable').datagrid('getPager'); 
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
                    $('#apmbusinesscfgSearchForm input[name="page.pageNo"]').val(pageNumber);
                    $('#apmbusinesscfgSearchForm input[name="page.length"]').val(pageSize);
                    $(this).pagination('loaded'); 
                },
                onChangePageSize:function(pageSize){
                    $(this).pagination('loading'); 
                    $('#apmbusinesscfgSearchForm input[name="page.length"]').val(pageSize);
                    $(this).pagination('loaded'); 
                },
                onSelectPage:function(pageNumber, pageSize){ 
                    $(this).pagination('loading'); 
                    $('#apmbusinesscfgSearchForm input[name="page.pageNo"]').val(pageNumber);
                    $('#apmbusinesscfgSearchForm input[name="page.length"]').val(pageSize);
                    searchApmBusinessCfg(); 
                    $(this).pagination('loaded'); 
                }
    });
}

/**
 * 重新查询表格数据（显示进度条）
 * @returns {undefined}
 */
function searchApmBusinessCfg() {
    var win = $.messager.progress({
        title: '请稍候',
        msg: '搜索中，请稍候……'
    });
    setTimeout(searchApmBusinessCfgAsync, 0);
}

/**
 * 重新查询表格数据
 * @returns {undefined}
 */
function searchApmBusinessCfgAsync() {
    var myData = $("#apmbusinesscfgSearchForm").serializeArray();
    //alert(JSON.stringify(myData));
    $.ajax({
        type: "post",
        url: "queryapmbusinesscfgpage",
        complete: function () {
            $.messager.progress('close');
        },
        data: myData,
        async: false,
        dataType: "json",
        success: function (data1) {
            $('#apmbusinesscfgtable').datagrid('loadData', data1);
            //alert(data1.length);
        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}



//添加表单
function openAddApmBusinessCfgDialog() {
    showWindow({
        title: '添加表单',
        href: 'gotoapmbusinesscfgedit',
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//添加表单
function openUpdateApmBusinessCfgDialog() {
    var row = $('#apmbusinesscfgtable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要修改的记录！', 'info');
        return;
    }
    showWindow({
        title: '添加表单',
        href: 'gotoapmbusinesscfgedit?id='+row.id,
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//提交表单数据
function submitApmBusinessCfgInfo() {
    //alert($('#apmbusinesscfgform').form('validate'));
    if($('#apmbusinesscfgform').form('validate')==false){
        return;
    }
    var pjData = $("#apmbusinesscfgform").serializeArray();

    //alert(JSON.stringify(pjData));
    $.ajax({
        type: "post",
        url: "addapmbusinesscfg",
        dataType: "json",
        data: pjData,
        async: false,
        success: function (data1) {
            if (data1 && data1.id) {
                $('#apmbusinesscfgtable').datagrid('load', {

                });
                //关闭新增/修改窗口
                closeWindow();
                //刷新查询列表
                searchApmBusinessCfg();
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
function deleteApmBusinessCfg(){
    var row = $('#apmbusinesscfgtable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要删除的记录！', 'info');
        return;
    }
    $.messager.confirm('?','您确定要删除本apm_business_cfg吗?',function(r) {
		if (r) {
			$.ajax({
				type : "post",
				url : "deleteapmbusinesscfg",
				data : {
					id:row.id
				},
				async : false,
				success : function(data1) {
					if(data1==true){
						 //$.messager.alert('提示','id为['+row.id+ ']的apm_business_cfg删除成功！', 'info');
                                                 searchApmBusinessCfg()
					}else{
						 $.messager.alert('提示', 'apm_business_cfg删除失败:' + data1, 'info');
					}
				},
				error : function(data, type) {
					$.messager.alert('提示', 'apm_business_cfg删除失败:'+data + type, 'info');
				}
			});
		}
	});
}

//******************************************************
//                    子表列表区域 
//******************************************************
