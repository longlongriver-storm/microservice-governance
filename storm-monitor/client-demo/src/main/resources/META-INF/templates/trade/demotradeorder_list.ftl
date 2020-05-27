<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>商品订单</title>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script type="text/javascript" src="../js/trade/demotradeorder.js"></script>
    </head>
    <body style="padding: 10px">
        <div id="MyPopWindow" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>
        <h2>商品订单管理</h2>
        <p>管理商品订单的添加、修改、删除.</p>
        <div class="easyui-panel searchdiv" style="padding:5px;width:99%">
            <form id="demotradeorderSearchForm" method="post">
                <input type="hidden" name="page.pageNo" value="1" />
                <input type="hidden" name="page.length" value="10" />
                <input type="hidden" name="page.sortColumn" value="" />
                <input type="hidden" name="page.orderBy" value="" />
                <table style="width: 90%">
                    <tr>
                        <td align="left">
                            <select class="easyui-combobox" name="skuName" 
                                    data-options="editable:false,label:'商品名称:',width:250">
                                <option value="">全部</option>
                                <option value="电视">电视</option>
                                <option value="电脑">电脑</option>
                                <option value="洗衣机">洗衣机</option>
                            </select>
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="customerName"  data-options="label:'下单客户:',width:250">
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="deliveryPlace"  data-options="label:'交货地点:',width:250">
                        </td>
                        <td style="width: 80px">
                            <a href="javascript:searchDemoTradeOrder()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false">查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <!--工具栏-->
        <div  style="margin:20px 0;">
            <a href="javascript:openAddDemoTradeOrderDialog()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:false">新建</a>
            <a href="javascript:openUpdateDemoTradeOrderDialog()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:false">修改</a>
            <a href="javascript:deleteDemoTradeOrder()" class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:false">删除</a>
        </div>
        <!--主表-->
        <table id="demotradeordertable" class="easyui-datagrid" style="width:99%;height:350px"
               data-options="onDblClickRow:onDblClickRowByDG,fitColumns:true,idField: 'id',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,pagination:true,onLoadSuccess:onLoadSuccess4DemoTradeOrder,onSortColumn:onSortColumn4DemoTradeOrder,onClickRow:onClickRow4DemoTradeOrder,title:'商品订单'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:120,sortable:true">订单ID</th>
                    <th data-options="field:'skuName',width:120,sortable:true">商品名称</th>
                    <th data-options="field:'skuPrice',width:120,sortable:true">商品单价</th>
                    <th data-options="field:'orderQuantity',width:120,sortable:true">下单数量</th>
                    <th data-options="field:'customerName',width:120,sortable:true">下单客户</th>
                    <th data-options="field:'deliveryDate',width:120,formatter:formateDate2Str,sortable:true">交货日期</th>
                    <th data-options="field:'deliveryPlace',width:120,sortable:true">交货地点</th>
                    <th data-options="field:'orderDesc',width:140,sortable:true">备注</th>
                </tr>
            </thead>
        </table>

        <!-- 子表列表区域 -->
    </body>



</html>