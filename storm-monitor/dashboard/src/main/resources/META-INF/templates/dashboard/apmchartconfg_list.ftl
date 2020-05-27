<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>图表配置</title>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script type="text/javascript" src="../js/dashboard/apmchartconfg.js"></script>
    </head>
    <body style="padding: 10px">
        <div id="MyPopWindow" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>
        <h2>图表配置管理</h2>
        <p>管理图表配置的添加、修改、删除.</p>
        <div class="easyui-panel searchdiv" style="padding:5px;width:99%">
            <form id="apmchartconfgSearchForm" method="post">
                <input type="hidden" name="page.pageNo" value="1" />
                <input type="hidden" name="page.length" value="10" />
                <input type="hidden" name="page.sortColumn" value="" />
                <input type="hidden" name="page.orderBy" value="" />
                <table style="width: 90%">
                    <tr>
                        <td align="left">
                            <input class="easyui-textbox" name="chartName"  data-options="label:'图表名称:',width:300">
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="description"  data-options="label:'图表说明:',width:300">
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="chartContent"  data-options="label:'图表内容:',width:300">
                        </td>
                    </tr>
                    <tr>
                        <td align="left">
                            创建时间:<input name="createTimeBegin" class="easyui-datetimebox" style="width:150px;">起---<input name="createTimeEnd" class="easyui-datetimebox" style="width:150px;">止
                        </td>
                        <td align="left">
                            修改时间:<input name="modifyTimeBegin" class="easyui-datetimebox" style="width:150px;">起---<input name="modifyTimeEnd" class="easyui-datetimebox" style="width:150px;">止
                        </td>
                        <td style="width: 80px">
                            <a href="javascript:searchApmChartConfg()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false">查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <!--工具栏-->
        <div  style="margin:20px 0;">
            <a href="javascript:openAddApmChartConfgDialog()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:false">新建</a>
            <a href="javascript:openUpdateApmChartConfgDialog()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:false">修改</a>
            <a href="javascript:deleteApmChartConfg()" class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:false">删除</a>
        </div>
        <!--主表-->
        <table id="apmchartconfgtable" class="easyui-datagrid" style="width:99%;height:450px"
               data-options="onDblClickRow:onDblClickRowByDG,fitColumns:true,idField: 'id',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,pagination:true,onLoadSuccess:onLoadSuccess4ApmChartConfg,onSortColumn:onSortColumn4ApmChartConfg,onClickRow:onClickRow4ApmChartConfg,title:'图表配置'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:120,sortable:true">ID</th>
                    <th data-options="field:'chartName',width:120,sortable:true">图表名称</th>
                    <th data-options="field:'description',width:120,sortable:true">图表说明</th>
                    <th data-options="field:'chartContent',width:120,sortable:true">图表内容</th>
                    <th data-options="field:'createTime',width:120,formatter:formateDate2Str,sortable:true">创建时间</th>
                    <th data-options="field:'modifyTime',width:120,formatter:formateDate2Str,sortable:true">修改时间</th>
                </tr>
            </thead>
        </table>

        <!-- 子表列表区域 -->
    </body>



</html>