<!DOCTYPE html>
<html>
    <head>
        <title>VegeShop - Register</title>
        <c:import url="component/head.jspf"/>
    </head>
    <body>
        <c:import url="component/header.jspf"/>
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card bg-dark text-white" style="border-radius: 1rem">
                        <div class="card-body p-5 text-center">
                            <div class="mb-md-5 mt-md-4 pb-5">
                                <h2 class="fw-bold mb-2 text-uppercase">Register</h2>
                                <p class="text-white-50 mb-5">
                                    Please enter enough information
                                </p>
                                <c:if test = "${requestScope.REGISTER_ERROR != null}">
                                    <div class="alert alert-danger" role="alert">
                                        ${requestScope.REGISTER_ERROR}
                                    </div>
                                </c:if>
                                <form action="RegisterController" method="post">
                                    <div class="form-outline form-white mb-4">
                                        <input
                                            placeholder="User ID"
                                            type="text"
                                            class="form-control form-control-lg"
                                            name="userID"
                                            required
                                            />
                                    </div>
                                    <div class="form-outline form-white mb-4">
                                        <input
                                            placeholder="Password"
                                            type="password"
                                            class="form-control form-control-lg"
                                            name="password"
                                            required
                                            />
                                    </div>

                                    <div class="form-outline form-white mb-4">
                                        <input
                                            placeholder="Confirm password"
                                            type="password"
                                            class="form-control form-control-lg"
                                            name="confirmPassword"
                                            required
                                            />
                                    </div>

                                    <div class="form-outline form-white mb-4">
                                        <input
                                            placeholder="Email"
                                            type="email"
                                            class="form-control form-control-lg"
                                            name="email"
                                            value="${param.email}"
                                            required
                                            />
                                    </div>

                                    <div class="form-outline form-white mb-4">
                                        <input
                                            placeholder="Full Name"
                                            type="text"
                                            class="form-control form-control-lg"
                                            name="fullName"
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
                                            required
                                            />
                                    </div>

                                    <div class="form-outline form-white mb-4">
                                        <input
                                            placeholder="Address"
                                            type="text"
                                            class="form-control form-control-lg"
                                            name="address"
                                            required
                                            />
                                    </div>

                                    <button
                                        class="btn btn-outline-light btn-lg px-5"
                                        type="submit"
                                        name="action"
                                        value="Register"
                                        >
                                        Register
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
        <c:import url="component/scripts.jspf"/>
    </body>
</html>
