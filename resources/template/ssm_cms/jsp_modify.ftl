<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="${project}.pojo.${alias}.${class_name}" %>

<%
    String path = (String) request.getContextPath();
    ${class_name} data = (${class_name}) request.getAttribute("${instance}");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
    <title>XX编辑</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="<%=path%>/css/style.css" type="text/css">
    <link rel="stylesheet" href="<%=path%>/css/common/common.css">
    <link rel="stylesheet" href="<%=path%>/css/button.css">
    <style type="text/css">

    </style>
</head>
<body>
    <form id="inputForm" method="post" name="inputForm" action="<%=path%>/${instance}/<%=data != null && data.getId() >=0 ? "update" : "add" %>.htm">
        <input type="hidden" name="id" value="<%=data == null ? 0 : data.getId() %>"/>

        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td height="30" bgcolor="#353c44">
                    <div class="data-top">
                        <div class="top-left">
                            <span><img src="<%=path%>/images/tb.gif" />XX编辑</span>
                        </div>
                        <div class="top-right"></div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <table class="data-add" cellpadding="4">
                        <#list table_column as c><#if (c_index>=1)>
                            <#if !(c.isDateType || c.isIntKVType)>
                            <tr>
                                <th>
                                    <label>${c.remark}：</label>
                                </th>
                                <td>
                                    <input type="text" name="${c.nameJ}" data-value="<%=data == null ? "${c.defaultValue}" : data.get${c.nameJ?cap_first}() %>" placeholder="${c.remark}" />
                                </td>
                            </tr>
                            </#if>
                            <#if c.isDateType>
                            <tr>
                                <th>
                                    <label>${c.remark}：</label>
                                </th>
                                <td>
                                    <input type="text" name="${c.nameJ}" data-value="<%=data == null ? "${c.defaultValue}" : data.get${c.nameJ?cap_first}Txt() %>" placeholder="${c.remark}" />
                                </td>
                            </tr>
                            </#if>
                            <#if c.isIntKVType>
                            <tr>
                                <th>
                                    <label>${c.remarkDict.title}：</label>
                                </th>
                                <td>
                                    <select name="${c.nameJ}" data-value="<%=data == null ? "${c.defaultValue}" : data.get${c.nameJ?cap_first}() %>">
                                        <option value="${c.defaultValue}">-选择${c.remarkDict.title}-</option>
                                        <#list c.remarkDict.list as d>
                                        <option value="${d.k}">${d.v}</option>
                                        </#list>
                                    </select>
                                </td>
                            </tr>
                            </#if>
                        </#if></#list>

                        <tr>
                            <th>操作：</th>
                            <td colspan="3">
                                <br/><input type="button" value="保存" style="width:80px;height:30px" onclick="save();" /><br/><br/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>

<script type="text/javascript" src="<%=path%>/js/lib/jquery-1.7.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/lib/jquery.json-2.3.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/frame/js/Validator/Validator.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonUtil.js"></script>
<script type="text/javascript" src="<%=path%>/js/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/js/layer/extend/layer.ext.js"></script>
<script>
    $(function(){
        // 初始化....

    });

    //添加保存
    function save() {
        if(Validator.Validate(document.getElementById("inputForm"),3)){
            // 防止 重复提交
            //if($("a[data-selector]").attr("data-selector") != 'on')  return;
            //$("a[data-selector]").attr("data-selector", 'off');
            $("#inputForm").submit();
        }
    }
</script>
</html>