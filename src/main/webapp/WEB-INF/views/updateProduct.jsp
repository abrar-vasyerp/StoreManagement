<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update Product</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .error {
            color: red;
            font-size: 0.9rem;
        }
    </style>
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
    <div class="container">
        <a class="navbar-brand" href="#">ProductApp</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/productsTest/getAll">Product List</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/productsTest/create">Create New</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <h2 class="mb-4">✏️ Update Product</h2>

    <form action="${pageContext.request.contextPath}/productsTest/edit/${productId}" method="post">

        <!-- Product Name -->
        <div class="mb-3">
            <label for="productName" class="form-label">Product Name</label>
            <input type="text"
                   id="productName"
                   name="productName"
                   class="form-control"
                   value="${product.productName}"
                   required minlength="2" maxlength="20">
        </div>

        <!-- Selling Price -->
        <div class="mb-3">
            <label for="sellingPrice" class="form-label">Selling Price</label>
            <input type="number"
                   id="sellingPrice"
                   name="sellingPrice"
                   class="form-control"
                   value="${product.sellingPrice}"
                   step="0.01" min="0.01" required>
        </div>

        <!-- Tax -->
        <div class="mb-3">
            <label for="tax" class="form-label">Tax (%)</label>
            <input type="number"
                   id="tax"
                   name="tax"
                   class="form-control"
                   value="${product.tax}"
                   min="0" required>
        </div>

        <!-- Version (for optimistic locking) -->
        <input type="hidden" name="version" value="${product.version}">

        <button type="submit" class="btn btn-primary">Update</button>
        <a href="${pageContext.request.contextPath}/productsTest/getAll" class="btn btn-secondary">Cancel</a>
    </form>

    <!-- Optimistic lock / general error -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3">
                ${error}
        </div>
    </c:if>
</div>

</body>
</html>
