<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>异常信息汇总表</title>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script type="text/javascript" src="../js/dashboard/errorlog.js"></script>
    </head>
    <body style="padding: 10px">
        <div id="MyPopWindow" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>
        <h2>异常信息汇总表管理</h2>
        <p>管理异常信息汇总表的添加、修改、删除.</p>
        <div class="easyui-panel searchdiv" style="padding:5px;width:99%">
            <form id="errorlogSearchForm" method="post">
                <input type="hidden" name="page.pageNo" value="1" />
                <input type="hidden" name="page.length" value="10" />
                <input type="hidden" name="page.sortColumn" value="" />
                <input type="hidden" name="page.orderBy" value="" />
                <table style="width: 95%">
                    <tr>
                        <td align="left">
                            日志时间:<input name="logTimeBegin" class="easyui-datetimebox" style="width:150px;">起
                            ---
                            <input name="logTimeEnd" class="easyui-datetimebox" style="width:150px;">止
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="machineAddress"  data-options="label:'主机地址:',width:300">
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="serviceName"  data-options="label:'服务名称:',width:300">
                        </td>
                        <td>
                        </td>
                    </tr>
                    <tr>
                        <td align="left">
                            <input class="easyui-textbox" name="traceId"  data-options="label:'跟踪ID:',width:300">
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="userKey"  data-options="label:'用户标识:',width:300">
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="errorCode"  data-options="label:'错误代码:',width:300">
                        </td>
                        <td style="width: 80px">
                            <a href="javascript:searchErrorLog()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false">查询</a>
                        </td>
                    </tr>
                    <tr>
                        
                    </tr>
                </table>
            </form>
        </div>
        <!--工具栏-->
        <div  style="margin:20px 0;">
            <a href="javascript:openUpdateErrorLogDialog()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:false">查看</a>
        </div>
        <!--主表-->
        <table id="errorlogtable" class="easyui-datagrid" style="width:99%;height:450px"
               data-options="onDblClickRow:onDblClickRowByDG,fitColumns:true,idField: 'id',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,pagination:true,onLoadSuccess:onLoadSuccess4ErrorLog,onSortColumn:onSortColumn4ErrorLog,onClickRow:onClickRow4ErrorLog,title:'异常信息汇总表'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:40,sortable:true">主键</th>
                    <th data-options="field:'logTime',width:110,formatter:formateDate2Str,sortable:true">日志时间</th>
                    <th data-options="field:'machineAddress',width:70,sortable:true">主机地址</th>
                    <th data-options="field:'serviceName',width:320,sortable:true">服务名称</th>
                    <th data-options="field:'errorType',width:70,formatter:formateErrorType,sortable:true">错误类型</th>
                    <th data-options="field:'traceId',width:180,sortable:true">跟踪ID</th>
                    <th data-options="field:'userKey',width:70,sortable:true">用户标识</th>
                    <th data-options="field:'errorCode',width:70,sortable:true">错误代码</th>
                    <th data-options="field:'errorMsg',width:120,sortable:true">错误信息</th>
                </tr>
            </thead>
        </table>

        <!-- 子表列表区域 -->
    </body>



</html>