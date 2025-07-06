<%@ page language="java" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="m" uri="http://iplass.org/tags/mtp"%>
<%@ page import="org.iplass.mtp.entity.GenericEntity"%>
<%@ page import="org.iplass.mtp.entity.Entity"%>
<%@ page import="org.iplass.mtp.entity.SearchResult"%>
<%@ page import="org.iplass.mtp.command.RequestContext"%>
<%@ page import="org.iplass.mtp.web.template.TemplateUtil"%>
<%
  RequestContext context = TemplateUtil.getRequestContext();
  @SuppressWarnings("unchecked")
  SearchResult<Entity> categories = (SearchResult<Entity>) context.getAttribute("categories");
%>
<html>
<head>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
function button_onclick(action) {
    const $form = $("#bulkForm");
    $form.attr("action", "${m:tcPath()}/" + action).submit();
}
function button_onclickAsync(webapi) { 
    let data = "{";
    data += "\"productName\":" + JSON.stringify($("[name='productName']").map(function() {return $(this).val()}).toArray());
    data += ",\"productCategory\":" + JSON.stringify($("[name='productCategory']").map(function() {return $(this).val()}).toArray());
    data += ",\"productPrice\":" + JSON.stringify($("[name='productPrice']").map(function() {return $(this).val()}).toArray());
    data += "}";
    $.ajax({
        url: "${m:tcPath()}/api/" + webapi,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: data
    }).done((data, textStatus, jqXHR) => {
        if (data.exceptionType != null) {
            alert("エラーが発生しました。"+ data.exceptionType +"\\n"+data.exceptionMessage);
            return;
        }
        $("#bulkForm")[0].reset();
    }).fail((jqXHR, textStatus, errorThrown) => {
        console.log('fail', jqXHR.status);
    });
}
</script>
</head>
<body>
<h2>商品一括登録</h2>
<form id="bulkForm" method="post" action="">
<table>
  <thead>
    <tr>
    <th>商品名</th><th>カテゴリ</th><th>価格</th>
    </tr>
  </thead>
  <tbody>
  <% for (int i = 0; i < 10; i++) { %>
    <tr>
    <td><input type="text" name="productName" /></td>
    <td>
    <select name="productCategory">
      <% for (Entity e : categories) { %>
      <option value="<c:out value="<%=e.getOid() %>"/>" ><c:out value="<%=e.getName() %>"/></option>
      <%}%>
    </select>
    </td>
    <td><input type="text" name="productPrice" /></td>
    </tr>
    <%}%>
  </tbody>
</table>
<input type="button" value="一括登録" onclick="button_onclick('tutorial/product/java/insertProduct')" />
<input type="button" value="一括登録(非同期)" onclick="button_onclickAsync('tutorial/product/java/insertProduct')" /> 
</form>
</body>
</html>