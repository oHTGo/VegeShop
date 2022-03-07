<!DOCTYPE html>
<html>
    <head>
        <title>VegeShop - Login</title>
        <c:import url="component/head.jspf"/>
        <meta name="google-signin-client_id" content="1060135796693-so41ec6uiagh6un8dq5mhatqpajbe70q.apps.googleusercontent.com">
    </head>
    <body>
        <c:import url="component/header.jspf"/>
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card bg-dark text-white" style="border-radius: 1rem">
                        <div class="card-body p-5 text-center">
                            <div class="mb-md-5 mt-md-4 pb-5">
                                <h2 class="fw-bold mb-2 text-uppercase">Login</h2>
                                <p class="text-white-50 mb-5">
                                    Please enter your login and password!
                                </p>
                                <c:if test = "${requestScope.LOGIN_ERROR != null}">
                                    <div class="alert alert-danger" role="alert">
                                        ${requestScope.LOGIN_ERROR}
                                    </div>
                                </c:if>
                                <c:if test = "${param.OAUTH_ERROR != null}">
                                    <div class="alert alert-danger" role="alert">
                                        Invalid token
                                    </div>
                                </c:if>
                                <form action="LoginController" method="post">
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

                                    <button
                                        class="btn btn-outline-light btn-lg px-5"
                                        type="submit"
                                        name="action"
                                        value="Login"
                                        >
                                        Login
                                    </button>
                                </form>

                                <div class="d-flex justify-content-center text-center mt-4 pt-1">
                                    <div id="my-signin2"></div>
                                </div>
                            </div>

                            <div>
                                <p class="mb-0">
                                    Don't have an account?
                                    <a href="RegisterController" class="text-white-50 fw-bold">Register</a>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            async function onSuccess(googleUser) {
                const e = btoa(googleUser.getBasicProfile().getEmail());

                const auth2 = gapi.auth2.getAuthInstance();
                await auth2.signOut();

                window.location.replace("${baseURL}/AuthController?e=" + e);
            }
            function onFailure(error) {
                console.log(error);
            }
            function renderButton() {
                gapi.signin2.render('my-signin2', {
                    'scope': 'profile email',
                    'width': 35,
                    'theme': 'light',
                    'onsuccess': onSuccess,
                    'onfailure': onFailure
                });
            }
        </script>

        <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
        <c:import url="component/scripts.jspf"/>
    </body>
</html>
