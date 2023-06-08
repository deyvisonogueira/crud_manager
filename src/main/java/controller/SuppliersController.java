package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Supplier;
import model.Company;
import model.ModelException;
import model.dao.SupplierDAO;
import model.dao.DAOFactory;
import model.dao.CompanyDAO;


@WebServlet(urlPatterns = {"/suppliers", "/supplier/form", "/supplier/insert","/supplier/delete", "/supplier/update"})
public class SuppliersController extends HttpServlet {



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/supplier/form": {
			listSuppliers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-supplier.jsp");
			break;
		}
		case "/crud-manager/supplier/update": {
			String idStr = req.getParameter("supplierId");
			int idSupplier = Integer.parseInt(idStr);

			SupplierDAO dao = DAOFactory.createDAO(SupplierDAO.class);

			Supplier supplier = null;
			try {
				supplier = dao.findById(idSupplier);
			} catch (ModelException e) {
				e.printStackTrace();
			}

			listSuppliers(req);
			req.setAttribute("action", "update");
			req.setAttribute("supplier", supplier);
			ControllerUtil.forward(req, resp, "/form-supplier.jsp");

			break;
		}
		default:
			listSuppliers(req);

			ControllerUtil.transferSessionMessagesToRequest(req);

			ControllerUtil.forward(req, resp, "/suppliers.jsp");
		}
	}


	private void listSuppliers(HttpServletRequest req) {
		SupplierDAO dao = DAOFactory.createDAO(SupplierDAO.class);
		List<Supplier> suppliers = null;

		try {
			suppliers = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (suppliers != null) {
			req.setAttribute("suppliers", suppliers);
		}

		CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class);
		List<Company> companies = null;

		try {
			companies = companyDAO.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (companies != null) {
			req.setAttribute("companies", companies);
		}
	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String action = req.getRequestURI();


		switch (action) {
		case "/crud-manager/supplier/insert": {

			insertSupplier(req, resp);
			break;
		}

		case "/crud-manager/supplier/delete": {

			deleteSupplier(req, resp);

			break;
		}

		case "/crud-manager/supplier/update": {

			updateSupplier(req, resp);

			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
		//redireciona a pagina
		ControllerUtil.redirect(resp, req.getContextPath()+"/suppliers");
	}

	private void updateSupplier(HttpServletRequest req, HttpServletResponse resp) {
	    String supplierIdStr = req.getParameter("supplierId");
	    String supplierName = req.getParameter("name");
	    String cnpj = req.getParameter("cnpj");
	    String branch = req.getParameter("branch");
	    String contract_start = req.getParameter("contract_start");
	    String contract_end = req.getParameter("contract_end");

	    Integer companyId = null;
	    String companyIdStr = req.getParameter("company");

	    if (companyIdStr != null && !companyIdStr.isEmpty()) {
	        companyId = Integer.parseInt(companyIdStr);
	    }

	    Supplier supplier = new Supplier(Integer.parseInt(supplierIdStr));
	    supplier.setName(supplierName);
	    supplier.setCnpj(cnpj);
	    supplier.setBranch(branch);
	    supplier.setContract_start(ControllerUtil.formatDate(contract_start));
	    supplier.setContract_end(ControllerUtil.formatDate(contract_end));
	    supplier.setCompany(new Company(companyId));

		SupplierDAO dao = DAOFactory.createDAO(SupplierDAO.class);

		try {
			if (dao.update(supplier)) {
				ControllerUtil.sucessMessage(req, "Fornecedor '" + supplier.getName() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Fornecedor '" + supplier.getName() + "' não pode ser atualizado.");
			}
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteSupplier(HttpServletRequest req, HttpServletResponse resp) {
		String supplierIdParameter = req.getParameter("id");

		int supplierId = Integer.parseInt(supplierIdParameter);

		SupplierDAO dao = DAOFactory.createDAO(SupplierDAO.class);		

		try {
			Supplier supplier = dao.findById(supplierId);

			if (supplier == null)
				throw new ModelException("Fornecedor não encontrado para deleção.");

			if (dao.delete(supplier)) {
				ControllerUtil.sucessMessage(req, "Fornecedor '" + supplier.getName() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Fornecedor '" + supplier.getName() + "' não pode ser deletado. Há dados relacionados ao forncedor.");
			}
		} catch (ModelException e) {
			// log no servidor
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

	}

	private void insertSupplier(HttpServletRequest req, HttpServletResponse resp) {
		// Obter dados do formulário
		String supplierName = req.getParameter("name");
		String cnpj = req.getParameter("cnpj");
		String branch = req.getParameter("branch");
		String contract_start = req.getParameter("contract_start");
		String contract_end = req.getParameter("contract_end");
		Integer companyId = null;
		String companyIdStr = req.getParameter("company");

		if (companyIdStr != null && !companyIdStr.isEmpty()) {
			companyId = Integer.parseInt(companyIdStr);
		}

		Supplier sup = new Supplier();
		sup.setName(supplierName);
		sup.setCnpj(cnpj);
		sup.setBranch(branch);
		sup.setContract_start(ControllerUtil.formatDate(contract_start));
		sup.setContract_end(ControllerUtil.formatDate(contract_end));
		sup.setCompany(new Company(companyId));

		// Persistência
		SupplierDAO dao = DAOFactory.createDAO(SupplierDAO.class);

		try {
			if (dao.save(sup)) {
				ControllerUtil.sucessMessage(req, "Fornecedor '" + sup.getName() + "' salvo com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Fornecedor '" + sup.getName() + "' não pode ser salvo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

}
