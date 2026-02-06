<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .text-danger { font-size: 0.9em; }
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

    <h2>Add Product</h2>
    <form id="productForm" action="${pageContext.request.contextPath}/productsTest/create" method="post" novalidate>

        <!-- Product Name -->
        <div class="mb-3">
            <label for="productName" class="form-label">Product Name</label>
            <input type="text"
                   id="productName"
                   name="productName"
                   class="form-control"
                   required
                   minlength="2"
                   maxlength="20">
            <small id="nameError" class="text-danger d-none">Product name already exists</small>
        </div>

        <!-- Selling Price -->
        <div class="mb-3">
            <label for="sellingPrice" class="form-label">Selling Price</label>
            <input type="number"
                   id="sellingPrice"
                   name="sellingPrice"
                   class="form-control"
                   step="0.01"
                   min="0.01"
                   required>
        </div>

        <!-- Tax -->
        <div class="mb-3">
            <label for="tax" class="form-label">Tax (%)</label>
            <input type="number"
                   id="tax"
                   name="tax"
                   class="form-control"
                   min="0"
                   required>
        </div>

        <!-- Add Variant Checkbox -->
        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" id="addVariantCheck" name="addVariant">
            <label class="form-check-label" for="addVariantCheck">Add Variant?</label>
        </div>

        <!-- Variants container -->
        <div id="variantsContainer" class="d-none">
            <h5>Variants</h5>
        </div>
        <button type="button" class="btn btn-secondary mb-3 d-none" id="addVariantBtn">Add Another Variant</button>

        <button type="submit" id="submitBtn" class="btn btn-success">Create</button>
    </form>
</div>

<script>
    const productNameInput = document.getElementById("productName");
    const nameError = document.getElementById("nameError");
    const submitBtn = document.getElementById("submitBtn");
    const addVariantCheck = document.getElementById("addVariantCheck");
    const variantsContainer = document.getElementById("variantsContainer");
    const addVariantBtn = document.getElementById("addVariantBtn");
    let variantIndex = 0;

    // Product name uniqueness check
    productNameInput.addEventListener("blur", function () {
        const name = this.value.trim();
        if (!name || name.length < 2) {
            nameError.classList.add("d-none");
            submitBtn.disabled = false;
            return;
        }

        fetch(`${pageContext.request.contextPath}/productsTest/check-name?name=` + encodeURIComponent(name))
            .then(res => res.json())
            .then(exists => {
                if (exists) {
                    nameError.classList.remove("d-none");
                    submitBtn.disabled = true;
                } else {
                    nameError.classList.add("d-none");
                    submitBtn.disabled = false;
                }
            });
    });

    // Show/hide variant fields
    addVariantCheck.addEventListener("change", function () {
        const checked = this.checked;
        variantsContainer.classList.toggle("d-none", !checked);
        addVariantBtn.classList.toggle("d-none", !checked);

        if (checked) {
            // Add first variant if none exist
            if (variantsContainer.querySelectorAll(".variantRow").length === 0) {
                addVariantRow();
            }
        } else {
            // Remove all variants if unchecked
            variantsContainer.innerHTML = '';
            variantIndex = 0;
        }
    });

    // Add new variant row
    addVariantBtn.addEventListener("click", addVariantRow);

    function addVariantRow() {
        const row = document.createElement("div");
        row.classList.add("variantRow", "mb-2");
        row.innerHTML = `
            <input type="text"
                   name="variants[`+variantIndex+`].variantName"
                   placeholder="Variant Name"
                   class="form-control mb-1"
                   required>
            <input type="number"
                   name="variants[`+variantIndex+`].variantPrice"
                   placeholder="Variant Price"
                   class="form-control"
                   step="0.01"
                   min="0.01"
                   required>
            <button type="button" class="btn btn-danger btn-sm mt-1 removeVariantBtn">Remove</button>
        `;
        variantsContainer.appendChild(row);


        variantIndex++;
    }

    // HTML5 validation + product name check
    document.getElementById("productForm").addEventListener("submit", function (e) {
        if (!this.checkValidity()) {
            e.preventDefault();
            e.stopPropagation();
            this.classList.add('was-validated');
        }

        if (!nameError.classList.contains("d-none")) {
            e.preventDefault();
            alert("Please fix product name before submitting.");
        }
    });
</script>
</body>
</html>
