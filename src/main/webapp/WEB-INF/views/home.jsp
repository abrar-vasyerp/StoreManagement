<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Home</title>
</head>
<body>
<%!
    int a=5;
    int b=4;
    public int sum(){
        return a+b;
    }
%>
<h1>Spring Boot 4 + JSP</h1>
<p>${message}</p>
a is <%=a%> and b is <%=b%> then sum is<%=sum()%>
</body>
</html>
