<!DOCTYPE html>
<html>
    <head>
        <title>VeShop - Checkout</title>
        <c:import url="component/head.jspf"/>
        <script src="https://www.google.com/recaptcha/api.js?hl=en" async defer></script>
    </head>
    <body>
        <c:import url="component/header.jspf"/>
        <div class="container bg-light rounded-3 my-4">
            <h3 class="py-4">Checkout</h3>
            <c:if test="${requestScope.PRODUCT_ERROR != null}">
                <div class="alert alert-warning" role="alert">
                    ${requestScope.PRODUCT_ERROR}
                </div>
            </c:if>
            <c:if test="${requestScope.ORDER_ERROR != null}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.ORDER_ERROR}
                </div>
            </c:if>
            <c:if test="${requestScope.SUCCESS != null}">
                <div class="alert alert-success" role="alert">
                    ${requestScope.SUCCESS}
                </div>
            </c:if>
            <c:choose>
                <c:when test="${sessionScope.CART.getProducts().size() != 0}">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th scope="col">Product Name</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Price</th>
                            </tr>
                        </thead>
                        <tbody class="table-hover">
                            <c:forEach items="${sessionScope.CART.getProducts()}" var="product">
                                <tr>
                                    <td>${product.key.getProductName()}</td>
                                    <td>
                                        ${product.value}
                                    </td>
                                    <td>${product.key.getFormattedPrice()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="text-end fw-bold pb-2">
                        Total: ${sessionScope.CART.getFormattedTotal()}
                    </div>

                    <div class="d-flex justify-content-end py-4">
                        <a href="HomeController" class="btn btn-secondary mx-2">Back to continue shopping</a>
                        <form action="CheckoutController" method="POST">
                            <button type="submit"
                                    class="btn btn-danger mx-2"
                                    name="action"
                                    value="Cancel"
                                    >Cancel</button>
                        </form>
                        <button type="button" class="btn btn-success mx-2" data-bs-toggle="modal" data-bs-target="#OrderModal">
                            Order
                        </button>
                    </div>

                    <div class="modal fade" id="OrderModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="CheckoutController" method="POST" id="OrderForm">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Order</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-outline form-white mb-4">
                                            <input
                                                placeholder="Email"
                                                type="email"
                                                class="form-control form-control-lg"
                                                name="email"
                                                value="${sessionScope.CURRENT_USER.getEmail()}"
                                                required
                                                />
                                        </div>

                                        <div class="form-outline form-white mb-4">
                                            <input
                                                placeholder="Phone"
                                                type="tel"
                                                pattern="(84|0[3|5|7|8|9])+([0-9]{8})"
                                                class="form-control form-control-lg"
                                                name="phone"
                                                value="${sessionScope.CURRENT_USER.getPhone()}"
                                                required
                                                />
                                        </div>

                                        <div class="form-outline form-white mb-4">
                                            <input
                                                placeholder="Address"
                                                type="text"
                                                class="form-control form-control-lg"
                                                name="address"
                                                value="${sessionScope.CURRENT_USER.getAddress()}"
                                                required
                                                />
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <div class="d-flex">
                                            <div class="g-recaptcha" data-sitekey="6Lf9KMEeAAAAAJsU04MyAc3h79Yokp0ZOgf0VwR-"></div>
                                        </div>
                                        <div>
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <button type="submit"
                                                    class="btn btn-success"
                                                    name="action"
                                                    value="Order"
                                                    >Confirm</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <figure class="text-center py-4">
                        <blockquote class="blockquote fw-bold">
                            <p>Cart is empty</p>
                        </blockquote>
                        <figcaption class="blockquote-footer pt-2">
                            Please return to the <a class="link-success" href="HomeController">Home page</a> to continue shopping...
                        </figcaption>
                    </figure>
                </c:otherwise>
            </c:choose>
        </div>
        <script>
            function onSubmit(token) {
                document.getElementById("OrderForm").submit();
            }
        </script>
        <c:import url="component/scripts.jspf"/>
    </body>
</html>
