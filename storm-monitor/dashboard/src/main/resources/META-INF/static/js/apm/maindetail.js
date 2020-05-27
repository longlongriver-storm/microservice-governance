/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var currentTableNamePrefix = "service";
var currentTableNameStuffix = "_monitor_log";

var outerApiCallChart = null;
var systemStorageChart = null;
var systemMemoryChart = null;
var jvmChangeChart = null;
var systemLoadChart = null;
var systemThreadDetailChart = null;
var systemStorageDetailChart = null;
var systemMemoryDetailChart = null;

function AttentionCellStyler(value, row, index) {
    if (value > 5) {
        return 'background-color:#ffee00;color:red;';
    }
    return 'color:red;';
}

function timeColorChange(value, row, index) {
    var nowTime = (new Date()).getTime();
    var oneDateInteval = 60 * 60 * 24 * 1000;
    if (nowTime - value < oneDateInteval) {
        return 'color:red;';
    }
    return '';
}

function DelayCellStyler(value, row, index) {
    if (value > 1000) {
        return 'background-color:#ffee00;color:red;';
    }
    return 'color:red;';
}

function shortServiceName(val, row) {
    var name = val.split(".");
    if (name.length > 3) {
        return "[" + name[2] + "]" + name[name.length - 2] + "." + name[name.length - 1];
    }
    return val;
}

function formateDate2Str(val, row) {
    var oDate = new Date(val);
    oYear = oDate.getFullYear();
    oMonth = oDate.getMonth() + 1;
    oDay = oDate.getDate();
    oHour = oDate.getHours();
    oMin = oDate.getMinutes();
    oSen = oDate.getSeconds();
    oTime = oYear + '-' + oMonth + '-' + oDay + ' ' + oHour + ':' + oMin + ':' + oSen;//最后拼接时间  
    return oTime;
}

/**
 * 修改显示模式
 * @param {type} mode 0:分钟，1：小时；2天
 * @return {undefined}
 */
function changeShowMode(mode) {
    if (mode == 0) {
        $("#menu_toolbar").panel("setTitle", "最近:::分钟:::监控");
        currentTableNameStuffix = "_monitor_log";
    } else if (mode == 1) {
        $("#menu_toolbar").panel("setTitle", "最近:::小时:::监控");
        currentTableNameStuffix = "_monitor_log_hour";
    } else if (mode == 2) {
        $("#menu_toolbar").panel("setTitle", "最近:::天:::监控");
        currentTableNameStuffix = "_monitor_log_day";
    }

    reflashLeftDatagridData();
    getNewestDataTime();
}

function reflashLeftDatagridTitle(title) {
    $('#m_error_top5').datagrid("getPanel").panel("setTitle", "【" + title + "】错误最多");
    $('#m_performance_top5').datagrid("getPanel").panel("setTitle", "【" + title + "】性能最差");
    $('#m_callmax_top5').datagrid("getPanel").panel("setTitle", "【" + title + "】调用次数最多");
    $('#m_callresourcemax_top5').datagrid("getPanel").panel("setTitle", "【" + title + "】总资源占用最多");
}

function onDblClickRowByDG(index, row) {
    var s = "<br><br><br>";
    for (var str in row) {
        if (str == 'logTime' && row[str] != null) {
            var unixTimestamp = new Date(row[str]);
            s += "<strong>" + str + "</strong>=<span style='color:red'>" + unixTimestamp.toLocaleString() + "</span><br>";
        } else {
            s += "<strong>" + str + "</strong>=<span style='color:red'>" + row[str] + "</span><br>";
        }
    }
    //$.messager.alert('记录明细',s,'info');
    $.messager.show({
        title: '记录明细',
        msg: s,
        showType: 'slide',
        timeout: 10000,
        width: '560px',
        height: '350px'
    });



}

function reflashDatagridData(domId, apiUrl, idaoType) {
    var domObj=document.getElementById(domId);
    if(domObj===undefined || domObj===null){
        return;
    }
    $('#' + domId).datagrid({url: apiUrl, queryParams: {
            idao: idaoType + currentTableNameStuffix
        }});
}

function reflashDatagridData4Service() {
    //API相关
    reflashDatagridData('m_error_top5', 'getnewestminuteerror', 'service');
    reflashDatagridData('m_biz_error_top5', 'getnewestminutebizerror', 'service');
    reflashDatagridData('m_performance_top5', 'getnewestminuteperformance', 'service');
    reflashDatagridData('m_callmax_top5', 'getnewestminutecallmax', 'service');
    reflashDatagridData('m_callresourcemax_top5', 'getnewestminuteloadmax', 'service');
}
function reflashDatagridData4Dao() {
    //DAO相关
    reflashDatagridData('dao_error_top5', 'getnewestminuteerror', 'dao');
    reflashDatagridData('dao_performance_top5', 'getnewestminuteperformance', 'dao');
    reflashDatagridData('dao_callmax_top5', 'getnewestminutecallmax', 'dao');
    reflashDatagridData('dao_callresourcemax_top5', 'getnewestminuteloadmax', 'dao');
}

function reflashDatagridData4GC() {
    var domObj=document.getElementById("m_gc");
    if(domObj===undefined || domObj===null){
        return;
    }
    //垃圾收集
    $('#m_gc').datagrid('load', {});
}

function reflashLeftDatagridData() {
    reflashDatagridData4Service();
    reflashDatagridData4Dao();
    reflashDatagridData4GC();
}

/**
 * 加载单主机的系统性能明细
 * @return {undefined}
 */
function loadSystemMinuteDetail() {
    $.ajax({
        type: "post",
        url: "getnewestminutesystemstatus",
        data: {
            idao: 'service_monitor_log_hour'
        },
        async: false,
        dataType: "json",
        success: function (data1) {
            var lastestTime = new Date(data1[0].logTime);
            var lastestTimeStr = lastestTime.toLocaleString();
            document.getElementById("m_system_title").innerHTML = '<strong>' + lastestTimeStr + '</strong>';
            //线程图表
            loadSystemMinuteThreadDetail(data1);
            //存储图表
            loadSystemMinuteStorageDetail(data1);
            //内存图表
            loadSystemMinuteMemoryDetail(data1);
        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}

/**
 * 单主机线程信息
 * @param {type} data1
 * @return {undefined}
 */
function loadSystemMinuteThreadDetail(data1) {
    // 基于准备好的dom，初始化echarts实例
    if (systemThreadDetailChart == null) {
        systemThreadDetailChart = echarts.init(document.getElementById('m_system_thread_detail'));
    }

    var xAxis_data = [];
    var series_peek_data = [];
    var series_active_data = [];
    var series_daemon_data = [];

    for (var i = 0; i < data1.length; i++) {
        xAxis_data.push(data1[i].machineAddress);
        series_peek_data.push(data1[i].threadPeek);
        series_active_data.push(data1[i].threadActive);
        series_daemon_data.push(data1[i].threadDaemonCount);
    }
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {// 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            top: 20,
            data: ['峰值线程总数', '活动线程数', '守护线程数']
        },
        grid: {
            left: '2%',
            right: '7%',
            bottom: '9%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xAxis_data,
                offset: 0,
                axisLabel: {
                    interval: 0,
                    rotate: -15
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '峰值线程总数',
                type: 'bar',
                data: series_peek_data
            },
            {
                name: '活动线程数',
                type: 'bar',
                data: series_active_data
            },
            {
                name: '守护线程数',
                type: 'bar',
                data: series_daemon_data
            }
        ]
    };

    option.title = {text: '线程', x: 'center', textStyle: {fontSize: 15}};
    systemThreadDetailChart.setOption(option, true);
}

/**
 * 单主机存储信息
 * @param {type} data1
 * @return {undefined}
 */
function loadSystemMinuteStorageDetail(data1) {
    // 基于准备好的dom，初始化echarts实例
    if (systemStorageDetailChart == null) {
        systemStorageDetailChart = echarts.init(document.getElementById('m_system_storage_detail'));
    }

    var xAxis_data = [];
    var series_all_data = [];
    var series_free_data = [];
    var series_usable_data = [];

    for (var i = 0; i < data1.length; i++) {
        xAxis_data.push(data1[i].machineAddress);
        series_all_data.push((data1[i].diskTotal / (1024 * 1024 * 1024)).toFixed(2));
        series_free_data.push((data1[i].diskFree / (1024 * 1024 * 1024)).toFixed(2));
        series_usable_data.push((data1[i].diskUsable / (1024 * 1024 * 1024)).toFixed(2));
    }
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {// 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            top: 20,
            data: ['总容量(G)', '未分配容量(G)', '可使用容量(G)']
        },
        grid: {
            left: '2%',
            right: '7%',
            bottom: '9%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xAxis_data,
                offset: 0,
                axisLabel: {
                    interval: 0,
                    rotate: -15
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '总容量(G)',
                type: 'bar',
                data: series_all_data
            },
            {
                name: '未分配容量(G)',
                type: 'bar',
                data: series_free_data
            },
            {
                name: '可使用容量(G)',
                type: 'bar',
                data: series_usable_data
            }
        ]
    };

    option.title = {text: '存储', x: 'center', textStyle: {fontSize: 15}};
    systemStorageDetailChart.setOption(option, true);
}

/**
 * 单主机存储信息
 * @param {type} data1
 * @return {undefined}
 */
function loadSystemMinuteMemoryDetail(data1) {
    // 基于准备好的dom，初始化echarts实例
    if (systemMemoryDetailChart == null) {
        systemMemoryDetailChart = echarts.init(document.getElementById('m_system_memory_detail'));
    }

    var xAxis_data = [];
    var series_all_data = [];
    var series_used_data = [];
    var series_jvm_all_data = [];
    var series_jvm_used_data = [];
    var series_jvm_heap_data = [];

    for (var i = 0; i < data1.length; i++) {
        xAxis_data.push(data1[i].machineAddress);
        series_all_data.push((data1[i].osTotalphysicalmemory / (1024 * 1024 * 1024)).toFixed(2));
        series_used_data.push(((data1[i].osTotalphysicalmemory - data1[i].osFreephysicalmemory) / (1024 * 1024 * 1024)).toFixed(2));
        series_jvm_all_data.push((data1[i].memoryTotal / (1024 * 1024 * 1024)).toFixed(2));
        series_jvm_used_data.push(((data1[i].memoryTotal - data1[i].memoryFree) / (1024 * 1024 * 1024)).toFixed(2));
        series_jvm_heap_data.push(((data1[i].memoryHeapusage + data1[i].memoryNonheapusage) / (1024 * 1024 * 1024)).toFixed(2));
    }
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {// 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            top: 20,
            data: ['总物理内存', '物理已用内存', 'JVM总内存', 'JVM已用内存', 'JVM堆+非堆内存']
        },
        grid: {
            left: '2%',
            right: '7%',
            bottom: '9%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xAxis_data,
                offset: 0,
                axisLabel: {
                    interval: 0,
                    rotate: -15
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '总物理内存',
                type: 'bar',
                data: series_all_data
            },
            {
                name: '物理已用内存',
                type: 'bar',
                data: series_used_data
            },
            {
                name: 'JVM总内存',
                type: 'bar',
                data: series_jvm_all_data
            },
            {
                name: 'JVM已用内存',
                type: 'bar',
                data: series_jvm_used_data
            },
            {
                name: 'JVM堆+非堆内存',
                type: 'bar',
                data: series_jvm_heap_data
            }
        ]
    };

    option.title = {text: '内存', x: 'center', textStyle: {fontSize: 15}};
    systemMemoryDetailChart.setOption(option, true);
}

/**
 * 最近一个小时，【JVM内存变化】 及 【系统负载变化】
 * @return {undefined}
 */
function loadHourJvmChange() {
    if (jvmChangeChart == null) {
        jvmChangeChart = echarts.init(document.getElementById('h_system_jvm_change'));
    }
    if (systemLoadChart == null) {
        systemLoadChart = echarts.init(document.getElementById('h_system_loadavg'));
    }
    $.ajax({
        type: "post",
        url: "getnewesthoursystemLoad",
        data: {
            idao: 'service_monitor_log_hour'
        },
        async: false,
        dataType: "json",
        success: function (data1) {
            var series_idx = -1;
            var series_data_load = [];
            var series_data_memory = [];
            var legend_data = [];
            var timex = [];   //时间轴
            for (var i = 0; i < data1.length; i++) {
                //alert(JSON.stringify(d1));
                var d1 = data1[i];
                if (legend_data.length == 0 || legend_data[legend_data.length - 1] != d1.machineAddress) {
                    legend_data.push(d1.machineAddress);
                    series_data_load.push({name: d1.machineAddress, type: 'line', data: []});     //负载
                    series_data_memory.push({name: d1.machineAddress, type: 'line', data: []});   //内存
                    series_idx++;
                }
                if (legend_data.length == 1) {
                    timex.push((new Date(d1.logTime)).getMinutes());
                }
                series_data_load[series_idx].data.push(d1.osSystemloadaverage);
                series_data_memory[series_idx].data.push(((d1.memoryTotal - d1.memoryFree) / (1024 * 1024 * 1024)).toFixed(2));
            }


            var option = {
                title: {
                    text: '1小时系统负载变化图'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    top: 20,
                    data: legend_data
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: timex
                },
                yAxis: {
                    type: 'value'
                },
                series: series_data_load
            };

            systemLoadChart.setOption(option, true);

            option.title.text = '1小时JVM使用内存变化图(单位：G)';
            option.series = series_data_memory;
            jvmChangeChart.setOption(option, true);

        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}

/**
 * 获取最新的数据时间
 * @returns {undefined}
 */
function getNewestDataTime(){
    var _sql="select * from "+currentTableNamePrefix+currentTableNameStuffix+" order by log_time desc limit 1";
    $.ajax({
        type: "post",
        url: "executeSelectSQL",
        data: {
            sql: _sql
        },
        async: false,
        dataType: "json",
        success: function (data1) {
            //alert(JSON.stringify(data1));
            if(data1 && data1.length>0){
                var timeNameStr="(分钟)";
                var timeStr=formateDate2Str(data1[0].log_time);
                if(currentTableNameStuffix === "_monitor_log"){
                    var timeStrArr=timeStr.split(":");
                    timeStr=timeStrArr[0]+":"+timeStrArr[1];
                }else if(currentTableNameStuffix === "_monitor_log_hour"){
                    timeNameStr="(小时)";
                    var timeStrArr=timeStr.split(":");
                    timeStr=timeStrArr[0]
                }else if(currentTableNameStuffix === "_monitor_log_day"){
                    timeNameStr="(天)";
                    timeStr=timeStr.split(" ")[0];
                }
                $("#currentDateTime").html("最新监控数据时间"+timeNameStr+"："+timeStr);
            }

        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}

function reloadBuyError24() {
    var _sql = "select \n" +
            "	*,REVERSE(user_key) as user_key_reverse \n" +
            "FROM \n" +
            "	error_log \n" +
            "where \n" +
            "	service_name like \n" +
            "		'com.storm.monitor.demo.service.impl.DemoTradeOrderServiceImpl%'\n" +
            "   and log_time>date_add(now(), interval -24 hour)\n" +
            "order by log_time desc limit 100";
    $.ajax({
        type: "post",
        url: "executeSelectSQL",
        data: {
            sql: _sql
        },
        async: false,
        dataType: "json",
        success: function (data1) {
            var data = {"total": 2, "rows": []};
            data.total = data1.length;
            data.rows = data1;
            $('#m_buyerror24').datagrid('loadData', data);
            $('#m_buyerror24').datagrid("getPanel").panel("setTitle", "24小时内核心交易故障(" + data1.length + ")");
        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}

function gotoSearchErrLog() {
    var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //定义弹出窗口的参数  
    if (window.screen) {
        var ah = screen.availHeight - 30;
        var aw = screen.availWidth - 10;
        fulls += ",height=" + ah;
        fulls += ",innerHeight=" + ah;
        fulls += ",width=" + aw;
        fulls += ",innerWidth=" + aw;
        fulls += ",resizable"
    } else {
        fulls += ",resizable"; // 对于不支持screen属性的浏览器，可以手工进行最大化。 manually  
    }
    window.open('errorquery', 'newwindow', fulls);
}

