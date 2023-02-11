<%@page import="model.Dataset"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
Dataset data = (Dataset)request.getAttribute("inputData");

%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta charset="UTF-8">
<title>Laziest is the best.</title>
</head>
<body>
<h1>Welcome to the Laziest Data Entry Website!</h1>
<form action="/lazy-word-collector/LazyClass" method="post">
<p>
                    <label>KW</label>
                    <input type="text" name="kw" value="<%= data.getKw() %>" required>

                <p>
                    <label>カテゴリー</label>
                    <select name="category">
                    <%for (Map.Entry<String, Integer> entry : Dataset.getCategoryMap().entrySet()) { %>
                    	<option value="<%= entry.getValue()%>"><%= entry.getKey()%></option>
                    <% } %>
                    </select>
                </p>

                <p>
                	<label>レベル</label>
                    <select name="level">
                    	<option value="1">レベル１</option>
                    	<option value="2">レベル２</option>
                    	<option value="3">レベル３</option>
                    	<option value="4">レベル４</option>
                    </select>
                   
                </p>
                <p><button type="submit">送信</button></p>

</form>
</body>
</html>