<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>图表配置</title>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script type="text/javascript" src="../js/apm/chartconfig.js"></script>
         <style>
            body {
                font-family:verdana,helvetica,arial,sans-serif;
                padding:20px;
                font-size:12px;
                margin:0;
            }
            h2 {
                font-size:18px;
                font-weight:bold;
                margin:0;
                margin-bottom:15px;
            }
            .demo-info{
                padding:0 0 12px 0;
            }
            .demo-tip{
                display:none;
            }
            .label-top{
                display: block;
                height: 22px;
                line-height: 22px;
                vertical-align: middle;
            }
            .searchdiv{
                background-color:#E4EEFF;
            }

            
        </style>
    </head>
    
    <body style="padding: 10px">
        <div id="MyPopWindow" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>
        <h2>图表配置管理</h2>
        <p>管理图表配置的添加、修改、删除.</p>
        <div class="easyui-panel searchdiv" style="padding:5px;width:99%">
            <form id="chartcfglist" method="post">
                <input type="hidden" name="page.end" value="1000" />
                <input type="hidden" name="page.length" value="1000" />
                <input type="hidden" name="page.sortColumn" value="create_time" />
                <input type="hidden" name="page.orderBy" value="asc" />
                <table style="width: 90%">
                    <tr>
                        <td><input class="easyui-textbox" name="chartName" data-options="label:'图表名称:',width:300"><span>*</span></td>
                        <td><input class="easyui-textbox" name="description"  data-options="label:'图表说明:',width:300"></td>

                        <td style="width: 80px">
                            <a href="javascript:search()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false">查询</a>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="6"><span id="alertMsg" style="color:red"></span></th>
                    </tr>
                </table>
            </form>
        </div>
        <!--工具栏-->
        <div  style="margin:20px 0;">
            <a href="javascript:openAddChartCfgDialog()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:false">新建图表</a>
            <a href="javascript:openUpdateChartCfgDialog()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:false">修改图表</a>
            <a href="javascript:deleteChartCfg()" class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:false">删除图表</a>
        </div>
        <table id="m_buyerror" class="easyui-datagrid" style="width:99%"
               data-options="onDblClickRow:onDblClickRowByDG,fitColumns:true,idField: 'id',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'图表配置'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:60">ID</th>
                    <th data-options="field:'chartName',width:120">图表名称</th>
                    <th data-options="field:'description',width:200">图表说明</th>
                    <th data-options="field:'createTime',width:80,formatter:formateDate2Str">创建时间</th>
                    <th data-options="field:'modifyTime',width:60,formatter:formateDate2Str">修改时间</th>
                </tr>
            </thead>
        </table>
    </body>



</html>