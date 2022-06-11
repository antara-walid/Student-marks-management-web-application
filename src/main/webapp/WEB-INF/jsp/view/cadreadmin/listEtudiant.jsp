<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>


page="../fragments/userheader.jsp" />

<div class="container">

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">

			<jsp:include page="../fragments/menu.jsp" />

		</div>
	</nav>






	<div>
		<h3>students List</h3>
	</div>

	<div>



			
		<table class="table">
			<thead>
				<tr>
					<th scope="col">CNE</th>
					<th scope="col">Nom</th>
					<th scope="col">Prénom</th>
					<th>Actions</th>
				</tr>
			</thead>

			<c:forEach items="${listeEtudiant}" var="p">
				<tr>

					<td><c:out value="${p.getCne}" /></td>
					<td><c:out value="${p.getNom}" /></td>

					<td><c:out value="${p.getPrenom}/></td>
				</tr>

			</c:forEach>

		</table>
