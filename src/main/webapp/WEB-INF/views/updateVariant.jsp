<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update Variant</title>
    <style>
        .error { color: red; }
        .form-group { margin-bottom: 10px; }
    </style>
</head>
<body>

<h2>Update Product Variant</h2>

<form
        method="post"
        action="${pageContext.request.contextPath}/productvariant/edit/${variantId}">

    <!-- Variant Name -->
    <div class="form-group">
        <label>Variant Name</label><br/>
        <input type="text"
               name="variantName"
               value="${variant.variantName}"
               required />
    </div>

    <!-- Selling Price -->
    <div class="form-group">
        <label>Variant Price</label><br/>
        <input type="number"
               name="variantPrice"
               value="${variant.variantPrice}"
               step="0.01"
               required />
    </div>

    <input type="hidden" name="productId" value="${variant.productId}">


    <br/>

    <button type="submit">Update Variant</button>
    <a href="${pageContext.request.contextPath}/productsTest/getAll">
        Cancel
    </a>

</form>

</body>
</html>
