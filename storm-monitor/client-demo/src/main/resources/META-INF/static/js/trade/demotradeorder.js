$(function () {
    searchDemoTradeOrder();
});

//表格列字段和数据库字段的映射关系
var fieldMap4DemoTradeOrder = {};
fieldMap4DemoTradeOrder['id'] = 'id';
fieldMap4DemoTradeOrder['skuName'] = 'sku_name';
fieldMap4DemoTradeOrder['skuPrice'] = 'sku_price';
fieldMap4DemoTradeOrder['orderQuantity'] = 'order_quantity';
fieldMap4DemoTradeOrder['customerName'] = 'customer_name';
fieldMap4DemoTradeOrder['deliveryDate'] = 'delivery_date';
fieldMap4DemoTradeOrder['deliveryPlace'] = 'delivery_place';
fieldMap4DemoTradeOrder['orderDesc'] = 'order_desc';

/**
 * 点击表格列头排序
 * @param {type} sort
 * @param {type} order
 * @returns {undefined}
 */
function onSortColumn4DemoTradeOrder(sort, order) {
    $('#demotradeorderSearchForm input[name="page.sortColumn"]').val(fieldMap4DemoTradeOrder[sort]);
    $('#demotradeorderSearchForm input[name="page.orderBy"]').val(order);
    searchDemoTradeOrder();
}

function onClickRow4DemoTradeOrder(index, row) {
}

/**
 * 表格数据加载成功后，刷新分页栏信息
 * @param {type} data
 * @returns {undefined}
 */
function onLoadSuccess4DemoTradeOrder(data) {
    var pager = $('#demotradeordertable').datagrid('getPager');
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
            $('#demotradeorderSearchForm input[name="page.pageNo"]').val(pageNumber);
            $('#demotradeorderSearchForm input[name="page.length"]').val(pageSize);
            $(this).pagination('loaded');
        },
        onChangePageSize: function (pageSize) {
            $(this).pagination('loading');
            $('#demotradeorderSearchForm input[name="page.length"]').val(pageSize);
            $(this).pagination('loaded');
        },
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $('#demotradeorderSearchForm input[name="page.pageNo"]').val(pageNumber);
            $('#demotradeorderSearchForm input[name="page.length"]').val(pageSize);
            searchDemoTradeOrder();
            $(this).pagination('loaded');
        }
    });
}

/**
 * 重新查询表格数据（显示进度条）
 * @returns {undefined}
 */
function searchDemoTradeOrder() {
    var win = $.messager.progress({
        title: '请稍候',
        msg: '搜索中，请稍候……'
    });
    setTimeout(searchDemoTradeOrderAsync, 0);
}

/**
 * 重新查询表格数据
 * @returns {undefined}
 */
function searchDemoTradeOrderAsync() {
    var myData = $("#demotradeorderSearchForm").serializeArray();
    //alert(JSON.stringify(myData));
    $.ajax({
        type: "post",
        url: "querydemotradeorderpage",
        complete: function () {
            $.messager.progress('close');
        },
        data: myData,
        async: false,
        dataType: "json",
        success: function (data1) {
            $('#demotradeordertable').datagrid('loadData', data1);
            //alert(data1.length);
        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}



//添加表单
function openAddDemoTradeOrderDialog() {
    showWindow({
        title: '添加表单',
        href: 'gotodemotradeorderedit',
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//添加表单
function openUpdateDemoTradeOrderDialog() {
    var row = $('#demotradeordertable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要修改的记录！', 'info');
        return;
    }
    showWindow({
        title: '添加表单',
        href: 'gotodemotradeorderedit?id=' + row.id,
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

//提交表单数据
function submitDemoTradeOrderInfo() {
    //alert($('#demotradeorderform').form('validate'));
    if ($('#demotradeorderform').form('validate') == false) {
        return;
    }
    var pjData = $("#demotradeorderform").serializeArray();

    //alert(JSON.stringify(pjData));
    $.ajax({
        type: "post",
        url: "adddemotradeorder",
        dataType: "json",
        data: pjData,
        async: false,
        success: function (data1) {
            if (data1 && data1.id) {
                $('#demotradeordertable').datagrid('load', {

                });
                //关闭新增/修改窗口
                closeWindow();
                //刷新查询列表
                searchDemoTradeOrder();
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
function deleteDemoTradeOrder() {
    var row = $('#demotradeordertable').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要删除的记录！', 'info');
        return;
    }
    $.messager.confirm('?', '您确定要删除本商品订单吗?', function (r) {
        if (r) {
            $.ajax({
                type: "post",
                url: "deletedemotradeorder",
                data: {
                    id: row.id
                },
                async: false,
                success: function (data1) {
                    if (data1 == true) {
                        //$.messager.alert('提示','id为['+row.id+ ']的商品订单删除成功！', 'info');
                        searchDemoTradeOrder()
                    } else {
                        $.messager.alert('提示', '商品订单删除失败:' + data1, 'info');
                    }
                },
                error: function (data, type) {
                    $.messager.alert('提示', '商品订单删除失败:' + data + type, 'info');
                }
            });
        }
    });
}

//******************************************************
//                    子表列表区域 
//******************************************************
