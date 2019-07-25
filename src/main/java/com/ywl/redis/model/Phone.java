package com.ywl.redis.model;

import java.io.Serializable;

public class Phone implements Serializable {

	private Integer id;
	private String name;
	private String ranking;

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
	 * @return the ranking
	 */
	public String getRanking() {
		return ranking;
	}

	/**
	 * @param ranking the ranking to set
	 */
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public Phone(Integer id, String name, String ranking) {
		super();
		this.id = id;
		this.name = name;
		this.ranking = ranking;
	}

	public Phone(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Phone() {
		super();
	}

}
