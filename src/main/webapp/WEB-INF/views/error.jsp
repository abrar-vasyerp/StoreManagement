<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5 text-center">
    <div class="card border-danger shadow-sm">
        <div class="card-header bg-danger text-white">
            <h3>⚠️ Something went wrong!</h3>
        </div>
        <div class="card-body">
            <p class="lead">${errorMessage}</p>
            <a href="${pageContext.request.contextPath}/productsTest/getAll" class="btn btn-primary mt-3">
                Back to Product List
            </a>
        </div>
    </div>
</div>

</body>
</html>
