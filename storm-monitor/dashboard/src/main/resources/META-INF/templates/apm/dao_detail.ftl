<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>图表配置</title>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script type="text/javascript" src="../js/apm/maindetail.js"></script>
    </head>

    <body class="easyui-layout" data-options="region:'center'">
        <div data-options="region:'north',border:false,split:true" style="height:40px">
            <a href="#" onclick="javascript:changeShowMode(0)" class="easyui-linkbutton" data-options="toggle:true,group:'g1',selected:true">分钟监控模式</a>
            <a href="#" onclick="javascript:changeShowMode(1)" class="easyui-linkbutton" data-options="toggle:true,group:'g1'">小时监控模式</a>
            <a href="#" onclick="javascript:changeShowMode(2)" class="easyui-linkbutton" data-options="toggle:true,group:'g1'">天监控模式</a>
            <span id="currentDateTime" style="width: 400px;align:right;color: red">当前时间:</span>
        </div>
        <div data-options="region:'center',split:true,border:false">
            <div class="easyui-layout" data-options="fit:true">
                <div id="menu_toolbar" data-options="region:'west',split:true,tools:'#tt12'" title="最近:::小时:::监控" style="width:50%;padding:1px">
                    <div id="tt12">
                        <a href="javascript:void(0)" class="icon-help" onclick="javascript:alert('多用用就知道了')"></a>
                    </div>

                    <!-- ************************************************ -->
                    <!-- ****************      DAO相关      ************** -->
                    <!-- ************************************************ -->
                    <div style="height: 230px;">
                        <table id="dao_error_top5" class="easyui-datagrid"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'【DAO】错误最多'">
                            <thead>
                                <tr>
                                    <th data-options="field:'serviceName',width:200,formatter:shortServiceName">DAO</th>
                                    <th data-options="field:'successCount',width:40">成功量</th>
                                    <th data-options="field:'failureCount',width:40,styler:AttentionCellStyler">失败量</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div style="height: 230px;">
                        <table id="dao_performance_top5" class="easyui-datagrid"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'【DAO】性能最差'">
                            <thead>
                                <tr>
                                    <th data-options="field:'serviceName',width:200,formatter:shortServiceName">DAO</th>
                                    <th data-options="field:'successCount',width:40">总调用量</th>
                                    <th data-options="field:'avgElapsed',width:40">平均延时(ms)</th>
                                    <th data-options="field:'maxElapsed',width:40,styler:DelayCellStyler">最大延时(ms)</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    

                </div>

                <div data-options="region:'center'" title="　　" style="padding:1px">
                    <div style="height: 230px;">
                        <table id="dao_callmax_top5" class="easyui-datagrid"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'【DAO】调用次数最多Top20'">
                            <thead>
                                <tr>
                                    <th data-options="field:'serviceName',width:200,formatter:shortServiceName">DAO</th>
                                    <th data-options="field:'successCount',width:40">成功量</th>
                                    <th data-options="field:'failureCount',width:40">失败量</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div style="height: 230px;">
                        <table id="dao_callresourcemax_top5" class="easyui-datagrid"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'【DAO】总资源占用最多Top5'">
                            <thead>
                                <tr>
                                    <th data-options="field:'serviceName',width:200,formatter:shortServiceName">DAO</th>
                                    <th data-options="field:'successCount',width:40">成功量</th>
                                    <th data-options="field:'avgElapsed',width:40,styler:DelayCellStyler">平均延时(ms)</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    
                    
                </div>
            </div>
        </div>

    </body>

    <script>
        $(document).ready(function () {
            currentTableNamePrefix = "dao";
            reflashLeftDatagridData();
            getNewestDataTime();
        });
        
        setInterval(function () {
            reflashLeftDatagridData();
            getNewestDataTime();
        }, 1000 * 60);
    </script>



</html>