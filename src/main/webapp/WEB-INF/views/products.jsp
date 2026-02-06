<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Products</title>
    <!-- Bootstrap CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<!-- Navbar -->
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

<!-- Page Content -->
<div class="container mt-5">

    <h2 class="mb-4">📦 Product List</h2>

    <table class="table table-striped table-bordered table-hover bg-white">
        <thead class="table-dark">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Price</th>
            <th scope="col">Tax</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${products}" varStatus="status">
            <tr>
                <th scope="row">${status.count}</th>
                <td>
                    <a href="${pageContext.request.contextPath}/productsTest/details/${p.productId}" class="btn btn-sm me-2">${p.productName}</a>
                </td>
                <td>${p.sellingPrice}</td>
                <td>${p.tax}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/productsTest/edit/${p.productId}" class="btn btn-sm btn-warning me-2">Edit</a>
                    <button
                            class="btn btn-sm btn-danger delete-product-btn"
                            data-product-id="${p.productId}">
                        Delete
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>document.addEventListener('DOMContentLoaded', function() {
    const contextPath = '<%= request.getContextPath() %>';

    const deleteButtons = document.querySelectorAll('.delete-product-btn');

    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const productId = this.getAttribute('data-product-id');

            if (confirm('Are you sure you want to delete this product?')) {
                fetch(contextPath + '/productsTest/delete/' + productId, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            // Refresh the page after deletion
                            window.location.href = contextPath + '/productsTest/getAll';
                        } else {
                            alert('Failed to delete product.');
                        }
                    })
                    .catch(error => {
                        console.error('Error deleting product:', error);
                        alert('Error deleting product.');
                    });
            }
        });
    });
});
</script>
</body>
</html>
