$(function () {
    searchErrorLog();
});

//表格列字段和数据库字段的映射关系
var fieldMap4ErrorLog = {};
fieldMap4ErrorLog['id'] = 'id';
fieldMap4ErrorLog['logTime'] = 'log_time';
fieldMap4ErrorLog['machineAddress'] = 'machine_address';
fieldMap4ErrorLog['serviceName'] = 'service_name';
fieldMap4ErrorLog['errorType'] = 'error_type';
fieldMap4ErrorLog['traceId'] = 'trace_id';
fieldMap4ErrorLog['userKey'] = 'user_key';
fieldMap4ErrorLog['errorCode'] = 'error_code';
fieldMap4ErrorLog['errorMsg'] = 'error_msg';

/**
 * 点击表格列头排序
 * @param {type} sort
 * @param {type} order
 * @returns {undefined}
 */
function onSortColumn4ErrorLog(sort, order) {
    $('#errorlogSearchForm input[name="page.sortColumn"]').val(fieldMap4ErrorLog[sort]);
    $('#errorlogSearchForm input[name="page.orderBy"]').val(order);
    searchErrorLog();
}

function onClickRow4ErrorLog(index, row) {
}

/**
 * 表格数据加载成功后，刷新分页栏信息
 * @param {type} data
 * @returns {undefined}
 */
function onLoadSuccess4ErrorLog(data) {
    var pager = $('#errorlogtable').datagrid('getPager');
    var pager_option = pager.pagination('options');
    var t_size = 10;
    var t_number = 1;
    if (pager_option) {
        t_size = pager_option.pageSize;
        t_number = pager_option.pageNumber;
    }

    pager.pagination({
        pageSize: t_size, //每页显示的记录条数，默认为10  
        pageNumber: t_number, //在分页控件创建的时候显示的页数。 
        beforePageText: '第', //页数文本框前显示的汉字  
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#errorlogSearchForm input[name="page.pageNo"]').val(pageNumber);
            $('#errorlogSearchForm input[name="page.length"]').val(pageSize);
            $(this).pagination('loaded');
        },
        onChangePageSize: function (pageSize) {
            $(this).pagination('loading');
            $('#errorlogSearchForm input[name="page.length"]').val(pageSize);
            $(this).pagination('loaded');
        },
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#errorlogSearchForm input[name="page.pageNo"]').val(pageNumber);
            $('#errorlogSearchForm input[name="page.length"]').val(pageSize);
            searchErrorLog();
            $(this).pagination('loaded');
        }
    });
}

/**
 * 重新查询表格数据（显示进度条）
 * @returns {undefined}
 */
function searchErrorLog() {
    var win = $.messager.progress({
        title: '请稍候',
        msg: '搜索中，请稍候……'
    });
    setTimeout(searchErrorLogAsync, 0);
}

/**
 * 重新查询表格数据
 * @returns {undefined}
 */
function searchErrorLogAsync() {
    var myData = $("#errorlogSearchForm").serializeArray();
    //alert(JSON.stringify(myData));
    $.ajax({
        type: "post",
        url: "queryerrorlogpage",
        complete: function () {
            $.messager.progress('close');
        },
        data: myData,
        async: false,
        dataType: "json",
        success: function (data1) {
            $('#errorlogtable').datagrid('loadData', data1);
            //alert(data1.length);
        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}



//添加表单
function openAddErrorLogDialog() {
    showWindow({
        title: '添加表单',
        href: 'gotoerrorlogedit',
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//添加表单
function openUpdateErrorLogDialog() {
    var row = $('#errorlogtable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要修改的记录！', 'info');
        return;
    }
    showWindow({
        title: '添加表单',
        href: 'gotoerrorlogedit?id=' + row.id,
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//提交表单数据
function submitErrorLogInfo() {
    //alert($('#errorlogform').form('validate'));
    if ($('#errorlogform').form('validate') == false) {
        return;
    }
    var pjData = $("#errorlogform").serializeArray();

    //alert(JSON.stringify(pjData));
    $.ajax({
        type: "post",
        url: "adderrorlog",
        dataType: "json",
        data: pjData,
        async: false,
        success: function (data1) {
            if (data1 && data1.id) {
                $('#errorlogtable').datagrid('load', {

                });
                //关闭新增/修改窗口
                closeWindow();
                //刷新查询列表
                searchErrorLog();
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
function deleteErrorLog() {
    var row = $('#errorlogtable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要删除的记录！', 'info');
        return;
    }
    $.messager.confirm('?', '您确定要删除本异常信息汇总表吗?', function (r) {
        if (r) {
            $.ajax({
                type: "post",
                url: "deleteerrorlog",
                data: {
                    id: row.id
                },
                async: false,
                success: function (data1) {
                    if (data1 == true) {
                        //$.messager.alert('提示','id为['+row.id+ ']的异常信息汇总表删除成功！', 'info');
                        searchErrorLog()
                    } else {
                        $.messager.alert('提示', '异常信息汇总表删除失败:' + data1, 'info');
                    }
                },
                error: function (data, type) {
                    $.messager.alert('提示', '异常信息汇总表删除失败:' + data + type, 'info');
                }
            });
        }
    });
}

function formateErrorType(val, row) {
    if(val===0){
        return '系统错误';
    }else if(val===1){
        return '业务错误';
    }
    return val;
}
