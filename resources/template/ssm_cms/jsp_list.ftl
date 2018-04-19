<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.List"%>
<%@ page import="${project}.pojo.${alias}.${class_name}" %>

<%
    String path = (String) request.getContextPath();
    List<${class_name}> ${instance}List = (List<${class_name}>) request.getAttribute("list${class_name}");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>XX标题</title>
    <link rel="stylesheet" href="<%=path%>/css/style.css" type="text/css">
    <link rel="stylesheet" href="<%=path%>/css/common/common.css">
    <link rel="stylesheet" href="<%=path%>/css/button.css">
    <style>

    </style>
</head>

<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td height="30">
           <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" bgcolor="#353c44">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="30" bgcolor="#353c44">
		                            <div class="data-top">
		                                <div class="top-left">
		                                    <span><img src="<%=path%>/images/tb.gif" /></span>
		                                    <span class="text">XX标题</span>
		                                </div>
		                                <div class="top-right" style="width:60px"> 
                                            <a href="<%=path %>/${instance}/modify.htm"><img src="<%=path%>/images/add.gif" /> <span class="btn">添加</span></a>&nbsp;
		                                </div>
		                            </div>
		                        </td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
        </td>
    </tr>
    <tr>
        <td>
            <form id="queryForm" method="get" action="<%=path%>/${instance}/list${class_name}/1.htm">
                <div class="data-search">
                    <div class="search-left">
                        ID:<input type="text" name="id" value="${r'${page.parmMap.id}'}" class="input-smallest">
                    </div>
                    <div class="search-left">
                        <input type="hidden" name="pageCount" value="${r'${page.rowCount}'}"  />
                        <a href="javascript:;" onclick="$('#queryForm').submit();">
                            <img src="<%=path%>/images/bt_cx.gif" border="0"/>
                        </a>
                    </div>
                </div>
            </form>
        </td>
    </tr>
    <tr>
        <td>
            <table cellpadding="3" class="data-grid" data-empty-msg="没有找到数据！">
                <thead>
                <tr>
                    <th>编号</th>
                <#list table_column as c>
                    <th>${c.remarkDict.title}</th>
                </#list>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
                <% if (${instance}List != null && ${instance}List.size() > 0) { %>
                    <%
                        int index = 0;
                        for (${class_name} data : ${instance}List) { %>
                    <tr>
                        <td><%=++index%></td>
                <#list table_column as c>
                    <#if (c.remarkDict.size<=1)>
                        <td><%=data.get${c.nameJ?cap_first}()%></td>
                    </#if>
                    <#if (c.remarkDict.size>1 && c.type == "int")>
                        <td><%=data.get${c.nameJ?cap_first}Txt()%></td>
                    </#if>
                </#list>
                        <td>
                            <a href="<%=path%>/${instance}/modify.htm?id=<%=data.getId()%>"><img src="<%=path%>/images/editStock.png" border="0" width="25" height="25" title="编辑" /></a>
                            <a class="remove" href="javascript:;" data-id="<%=data.getId()%>"><img src="<%=path%>/images/deleteInfom.png" border="0" width="25" height="25" title="删除" /></a>
                        </td>
                    </tr>
                    <% } %>
                <% } %>
                </tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td height="30">
            <%@include file="../include/page.jsp"%>
        </td>
    </tr>
</table>
</body>

<script type="text/javascript" src="<%=path%>/frame/js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/frame/js/Validator/Validator.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonUtil.js"></script>
<script type="text/javascript" src="<%=path%>/js/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/js/layer/extend/layer.ext.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    // 删除按钮
    $(".remove").click(function () {
        var id = $(this).attr("data-id");
        layer.confirm("确认是否删除记录？", function(index){
            $.get('<%=path%>/${instance}/del/'+id+'.htm', {}, function(data) {
                if (data.code == 0) {
                    layer.msg("操作成功!");
                    window.location.reload();
                } else {
                    layer.alert("操作失败!原因:"+data.msg);
                }
                layer.close(index);
            }, 'json');
        })
    });
});
</script>
</html>


