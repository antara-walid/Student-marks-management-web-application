<%--
  Created by IntelliJ IDEA.
  User: walid
  Date: 6/11/2022
  Time: 2:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<jsp:include page="../fragments/userheader.jsp" />

<div class="container">

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">

            <jsp:include page="../fragments/usermenu.jsp" />

        </div>
    </nav>


    <div>
        <h3>liste of  students to be updated</h3>
    </div>

    <div>

        <f:form action="${pageContext.request.contextPath}/cadreadmin/updatesEtudiants" modelAttribute="checkBox">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Donees de fichier excel</th>
                <th scope="col">Donees en base de donnee</th>
                <th scope="col">mettre a jour</th>

            </tr>
            </thead>
            <c:forEach items="${listReainscrire}" var="a" varStatus="status">
                <tr>
                    <td><c:out value="${a.getNom()}" /></td>
                    <td><c:out value="${listOriginal.get(status.index).getNom()}" /></td>

                    <td><form:checkbox  path="checked" value="${status.index}"/> </td>

                </tr>

            </c:forEach>

        </table>
            <div style="text-align: right">
                <button type="submit" class="btn btn-primary">Update</button>
            </div>

        </f:form>
    </div>

    <jsp:include page="../fragments/adminfooter.jsp" />
