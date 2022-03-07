<!DOCTYPE html>
<html>
    <head>
        <title>VegeShop - User</title>
        <c:import url="component/head.jspf"/>
    </head>
    <body>
        <c:import url="component/header.jspf"/>
        <div class="container bg-light rounded-3 my-4">
            <h3 class="py-4">Order</h3>
            <c:choose>
                <c:when test="${requestScope.ORDERS != null}">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Date</th>
                                <th scope="col">Total</th>
                                <th scope="col"> </th>
                            </tr>
                        </thead>
                        <tbody class="table-hover">

                            <c:forEach items="${requestScope.ORDERS}" var="order">
                                <tr>
                                    <td>${order.getOrderID()}</td>
                                    <td>
                                        ${order.getFormatedOrderDate()}
                                    </td>
                                    <td>${order.getFormattedTotal()}</td>
                                    <td>                                  
                                        <a type="submit"
                                                class="btn btn-light btn-sm"
                                                href="UserController?orderID=${order.getOrderID()}"
                                                >
                                            <i class="bi bi-view-list text-success"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="d-flex justify-content-end py-4">
                        <a href="HomeController" class="btn btn-secondary mx-2">Back to continue shopping</a>
                    </div>
                </c:when>

                <c:when test="${requestScope.ORDER_PRODUCTS != null}">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb px-3">
                            <li class="breadcrumb-item"><a style="text-decoration: none" href="UserController">Orders</a></li>
                            <li class="breadcrumb-item active" aria-current="page">${param.orderID}</li>
                        </ol>
                    </nav>
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Name</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Price</th>
                            </tr>
                        </thead>
                        <tbody class="table-hover">
                            <c:forEach items="${requestScope.ORDER_PRODUCTS}" var="product">
                                <tr>
                                    <td>${product.getProductID()}</td>
                                    <td>
                                        ${product.getProductName()}
                                    </td>
                                    <td>${product.getQuantity()}</td>
                                    <td>${product.getFormattedPrice()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="d-flex justify-content-end py-4">
                        <a href="HomeController" class="btn btn-secondary mx-2">Back to continue shopping</a>
                    </div>
                </c:when>

                <c:otherwise>
                    <figure class="text-center py-4">
                        <blockquote class="blockquote fw-bold">
                            <p>You don't have any orders</p>
                        </blockquote>
                        <figcaption class="blockquote-footer pt-2">
                            Please return to the <a class="link-success" href="HomeController">Home page</a> to continue shopping...
                        </figcaption>
                    </figure>
                </c:otherwise>
            </c:choose>
        </div>
        <c:import url="component/scripts.jspf"/>
    </body>
</html>
