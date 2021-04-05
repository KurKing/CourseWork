<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <title>Tenders</title>
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

    <style>

        .tender-table {
            width: 100%;
        }
        .header-user-td{
            font-size: 24px;
        }
        .header-name-td{
            text-align: center;
            font-size: 24px;
            border-right: 1px solid black;
            width: 25%;
        }
        .header-info-td{
            text-align: center;
            font-size: 24px;
        }
        .name-td{
            text-align: center;
            border-right: 1px solid black;
            border-top: 1px solid black;
            font-size: 22px;
            width: 25%;
        }
        .info-td{
            border-top: 1px solid black;
        }
        td{
            padding: 20px;
        }

    </style>

</head>
<body>

<c:if test="${errorMessage != null}">
    <script>
        alert("${errorMessage}")
    </script>
</c:if>

<div class="bg-contact2" style="background-image: url('../images/bg-02.jpg');">
    <div class="container-contact2">
        <div class="wrap-contact3">
            <table class="tender-table">
                <c:choose>
                    <c:when test="${!empty user}">
                        <tr>
                            <td class="header-user-td">
                                Hello, <c:out value="${user.getName()}"/>!
                            </td>
                            <td align="right">
                                <table>
                                    <tr>
                                        <c:if test="${tender.isOwner(user.getLogin())}">
                                            <c:choose>
                                                <c:when test="${tender.isActive()}">
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/tenders/setStatus?tenderId=${tender.getId()}&status=0">
                                                            <u>Disable tender</u>
                                                        </a>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/tenders/setStatus?tenderId=${tender.getId()}&status=1">
                                                            <u>Activate tender</u>
                                                        </a>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/tenders/deleteTender?tenderId=${tender.getId()}">
                                                    <u>Delete tender</u>
                                                </a>
                                            </td>
                                        </c:if>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/tenders/newOffer?tenderId=${tender.getId()}"><u>Create offer</u></a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/tenders/newTender"><u>Create tender</u></a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/tenders/"><u>All tenders</u></a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/tenders/myTenders"><u>My tenders</u></a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/tenders/logout"><u>Log out</u></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td></td>
                            <td align="right">
                                <a href="${pageContext.request.contextPath}/tenders/login"><u>Log in</u></a>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <td class="header-name-td">
                        <b>Name</b>
                    </td>
                    <td class="header-info-td">
                        <b><c:out value="${tender.getName()}"/></b>
                    </td>
                </tr>
                <tr>
                    <td class="name-td">
                        <b>About</b>
                    </td>
                    <td class="info-td">
                        <b><c:out value="${tender.getAbout()}"/></b>
                    </td>
                </tr>
                <tr>
                    <td class="name-td">
                        <b>Owner</b>
                    </td>
                    <td class="info-td">
                        <b><c:out value="${tender.getOwner()}"/></b>
                    </td>
                </tr>
                <tr>
                    <td class="name-td">
                        <b>Status:</b>
                    </td>
                    <td class="info-td">
                        <b>
                        <c:choose>
                            <c:when test="${tender.isActive()}">
                                Active
                            </c:when>
                            <c:otherwise>
                                Disabled
                            </c:otherwise>
                        </c:choose>
                        </b>
                    </td>
                </tr>

                <tr>
                    <td class="name-td">
                        <b>Offers</b>
                    </td>

               <c:choose>
                    <c:when test="${tender.getOffers().size()!=0}">

                            <td class="info-td">
                                <table>
                                    <c:forEach var="offer" items="${tender.getOffers()}">
                                        <tr>
                                            <td>
                                                From: <c:out value="${offer.getOwner()}"/><br>
                                                Text: <c:out value="${offer.getText()}"/><br>
                                                Money: <c:out value="${offer.getMoney()}$"/><br>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>

                    </c:when>
                    <c:otherwise>

                            <td class="info-td">
                                No offers yet... <a href="${pageContext.request.contextPath}/tenders/newOffer?tenderId=${tender.getId()}"><u>Create new one!</u></a>
                            </td>

                    </c:otherwise>
                </c:choose>

                </tr>

            </table>
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
