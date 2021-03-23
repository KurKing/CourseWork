<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
    <head>
        <title>Log in</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" type="image/png" href="../images/icons/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="../vendor/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="../vendor/animate/animate.css">
        <link rel="stylesheet" type="text/css" href="../vendor/css-hamburgers/hamburgers.min.css">
        <link rel="stylesheet" type="text/css" href="../vendor/select2/select2.min.css">
        <link rel="stylesheet" type="text/css" href="../css/util.css">
        <link rel="stylesheet" type="text/css" href="../css/main.css">
    </head>
    <body>
        <div class="bg-contact2" style="background-image: url('../images/bg-02.jpg');">
            <div class="container-contact2">
                <div class="wrap-contact2">
                    <form method="post" action="${pageContext.request.contextPath}/tenders/login" class="contact2-form validate-form">

                        <span class="contact2-form-title">
                            Log in
                        </span>

                        <div class="wrap-input2 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
                            <input class="input2" type="text" name="email">
                            <span class="focus-input2" data-placeholder="EMAIL"></span>
                        </div>

                        <div class="wrap-input2 validate-input" data-validate="Password is required">
                            <input class="input2" type="password" name="password">
                            <span class="focus-input2" data-placeholder="PASSWORD"></span>
                        </div>

                        <div class="container-contact2-form-btn">
                            <div class="wrap-contact2-form-btn">
                                <div class="contact2-form-bgbtn"></div>

                                <button class="contact2-form-btn" type="submit">
                                    Log in!
                                </button>

                            </div>
                        </div>

                    </form>

                    <a href="${pageContext.request.contextPath}/tenders/signup"><u>Sign up</u></a>

                </div>
            </div>
        </div>

        <script src="../vendor/jquery/jquery-3.2.1.min.js"></script>
        <script src="../vendor/bootstrap/js/popper.js"></script>
        <script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../vendor/select2/select2.min.js"></script>
        <script src="../js/main.js"></script>

    </body>
</html>
