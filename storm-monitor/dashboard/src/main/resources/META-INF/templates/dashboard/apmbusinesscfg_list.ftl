<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>apm_business_cfg</title>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script type="text/javascript" src="../js/dashboard/apmbusinesscfg.js"></script>
    </head>
    <body style="padding: 10px">
        <div id="MyPopWindow" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>
        <h2>自定义业务数据采集管理</h2>
        <p style="color:red">定义对任意com.storm包目录及子目录下的Spring Bean接口的入参和出参的采集.</p>
        <div class="easyui-panel searchdiv" style="padding:5px;width:99%">
            <form id="apmbusinesscfgSearchForm" method="post">
                <input type="hidden" name="page.pageNo" value="1" />
                <input type="hidden" name="page.length" value="10" />
                <input type="hidden" name="page.sortColumn" value="" />
                <input type="hidden" name="page.orderBy" value="" />
                <table style="width: 95%">
                    <tr>
                        <td align="left">
                            <input class="easyui-textbox" name="businessKey"  data-options="label:'业务主键:',width:300">
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="title"  data-options="label:'标题:',width:300">
                        </td>
                        <td align="left">
                            <input class="easyui-textbox" name="classMethodName"  data-options="label:'类名方法名:',width:300">
                        </td>
                        <td style="width: 80px">
                            <a href="javascript:searchApmBusinessCfg()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false">查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <!--工具栏-->
        <div  style="margin:20px 0;">
            <a href="javascript:openAddApmBusinessCfgDialog()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:false">新建</a>
            <a href="javascript:openUpdateApmBusinessCfgDialog()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:false">修改</a>
            <a href="javascript:deleteApmBusinessCfg()" class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:false">删除</a>
        </div>
        <!--主表-->
        <table id="apmbusinesscfgtable" class="easyui-datagrid" style="width:99%;height:550px"
               data-options="onDblClickRow:onDblClickRowByDG,fitColumns:true,idField: 'id',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,pagination:true,onLoadSuccess:onLoadSuccess4ApmBusinessCfg,onSortColumn:onSortColumn4ApmBusinessCfg,onClickRow:onClickRow4ApmBusinessCfg,title:'自定义采集配置列表'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:70,sortable:true">id</th>
                    <th data-options="field:'businessKey',width:70,sortable:true">业务主键</th>
                    <th data-options="field:'title',width:120,sortable:true">标题</th>
                    <th data-options="field:'description',width:120,sortable:true">描述</th>
                    <th data-options="field:'classMethodName',width:300,sortable:true">类名方法名</th>
                    <th data-options="field:'parameters',width:120,sortable:true">输入参数</th>
                    <th data-options="field:'returnResult',width:120,sortable:true">返回参数</th>
                    <th data-options="field:'createTime',width:80,formatter:formateDate2Str,sortable:true">创建时间</th>
                    <th data-options="field:'modifyTime',width:80,formatter:formateDate2Str,sortable:true">修改时间</th>
                </tr>
            </thead>
        </table>

        <!-- 子表列表区域 -->
    </body>



</html>