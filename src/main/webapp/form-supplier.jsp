<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="base-head.jsp"%>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">

		<h3 class="page-header">${not empty supplier ? 'Atualizar' : 'Adicionar' }
			Fornecedor</h3>

		<form action="${pageContext.request.contextPath}/supplier/${action}"
			method="POST">
			<input type="hidden" value="${supplier.getId()}" name="supplierId">
			<div class="row">
				<div class="form-group col-md-4">
					<label for="name">Nome</label> <input type="text"
						class="form-control" id="name" name="name" autofocus="autofocus"
						placeholder="Nome Fornecedor" required
						oninvalid="this.setCustomValidity('Por favor, informe o nome do fornecedor.')"
						oninput="setCustomValidity('')" value="${supplier.getName()}">
				</div>

				<div class="form-group col-md-4">
					<label for="cnpj">CNPJ</label> <input type="text"
						class="form-control" id="cnpj" name="cnpj" autofocus="autofocus"
						placeholder="XX.XXX.XXX/0001-XX" required
						oninvalid="this.setCustomValidity('Por favor, informe o CNPJ do fornecedor.')"
						oninput="setCustomValidity('')" value="${supplier.getCnpj()}">
				</div>
				<div class="form-group col-md-4">
					<label for="branch">Ramo</label> <input type="text"
						class="form-control" id="branch" name="branch"
						autofocus="autofocus" placeholder="Ramo" required
						oninvalid="this.setCustomValidity('Por favor, informe o ramo do fornecedor.')"
						oninput="setCustomValidity('')" value="${supplier.getBranch()}">
				</div>

			</div>

			<div class="row">
				<div class="form-group col-md-4">
					<label for="contract_start">Data do início do contrato</label> <input
						type="date" class="form-control" id="contract_start"
						name="contract_start" autofocus="autofocus"
						placeholder="Data de início do contrato" required
						oninvalid="this.setCustomValidity('Por favor, informe a data de início do contrato.')"
						oninput="setCustomValidity('')"
						value="${supplier.getContract_start()}">
				</div>

				<div class="form-group col-md-4">
					<label for="contract_end">Data do fim do contrato</label> <input
						type="date" class="form-control" id="contract_end"
						name="contract_end" autofocus="autofocus"
						placeholder="Data fim do contrato"
						oninvalid="this.setCustomValidity('Por favor, informe a data de fim do contrato.')"
						oninput="setCustomValidity('')"
						value="${supplier.getContract_end()}">
				</div>

				<div class="form-group col-md-4">
					<label for="company">Empresa Contratante</label> <select id="company"
						class="form-control selectpicker" name="company" required
						oninvalid="this.setCustomValidity('Por favor, informe uma empresa parceira.')"
						oninput="setCustomValidity('')">
						<option value="" ${empty supplier.getCompany() ? 'selected' : ''}
							disabled>Selecione uma empresa parceira</option>
						<c:forEach var="company" items="${companies}">
							<option value="${company.getId()}"
								${supplier.getCompany() != null && supplier.getCompany().getId() == company.getId() ? 'selected' : ''}>${company.getName()}</option>
						</c:forEach>
					</select>

				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/suppliers"
							class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty supplier ? 'Atualizar' : 'Cadastrar'}
							Fornecedor</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
