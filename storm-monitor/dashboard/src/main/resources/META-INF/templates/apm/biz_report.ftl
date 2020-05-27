<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>图表配置</title>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script type="text/javascript" src="../js/apm/bizreport.js"></script>
        <script type="text/javascript" src="../js/echarts.min.js"></script>
    </head>

    <body class="easyui-layout" data-options="region:'center'">

        <div data-options="region:'north',border:false,split:true" style="height:20px">
            组合图表
        </div>
        <div data-options="region:'center',split:true,border:false">
            <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
            <#list reports as report>
                <div id="report_${report.id}" style="height:400px">${report.name}</div>
            </#list>
        </div>
        <script type="text/javascript">

                        // 基于准备好的dom，初始化echarts图表
            <#list reports as report>
                        //${report.type}
                <#if report.type=='hour_dynamic'>
                        //*********************************************
                        //*           动态报表(小时报表)              *
                        //*********************************************
                        var myChart_${report.id} = echarts.init(document.getElementById('report_${report.id}'));
                        option_${report.id} = {
                            title: {
                                text: '${report.mainTitle}',
                                subtext: '${report.subTitle}'
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data: ['最新成交价', '预购队列']
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            dataZoom: {
                                show: false,
                                start: 0,
                                end: 100
                            },
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: true,
                                    data: (function () {
                                        var now = new Date();
                                        var res = [];
                                        var len = 10;
                                        while (len--) {
                                            res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
                                            now = new Date(now - 2000);
                                        }
                                        return res;
                                    })()
                                },
                                {
                                    type: 'category',
                                    boundaryGap: true,
                                    data: (function () {
                                        var res = [];
                                        var len = 10;
                                        while (len--) {
                                            res.push(len + 1);
                                        }
                                        return res;
                                    })()
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    scale: true,
                                    name: '价格',
                                    boundaryGap: [0.2, 0.2]
                                },
                                {
                                    type: 'value',
                                    scale: true,
                                    name: '预购量',
                                    boundaryGap: [0.2, 0.2]
                                }
                            ],
                            series: [
                                {
                                    name: '预购队列',
                                    type: 'bar',
                                    xAxisIndex: 1,
                                    yAxisIndex: 1,
                                    data: (function () {
                                        var res = [];
                                        var len = 10;
                                        while (len--) {
                                            res.push(Math.round(Math.random() * 1000));
                                        }
                                        return res;
                                    })()
                                },
                                {
                                    name: '最新成交价',
                                    type: 'line',
                                    data: (function () {
                                        var res = [];
                                        var len = 10;
                                        while (len--) {
                                            res.push((Math.random() * 10 + 5).toFixed(1) - 0);
                                        }
                                        return res;
                                    })()
                                }
                            ]
                        };
                        var lastData = 11;
                        var axisData;

                        timeTicket = setInterval(function () {
                            lastData += Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1);
                            lastData = lastData.toFixed(1) - 0;
                            axisData = (new Date()).toLocaleTimeString().replace(/^\D*/, '');

                            // 动态数据接口 addData
                            myChart_${report.id}.addData([
                                [
                                    0, // 系列索引
                                    Math.round(Math.random() * 1000), // 新增数据
                                    false, // 新增数据是否从队列头部插入
                                    true, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                                    axisData
                                ],
                                [
                                    1, // 系列索引
                                    lastData, // 新增数据
                                    false, // 新增数据是否从队列头部插入
                                    true, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                                    axisData  // 坐标轴标签
                                ]
                            ]);
                        }, 2100);
                        //clearInterval(timeTicket);

                        // 为echarts对象加载数据 
                        myChart_${report.id}.setOption(option_${report.id});
                </#if>
                <#if report.type=='day_static' || report.type=='hour_static'>
                        //*********************************************
                        //*           静态报表(天报表)                *
                        //*********************************************
                        var myChart_${report.id} = echarts.init(document.getElementById('report_${report.id}'));
                        option_${report.id} = {
                            title: {
                                text: '${report.mainTitle}',
                                subtext: '${report.subTitle}'
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            legend: {
                                data: []
                            },
                            xAxis: [
                                {
                                    type: 'category',
                                    data: []
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    name: '交易次数',
                                    axisLabel: {
                                        formatter: '{value} '
                                    }
                                },
                                {
                                    type: 'value',
                                    name: '交易金额',
                                    axisLabel: {
                                        formatter: '{value} '
                                    }
                                }
                            ],
                            series: []
                        };

                        myChart_${report.id}.setOption(option_${report.id});
                        loadStaticData("${report.id}", option_${report.id}, myChart_${report.id},<#if report.type=='day_static'>"getdaydata"</#if><#if report.type=='hour_static'>"gethourdata"</#if>);
                        setInterval(function () {
                            loadStaticData("${report.id}", option_${report.id}, myChart_${report.id},<#if report.type=='day_static'>"getdaydata"</#if><#if report.type=='hour_static'>"gethourdata"</#if>);
                        }, 1000 * 20);
                </#if>
                
                
                
                <#if report.type=='hour_splashed'>
                    //************************************************
                // *          散点图
                //************************************************
                var myChart_${report.id} = echarts.init(document.getElementById('report_${report.id}'));
                        option_${report.id} ={
    title : {
        text: '${report.mainTitle}',
        subtext: '${report.subTitle}'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer:{
            show: true,
            type : 'cross',
            lineStyle: {
                type : 'dashed',
                width : 1
            }
        }
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    dataZoom: {
        show: false,
        start : 0,
        end : 100
    },
    legend : {
        data : []
    },
    xAxis : [
        {
            type : 'category',
            data : []
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    animation: false,
    series : []
};

myChart_${report.id}.setOption(option_${report.id});
loadSplashedData("${report.id}", option_${report.id}, myChart_${report.id});
setInterval(function () {
    loadSplashedData("${report.id}", option_${report.id}, myChart_${report.id});
}, 1000 * 20);                    
                            
                </#if>


            </#list>
                
                
                
                
                
                
                
                
                
                
                
                
                
                

        </script>
    </body>



</html>