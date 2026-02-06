<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Variant</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h3 class="mb-4">Add Variant</h3>

    <form method="post"
          action="${pageContext.request.contextPath}/productvariant/create/${productId}"
          novalidate>

        <!-- Variant Name -->
        <div class="mb-3">
            <label class="form-label">Variant Name</label>
            <input type="text"
                   name="variantName"
                   class="form-control"
                   required
                   minlength="2">
        </div>

        <!-- Variant Price -->
        <div class="mb-3">
            <label class="form-label">Variant Price</label>
            <input type="number"
                   name="variantPrice"
                   class="form-control"
                   step="0.01"
                   min="0.01"
                   required>
        </div>

        <button type="submit" class="btn btn-success">Add Variant</button>

        <a href="${pageContext.request.contextPath}/productsTest/variant/list/${productId}"
           class="btn btn-secondary ms-2">
            Cancel
        </a>

    </form>
</div>

</body>
</html>
