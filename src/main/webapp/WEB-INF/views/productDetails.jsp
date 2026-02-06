<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">


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
                        <a class="nav-link" href="${pageContext.request.contextPath}/productvariant/create/${product.productId}">Add New Variant</a>

                    </li>
                </ul>
            </div>
        </div>
    </nav>
<div class="container mt-5">
    <h2>${product.productName}</h2>
    <p><strong>Selling Price:</strong> ${product.sellingPrice}</p>
    <p><strong>Tax:</strong> ${product.tax}%</p>

    <h4>Variants</h4>
    <ul class="list-group">
        <table class="table table-striped table-bordered table-hover bg-white">
            <thead class="table-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Variant Name</th>
                    <th scope="col">Variant Price</th>
                    <th scope="col">Actions</th>

                </tr>
            </thead>
            <tbody>

                    <c:forEach var="variant" items="${product.variants}" varStatus="status">
                        <tr>
                                <td>${status.count}</td>
                                <td > ${variant.variantName}</td>
                                <td >${variant.variantPrice}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/productvariant/edit/${variant.variantId}" class="btn btn-sm btn-warning me-2">Edit</a>
                                    <button
                                            class="btn btn-sm btn-danger delete-variant-btn"
                                            data-variant-id="${variant.variantId}"
                                            data-product-id="${variant.productId}">
                                        Delete
                                    </button>
                                </td>
                        </tr>
                    </c:forEach>
                </tbody>


        </table>
    </ul>
    <div class="card-body d-flex p-2">
        <p class="lead">${errorMessage}</p>
        <a href="${pageContext.request.contextPath}/productsTest/getAll" class="btn btn-primary mt-3">
            Back to Product List
        </a>
    </div>
</div>
    <script>
        console.log("Product ID:", "${product.productId}");
    </script>
</body>
<script>
    document.addEventListener('DOMContentLoaded', function() {

        const contextPath = '<%= request.getContextPath() %>';


        const deleteButtons = document.querySelectorAll('.delete-variant-btn');

        deleteButtons.forEach(button => {
            button.addEventListener('click', function() {
                const variantId = this.getAttribute('data-variant-id');
                const productId = this.getAttribute('data-product-id');

                if (confirm('Are you sure you want to delete this variant?')) {
                    // Make a POST request using fetch
                    fetch(contextPath+`/productvariant/delete/`+variantId, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: `productId=`+productId
                    })
                        .then(response => {
                            if (response.ok) {
                                // Redirect back to product details
                                window.location.href = contextPath+`/productsTest/details/`+productId;
                            } else {
                                alert('Failed to delete variant.');
                            }
                        })
                        .catch(error => {
                            console.error('Error deleting variant:', error);
                            alert('Error deleting variant.');
                        });
                }
            });
        });
    });
</script>

</html>
