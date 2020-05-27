<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>添加表单</title>
    </head>
    <body>
        <div style="padding:2px;border:1px solid #ddd">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" title='保存' onclick="submitApmChartConfgInfo()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" title='保存' onclick="closeWindow()">取消</a>
        </div>
        <form id="apmchartconfgform" method="post">
                            <input type="hidden" name="id" value="${pj.id!}" />
                                    <table width="98%">
                                                                                <tr>
                            <td width="25%" align="right">
                                图表名称: &nbsp;
                            </td>
                            <td width="75%">
                                                                                                                                                       <input class="easyui-textbox" data-options="validType:['length[0,200]'] ,required:true " name="chartName" value="${pj.chartName!}" size="90"></input>
                                                                                                            
                            </td>
                        </tr>
                                                                                                    <tr>
                            <td width="25%" align="right">
                                图表说明: &nbsp;
                            </td>
                            <td width="75%">
                                                                                                                                                       <input class="easyui-textbox" data-options="validType:['length[0,400]'] ,required:true " name="description" value="${pj.description!}" size="90"></input>
                                                                                                            
                            </td>
                        </tr>
                                                                                                    <tr>
                            <td width="25%" align="right">
                                图表内容: &nbsp;
                            </td>
                            <td width="75%">
                                                                                                                                                       <textarea name="chartContent"  style="height:60px;width:600px">${pj.chartContent!}</textarea>
                                                                                                            
                            </td>
                        </tr>
                                                                                                    <tr>
                            <td width="25%" align="right">
                                创建时间: &nbsp;
                            </td>
                            <td width="75%">
                                                                    <input name="createTime" class="easyui-datetimebox" value="${pj.createTime!}" style="width:150px;">
                                
                            </td>
                        </tr>
                                                                                                    <tr>
                            <td width="25%" align="right">
                                修改时间: &nbsp;
                            </td>
                            <td width="75%">
                                                                    <input name="modifyTime" class="easyui-datetimebox" value="${pj.modifyTime!}" style="width:150px;">
                                
                            </td>
                        </tr>
                                                    <tr>
                </tr>
            </table>
        </form>
    </body>
</html>