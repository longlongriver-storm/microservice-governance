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
            <div id="m_system_title" style="height:20px;color:red;align:center"></div>
        </div>
        <div data-options="region:'center',split:true,border:false">
            <div class="easyui-layout" data-options="fit:true">
                <div id="menu_toolbar" data-options="region:'west',split:true,tools:'#tt12'"  style="width:500px;">
                    <div id="tt12">
                        <a href="javascript:void(0)" class="icon-help" onclick="javascript:alert('多用用就知道了')"></a>
                    </div>
                    <div style="height: 400px;">
                        <table id="m_gc" class="easyui-datagrid"
                               data-options="onDblClickRow:onDblClickRowByDG,striped:true,scrollbarSize:5,fit:true,fitColumns:true,idField: 'serviceName',rownumbers:true,singleSelect:true,url:'getnewestgcinfo',method:'post',selectOnCheck:true,title:'（JVM启动后）垃圾收集统计'">
                            <thead>
                                <tr>
                                    <th data-options="field:'machineAddress',width:90">服务器IP</th>
                                    <th data-options="field:'gcName',width:130">收集器</th>
                                    <th data-options="field:'gcCount',width:40">次数</th>
                                    <th data-options="field:'gcTime',width:60">总耗时(ms)</th>
                                </tr>
                            </thead>
                        </table>
                    </div>


                </div>

                <div data-options="region:'center',split:true" style="padding:1px">
                    <div class="easyui-layout" data-options="fit:true">
                        <div data-options="region:'west',split:true" title="主机监控" style="width:700px;">
                            
                            <!-- 1小时内存变化图 -->
                            <div id="h_system_jvm_change" style="height:350px;margin-top: 10px"></div>
                            <!-- 1小时系统负载变化图 -->
                            <div id="h_system_loadavg" style="height:350px;margin-top:10px"></div>
                        </div>
                        <div data-options="region:'center',split:true" style="padding:1px">
                            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                            <div id="m_system_thread_detail" style="height:240px;"></div>
                            <div id="m_system_storage_detail" style="height:240px;"></div>
                            <div id="m_system_memory_detail" style="height:230px;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>

    <script>
        $(document).ready(function () {
            loadHourJvmChange();
            loadSystemMinuteDetail();
        });


        setInterval(function () {
            loadHourJvmChange();
            loadSystemMinuteDetail();
        }, 1000 * 60);

    </script>



</html>