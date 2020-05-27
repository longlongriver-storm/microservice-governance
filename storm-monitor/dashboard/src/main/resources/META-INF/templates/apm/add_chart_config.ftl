<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>添加图表</title>
    </head>
    <body>
        <div style="padding:2px;border:1px solid #ddd">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" title='保存' onclick="submitChartCfgInfo()">保存</a>
        </div>
        <form id="chartcfgform" method="post">
            <table width="98%">
                <tr>
                    <td>ID:</td>
                    <td><input class="easyui-textbox" data-options="required:true" name="id" value="${pj.id!}" size="90"></input><span style="color:red">*</span></td>
                </tr>
                <tr>
                    <td>图表名称:</td>
                    <td><input class="easyui-textbox" data-options="required:true" name="chartName" value="${pj.chartName!}" size="90"></input><span style="color:red">*</span>
                    </td>
                </tr>
                <tr>
                    <td>图表说明:</td>
                    <td><textarea name="description"  style="height:60px;width:600px">${pj.description!}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>图表内容:</td>
                    <td><textarea name="chartContent" style="height:350px;width:600px">${pj.chartContent!}</textarea>
                    </td>
                </tr>
                <tr>
                </tr>
            </table>
        </form>
    </body>
</html>