package model;

import java.util.Date;

public class Supplier {
	private Integer id;
	private String name;
	private String cnpj;
	private String branch;
	private Date contract_start;
	private Date contract_end;
	private Company company;
	
	public Supplier() {}
	
	public Supplier(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getBranch() {
		return branch;
	}
	
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	public Date getContract_start() {
		return contract_start;
	}
	
	public void setContract_start(Date contract_start) {
		this.contract_start = contract_start;
	}
	
	public Date getContract_end() {
		return contract_end;
	}
	
	public void setContract_end(Date contract_end) {
		this.contract_end = contract_end;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
}
