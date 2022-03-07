<!DOCTYPE html>
<html>
    <head>
        <title>VegeShop - Admin</title>
        <c:import url="component/head.jspf"/>
        <meta name="google-signin-client_id" content="1060135796693-so41ec6uiagh6un8dq5mhatqpajbe70q.apps.googleusercontent.com">
    </head>
    <body>
        <c:import url="component/header.jspf"/>
        <div class="container my-4 px-5 py-5 bg-light rounded-3">
            <c:if test="${requestScope.CATEGORIES.size() != 0}">
                <div>
                    <div class="d-flex justify-content-between">
                        <h3>Categories</h3>
                        <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#CreateCategoryModal">
                            Create
                        </button>
                    </div>
                    <c:if test="${requestScope.CATEGORY_ERROR != null}">
                        <div class="alert alert-danger" role="alert">
                            ${requestScope.CATEGORY_ERROR}
                        </div>
                    </c:if>

                    <div class="modal fade" id="CreateCategoryModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="AdminController" method="POST">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Create category</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input type="number"
                                               name="categoryID"
                                               class="form-control my-2"
                                               placeholder="Category ID"
                                               min="1"
                                               required
                                               >
                                        <input type="text"
                                               name="categoryName"
                                               class="form-control my-2"
                                               maxlength="50"
                                               placeholder="Category Name"
                                               required
                                               >
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal">Close</button>
                                        <button type="submit"
                                                class="btn btn-outline-success"
                                                name="action"
                                                value="AddCategory"
                                                >Add</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table align-middle">
                            <thead>
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Name</th>
                                    <th scope="col"> </th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach var="category" items="${requestScope.CATEGORIES}">
                                <form action="AdminController" method="POST">
                                    <tr>
                                        <td>${category.getCategoryID()}</td>
                                        <td>
                                            <input type="hidden"
                                                   name="categoryID"
                                                   value="${category.getCategoryID()}"
                                                   >
                                            <input class="form-control form-control-sm bg-light"
                                                   type="text"
                                                   name="categoryName"
                                                   value="${category.getCategoryName()}"
                                                   maxlength="50"
                                                   required
                                                   >
                                        </td>
                                        <td>
                                            <button type="submit"
                                                    class="btn btn-light btn-sm"
                                                    name="action"
                                                    value="UpdateCategory"
                                                    >
                                                <i class="bi bi-arrow-repeat text-dark"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>

            <c:if test="${requestScope.CATEGORIES.size() != 0}">
                <div class="mt-4">
                    <div class="d-flex justify-content-between">
                        <h3>Products</h3>
                        <button type="button" class="btn btn-outline-success mx-2" data-bs-toggle="modal" data-bs-target="#CreateProductModal">
                            Create
                        </button>
                    </div>
                    <c:if test="${requestScope.PRODUCT_ERROR != null}">
                        <div class="alert alert-danger" role="alert">
                            ${requestScope.PRODUCT_ERROR}
                        </div>
                    </c:if>

                    <div class="modal fade" id="CreateProductModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="AdminController" method="POST">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Create product</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input type="number"
                                               name="productID"
                                               class="form-control my-2"
                                               placeholder="Product ID"
                                               min="1"
                                               required
                                               >
                                        <input type="text"
                                               name="productName"
                                               class="form-control my-2"
                                               maxlength="50"
                                               placeholder="Product Name"
                                               required
                                               >
                                        <input type="text"
                                               class="form-control my-2"
                                               name="image"
                                               maxlength="250"
                                               placeholder="Image"
                                               required
                                               >
                                        <input type="number"
                                               class="form-control my-2"
                                               name="quantity"
                                               min="1"
                                               placeholder="Quantity"
                                               required
                                               >
                                        <input type="number"
                                               class="form-control my-2"
                                               name="price"
                                               min="1"
                                               placeholder="Price"
                                               required
                                               >
                                        <select class="form-select" name="categoryID">
                                            <c:forEach var="category" items="${requestScope.CATEGORIES}">
                                                <option value="${category.getCategoryID()}"
                                                        <c:if test="${product.getCategoryID() == category.getCategoryID()}">
                                                            selected
                                                        </c:if>
                                                        >
                                                    ${category.getCategoryName()}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button type="submit"
                                                class="btn btn-outline-success"
                                                name="action"
                                                value="AddProduct"
                                                >Add</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table align-middle">
                            <thead>
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Image</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Category</th>
                                    <th scope="col"> </th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach var="product" items="${requestScope.PRODUCTS}">
                                <form action="AdminController" method="POST">
                                    <tr>
                                        <td>
                                            ${product.getProductID()}
                                            <input type="hidden"
                                                   name="productID"
                                                   value="${product.getProductID()}"
                                                   >
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm bg-light"
                                                   type="text"
                                                   name="productName"
                                                   value="${product.getProductName()}"
                                                   maxlength="50"
                                                   >
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm bg-light"
                                                   type="text"
                                                   name="image"
                                                   value="${product.getImage()}"
                                                   maxlength="250"
                                                   >
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm bg-light"
                                                   type="number"
                                                   name="quantity"
                                                   value="${product.getQuantity()}"
                                                   min="1"
                                                   >
                                        </td>
                                        <td>
                                            <input class="form-control form-control-sm bg-light"
                                                   type="number"
                                                   name="price"
                                                   value="${product.getPrice()}"
                                                   min="1"
                                                   >
                                        </td>
                                        <td>
                                            <select class="form-select" name="categoryID">
                                                <c:forEach var="category" items="${requestScope.CATEGORIES}">
                                                    <option value="${category.getCategoryID()}"
                                                            <c:if test="${product.getCategoryID() == category.getCategoryID()}">
                                                                selected
                                                            </c:if>
                                                            >
                                                        ${category.getCategoryName()}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <button type="submit"
                                                    class="btn btn-light btn-sm"
                                                    name="action"
                                                    value="UpdateProduct"
                                                    >
                                                <i class="bi bi-arrow-repeat text-dark"></i>
                                            </button>
                                            <button type="submit"
                                                    class="btn btn-light btn-sm"
                                                    name="action"
                                                    value="RemoveProduct"
                                                    >
                                                <i class="bi bi-dash-lg text-dark"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>
        <c:import url="component/scripts.jspf"/>
    </body>
</html>
