package com.demo.kmd.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plaid_auth_data")
public class PlaidAuthData {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genId;                  //generated id locally in database .
    private String stUserid;
	
	
	private String plaidItemId;
	private String plaidAccessToken ; //this is needed to access data rom p[lai d 
	private String plaidPublicToken ;
	
	
	public PlaidAuthData() {}
	
	public PlaidAuthData(String stUserid, String plaidItemId, String plaidAccessToken, String plaidPublicToken){
		this.stUserid= stUserid;
		this.plaidItemId = plaidItemId;
		this.plaidAccessToken = plaidAccessToken;		
		this.plaidPublicToken = plaidPublicToken;
	}
	
	
	public Long getGenId() {
		return genId;
	}
	public void setGenId(Long genId) {
		this.genId = genId;
	}
	public String getStUserid() {
		return stUserid;
	}
	public void setStUserid(String stUserid) {
		this.stUserid = stUserid;
	}
	public String getPlaidItemId() {
		return plaidItemId;
	}
	public void setPlaidItemId(String plaidItemId) {
		this.plaidItemId = plaidItemId;
	}
	public String getPlaidAccessToken() {
		return plaidAccessToken;
	}
	public void setPlaidAccessToken(String plaidAccessToken) {
		this.plaidAccessToken = plaidAccessToken;
	}
	public String getPlaidPublicToken() {
		return plaidPublicToken;
	}
	public void setPlaidPublicToken(String plaidPublicToken) {
		this.plaidPublicToken = plaidPublicToken;
	}
}
