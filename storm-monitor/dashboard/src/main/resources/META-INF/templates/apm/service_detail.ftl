<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>图表配置</title>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script type="text/javascript" src="../js/apm/maindetail.js"></script>
        <script type="text/javascript" src="../js/echarts.min.js"></script>
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
                <div id="menu_toolbar" data-options="region:'west',split:true,tools:'#tt12'" title="最近:::小时:::监控" style="width:650px;">
                    <div id="tt12">
                        <a href="javascript:void(0)" class="icon-help" onclick="javascript:alert('多用用就知道了')"></a>
                    </div>
                    <!-- ************************************************ -->
                    <!-- ****************    API服务相关     ************** -->
                    <!-- ************************************************ -->
                    <div style="height: 230px;">
                        <table id="m_error_top5" class="easyui-datagrid" style="width:100%;height:600px"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'【服务】系统错误最多'">
                            <thead>
                                <tr>
                                    <th data-options="field:'serviceName',width:200,formatter:shortServiceName">接口</th>
                                    <th data-options="field:'successCount',width:40">成功量</th>
                                    <th data-options="field:'failureCount',width:40,styler:AttentionCellStyler">失败量</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div style="height: 230px;">
                        <table id="m_biz_error_top5" class="easyui-datagrid" style="width:100%;height:600px"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'【服务】业务错误最多'">
                            <thead>
                                <tr>
                                    <th data-options="field:'serviceName',width:200,formatter:shortServiceName">接口</th>
                                    <th data-options="field:'successCount',width:40">业务调用量</th>
                                    <th data-options="field:'bizFailureCount',width:40,styler:AttentionCellStyler">业务失败量</th>
                                    <th data-options="field:'lastestBizErrorMsg',width:100">最新异常信息</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div style="height: 230px;">
                        <table id="m_performance_top5" class="easyui-datagrid"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'【服务】性能最差Top20'">
                            <thead>
                                <tr>
                                    <th data-options="field:'serviceName',width:200,formatter:shortServiceName">接口</th>
                                    <th data-options="field:'successCount',width:40">总调用量</th>
                                    <th data-options="field:'avgElapsed',width:40">平均延时(ms)</th>
                                    <th data-options="field:'maxElapsed',width:40,styler:DelayCellStyler">最大延时(ms)</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div style="height: 230px;">
                        <table id="m_callmax_top5" class="easyui-datagrid"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'【服务】调用次数最多Top20'">
                            <thead>
                                <tr>
                                    <th data-options="field:'serviceName',width:200,formatter:shortServiceName">接口</th>
                                    <th data-options="field:'successCount',width:40">成功量</th>
                                    <th data-options="field:'failureCount',width:40">失败量</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div style="height: 230px;">
                        <table id="m_callresourcemax_top5" class="easyui-datagrid"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,method:'post',selectOnCheck:true,title:'【服务】总资源占用最多Top5'">
                            <thead>
                                <tr>
                                    <th data-options="field:'serviceName',width:200,formatter:shortServiceName">接口</th>
                                    <th data-options="field:'successCount',width:40">成功量</th>
                                    <th data-options="field:'avgElapsed',width:40,styler:DelayCellStyler">平均延时(ms)</th>
                                </tr>
                            </thead>
                        </table>
                    </div>



                </div>

                <div data-options="region:'center',split:true" style="padding:1px">
                    <div class="easyui-layout" data-options="fit:true">
                        <div data-options="region:'west',split:true" title="变化幅度监控" style="width:650px;">
                            <!-- ************************************************ -->
                            <!-- ****************      变化量      ************** -->
                            <!-- ************************************************ -->
                            <div style="height: 230px;">
                                <table id="h_callmax_top5" class="easyui-datagrid"
                                       data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,url:'getnewesthourcallchangemax',method:'post',selectOnCheck:true,title:'【服务】每小时调用次数变化最多Top5'">
                                    <thead>
                                        <tr>
                                            <th data-options="field:'serviceName',width:200,formatter:shortServiceName">接口</th>
                                            <th data-options="field:'successCount',width:40">现值</th>
                                            <th data-options="field:'failureCount',width:40">旧值</th>
                                            <th data-options="field:'maxElapsed',width:30,styler:AttentionCellStyler">变化率</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                            <div style="height: 230px;">
                                <table id="d_callmax_top5" class="easyui-datagrid"
                                       data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,url:'getnewesthourcallchangemax?idao=service_monitor_log_day',method:'post',selectOnCheck:true,title:'【服务】每天调用次数变化最多Top5'">
                                    <thead>
                                        <tr>
                                            <th data-options="field:'serviceName',width:200,formatter:shortServiceName">接口</th>
                                            <th data-options="field:'successCount',width:40">现值</th>
                                            <th data-options="field:'failureCount',width:40">旧值</th>
                                            <th data-options="field:'maxElapsed',width:30,styler:AttentionCellStyler">变化率</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>

                        </div>
                        <div data-options="region:'center',split:true" style="padding:1px">
                            <!-- 高危用户列表 -->
                            <div style="height: 260px;">
                                <table id="m_dangeroususers" class="easyui-datagrid"
                                       data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,url:'getNewestDangerousUsers',method:'post',selectOnCheck:true,title:'最新高危用户列表'">
                                    <thead>
                                        <tr>
                                            <th data-options="field:'user_error_count',width:20">异常数</th>
                                            <th data-options="field:'user_key',width:50">用户标识</th>
                                            <th data-options="field:'error_code',width:20">最新码</th>
                                            <th data-options="field:'error_msg',width:60">最新异常信息</th>
                                            <th data-options="field:'lastest_log_time',width:60,formatter:formateDate2Str,styler:timeColorChange">最新异常触发时间</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>

                            <!-- 24小时内核心交易故障 -->
                            <div style="height: 360px;">
                                <table id="m_buyerror24" class="easyui-datagrid"
                                       data-options="toolbar:'#tb_buyerror24',onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'trace_id',rownumbers:true,singleSelect:true,selectOnCheck:true,title:'24小时内核心交易故障'">
                                    <thead>
                                        <tr>
                                            <th data-options="field:'log_time',width:60,formatter:formateDate2Str,styler:timeColorChange">时间</th>
                                            <th data-options="field:'user_key',width:50">用户标识</th>
                                            <th data-options="field:'service_name',width:20">接口</th>
                                            <th data-options="field:'error_msg',width:60">错误信息</th>
                                            <th data-options="field:'error_code',width:20">错误码</th>
                                        </tr>
                                    </thead>
                                </table>
                                <div id="tb_buyerror24" style="height:auto">
                                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="gotoSearchErrLog()">自定义查询</a>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>

    <script>
        $(document).ready(function () {
            reflashLeftDatagridData();
            reloadBuyError24();
            getNewestDataTime();
        });

        setInterval(function () {
            reflashLeftDatagridData();
            reloadBuyError24();
            getNewestDataTime();
            $('#h_callmax_top5').datagrid('reload');
            $('#d_callmax_top5').datagrid('reload');
        }, 1000 * 60);


        //刷新高危用户列表
        setInterval(function () {
            $('#m_dangeroususers').datagrid('reload');
        }, 1000 * 60 * 10);
    </script>



</html>