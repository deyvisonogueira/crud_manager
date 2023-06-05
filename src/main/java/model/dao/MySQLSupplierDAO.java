package model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Company;
import model.ModelException;
import model.Supplier;

public class MySQLSupplierDAO implements SupplierDAO {

	@Override
	public boolean save(Supplier supplier) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO suppliers VALUES (DEFAULT, ?, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		
		db.setString(1, supplier.getName());
		db.setString(2, supplier.getCnpj());
		db.setString(2, supplier.getBranch());
		db.setDate(3, supplier.getContract_start() == null ? new Date() : supplier.getContract_start());
			
		if (supplier.getContract_end() == null)
			db.setNullDate(4);
		else db.setDate(4, supplier.getContract_end());

		db.setInt(5, supplier.getCompany().getId());
		
		return db.executeUpdate() > 0;	
	}

	@Override
	public boolean update(Supplier supplier) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE suppliers "
				+ " SET name = ?, "
				+ " cnpj = ?, "
				+ " branch = ?, "
				+ " contract_start = ?, "
				+ " contract_end = ?, "
				+ " company_id = ? "
				+ " WHERE id = ?; "; 
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, supplier.getName());
		db.setString(2, supplier.getCnpj());
		db.setString(2, supplier.getBranch());
		
		db.setDate(3, supplier.getContract_start() == null ? new Date() : supplier.getContract_start());
		
		if (supplier.getContract_end() == null)
			db.setNullDate(4);
		else db.setDate(4, supplier.getContract_end());
		
		db.setInt(5, supplier.getCompany().getId());
		db.setInt(6, supplier.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Supplier supplier) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM suppliers "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, supplier.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Supplier> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<Supplier> suppliers = new ArrayList<Supplier>();
			
		// Declara uma instrução SQL
		String sqlQuery = " SELECT c.id as 'supplier_id', c.*, u.* \n"
				+ " FROM suppliers c \n"
				+ " INNER JOIN companies u \n"
				+ " ON c.company_id = u.id;";
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Company company = new Company(db.getInt("company_id"));
			company.setName(db.getString("nome"));
			company.setRole(db.getString("cargo"));
			
			
			Supplier supplier = new Supplier(db.getInt("supplier_id"));
			supplier.setName(db.getString("name"));
			supplier.setCnpj(db.getString("cnpj"));
			supplier.setBranch(db.getString("branch"));
			supplier.setContract_start(db.getDate("contrac_start"));
			supplier.setContract_end(db.getDate("contrac_end"));
			supplier.setCompany(company);
			
			suppliers.add(supplier);
		}
		
		return suppliers;
	}

	@Override
	public Supplier findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "SELECT * FROM suppliers WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Supplier s = null;
		while (db.next()) {
			s = new Supplier(id);
			s.setName(db.getString("name"));
			s.setCnpj(db.getString("cnpj"));
			s.setBranch(db.getString("branch"));
			s.setContract_start(db.getDate("start"));
			s.setContract_end(db.getDate("end"));
			
			CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class); 
			Company company = companyDAO.findById(db.getInt("company_id"));
			s.setCompany(company);
			
			break;
		}
		
		return s;
	}
}
