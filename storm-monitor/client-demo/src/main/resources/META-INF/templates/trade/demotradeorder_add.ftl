<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>添加表单</title>
    </head>
    <body>
        <div style="padding:2px;border:1px solid #ddd">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" title='保存' onclick="submitDemoTradeOrderInfo()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" title='保存' onclick="closeWindow()">取消</a>
        </div>
        <form id="demotradeorderform" method="post">
            <input type="hidden" name="id" value="${pj.id!}" />
            <table width="98%">
                <tr>
                    <td width="25%" align="right">
                        商品名称: &nbsp;
                    </td>
                    <td width="75%">
                        <select class="easyui-combobox" name="skuName" 
                                data-options="editable:false"
                                style="width: 200px">
                            <option value="电视" <#if (pj.skuName??) && pj.skuName=='电视'>selected</#if> >电视</option>
                            <option value="电脑" <#if (pj.skuName??) && pj.skuName=='电脑'>selected</#if> >电脑</option>
                            <option value="洗衣机" <#if (pj.skuName??) && pj.skuName=='洗衣机'>selected</#if> >洗衣机</option>
			</select>
                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        商品单价: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox"    data-options="required:true"  name="skuPrice" value="${pj.skuPrice!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        下单数量: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox"    data-options="required:true"  name="orderQuantity" value="${pj.orderQuantity!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        下单客户: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox" data-options="validType:['length[0,100]'] ,required:true " name="customerName" value="${pj.customerName!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        交货日期: &nbsp;
                    </td>
                    <td width="75%">
                        <input name="deliveryDate" class="easyui-datetimebox" value="${pj.deliveryDate!}" style="width:150px;">

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        交货地点: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox" data-options="validType:['length[0,200]'] ,required:true " name="deliveryPlace" value="${pj.deliveryPlace!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        备注: &nbsp;
                    </td>
                    <td width="75%">
                        <textarea name="orderDesc"  style="height:60px;width:600px">${pj.orderDesc!}</textarea>

                    </td>
                </tr>
                <tr>
                </tr>
            </table>
        </form>
    </body>
</html>