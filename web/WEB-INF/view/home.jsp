<!DOCTYPE html>
<html>
    <head>
        <title>VegeShop - Home</title>
        <c:import url="component/head.jspf"/>
    </head>
    <body>
        <c:import url="component/header.jspf"/>
        <div class="container bg-light rounded-3">
            <form class="d-flex m-4 p-3" action="HomeController">
                <input class="form-control me-2"
                       type="search"
                       placeholder="Search"
                       aria-label="Search"
                       name="search"
                       value="${param.search}">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
        <div class="container d-flex flex-column align-items-center pb-4">
            <c:set var="count" scope="request" value="0"/>
            <c:set var="itemsPerRow" scope="request" value="4"/>
            <c:forEach items="${requestScope.PRODUCTS}" var="product">
                <c:if test="${count % itemsPerRow == 0}">
                    <div class="card-group">
                        <c:forEach items="${requestScope.PRODUCTS}" var="item" begin="${count}" end="${count + itemsPerRow - 1}">
                            <form action="HomeController" method="POST">
                                <div class="card mx-2 my-2" style="width: 18rem;">
                                    <img src="${item.getImage()}" class="card-img-top" alt="..">
                                    <div class="card-body">
                                        <h5 class="card-title">
                                            <c:out value="${item.getProductName()}"/>
                                        </h5>
                                        <h6 class="card-title my-4">Category: ${item.getCategoryName()}</h6>

                                        <h6 class="card-title">Price: ${item.getFormattedPrice()}</h6>
                                        <h6 class="card-title mb-4">Remaining amount: ${item.getQuantity()}</h6>

                                        <div class="d-inline-flex justify-content-center w-100">
                                            <input type="hidden"
                                                   name="productID"
                                                   value="${item.getProductID()}"
                                                   >
                                            <input class="form-control form-control-sm w-50 bg-light"
                                                   type="number"
                                                   name="quantity"
                                                   value="1"
                                                   min="1"
                                                   max="${item.getQuantity()}"
                                                   <c:if test="${item.getQuantity() == 0}">
                                                       disabled
                                                   </c:if>
                                                   required
                                                   >
                                            <input class="btn btn-success w-25 ms-2"
                                                   name="action"
                                                   value="Add"
                                                   type="submit"
                                                   <c:if test="${item.getQuantity() == 0}">
                                                       disabled
                                                   </c:if>
                                                   >
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </c:forEach>
                    </div>                                    
                </c:if>
                <c:set var="count" value="${count+1}" />
            </c:forEach>
        </div>

        <!--Cart Modal-->
        <div class="modal fade" id="CART" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Cart</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <table class="table align-middle">
                            <thead>
                                <tr>
                                    <th scope="col">Product Name</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Price</th>
                                    <th scope="col"> </th>
                                </tr>
                            </thead>
                            <tbody class="table-hover">
                                <c:forEach items="${sessionScope.CART.getProducts()}" var="product">
                                <form action="HomeController" method="POST">
                                    <tr>
                                        <td>${product.key.getProductName()}</td>
                                        <td class="d-flex">
                                            <input class="form-control form-control-sm w-50 bg-light"
                                                   type="number"
                                                   name="quantity"
                                                   value="${product.value}"
                                                   min="1"
                                                   max="${product.key.getQuantity()}"
                                                   required
                                                   >
                                            <button type="submit"
                                                    class="btn btn-light btn-sm"
                                                    name="action"
                                                    value="Update"
                                                    >
                                                <i class="bi bi-arrow-repeat text-dark"></i>
                                            </button>
                                        </td>
                                        <td>${product.key.getFormattedPrice()}</td>
                                        <td class="d-flex justify-content-center">
                                            <input type="hidden"
                                                   name="productID"
                                                   value="${product.key.getProductID()}"
                                                   >
                                            <button type="submit"
                                                    class="btn btn-light btn-sm"
                                                    name="action"
                                                    value="Remove"
                                                    >
                                                <i class="bi bi-dash-lg text-danger"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>

                        <div class="text-end fw-bold">
                            Total: ${sessionScope.CART.getFormattedTotal()}
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <a class="btn btn-success" href="CheckoutController">Checkout</a>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="component/scripts.jspf"/>
    </body>
</html>
