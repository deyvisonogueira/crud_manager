package model.dao;

import java.util.List;

import model.ModelException;
import model.Supplier;

public interface SupplierDAO {
	boolean save(Supplier supplier) throws ModelException;
	boolean update(Supplier supplier) throws ModelException;
	boolean delete(Supplier supplier) throws ModelException;
	List<Supplier> listAll() throws ModelException;
	Supplier findById(int id) throws ModelException;
}
