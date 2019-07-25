package com.ywl.redis.vo;

public class PhoneVO {
	private Integer id;

	private String name;

	private Double sales;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sales
	 */
	public Double getSales() {
		return sales;
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(Double sales) {
		this.sales = sales;
	}
}
