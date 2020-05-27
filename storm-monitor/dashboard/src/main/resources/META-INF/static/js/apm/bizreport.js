
function loadStaticData(reportId, option, eChartObj, dataUrl) {

    $.ajax({
        type: "post",
        url: dataUrl,

        data: {
            id: reportId
        },
        async: false,
        dataType: "json",
        success: function (data1) {
            //alert(JSON.stringify(data1));
            //for(var prop in data1){
            //    alert(prop);
            //}
            option.title.subtext = data1.subTitle;
            option.series = data1.series;
            option.xAxis[0].data = data1.xAxis;
            option.legend.data = data1.legendData;
            option.yAxis[0].name = data1.yAxisFirstName;
            option.yAxis[1].name = data1.yAxisSecondName;
            option.yAxis[0].axisLabel.formatter = '{value} ' + data1.yAxisFirstUnit;
            option.yAxis[1].axisLabel.formatter = '{value} ' + data1.yAxisSecondUnit;
            eChartObj.setOption(option, true);

        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}

function loadSplashedData(reportId, option, eChartObj) {

    $.ajax({
        type: "post",
        url: "getsplasheddata",

        data: {
            id: reportId
        },
        async: false,
        dataType: "json",
        success: function (data1) {
            //alert(JSON.stringify(data1));
            //for(var prop in data1){
            //    alert(prop);
            //}
            option.title.subtext = data1.subTitle;
            option.legend.data = data1.legendData;
            option.series = data1.series;
            option.xAxis[0].data = data1.xAxis;
            option.legend.data = data1.legendData;
            for (var i = 0; i < option.series.length; i++) {
                option.series[i].tooltip = {
                    trigger: 'item',
                    formatter: function (params) {
                        return params.seriesName + '<br/>'
                                + params.value[2];
                    },
                    axisPointer: {
                        show: true
                    }
                };
                option.series[i].symbolSize = function (value) {
                    return 6;
                };
                option.series[i].markPoint={
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            };
            option.series[i].markLine={
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
            }
            eChartObj.setOption(option, true);
        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}
