package com.demo.kmd.models;

import java.util.List;

import com.plaid.client.response.TransactionsGetResponse.Transaction.Location;
import com.plaid.client.response.TransactionsGetResponse.Transaction.PaymentMeta;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_info")

public  class TransactionInfo {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genId;                                 //generated id locally in database .
    
    private String accountId;
    private Double amount;
    private String isoCurrencyCode;
    private String unofficialCurrencyCode;
    
    //private List<String> category;
    
    private String categoryId;
    private String date;
   // private Location location;
    private String name;
    private String originalDescription;
  //  private PaymentMeta paymentMeta;
    private Boolean pending;
    private String pendingTransactionId;
    private String transactionId;
    private String transactionType;
    private String accountOwner;

    public String getTransactionId() {
      return transactionId;
    }

    public String getAccountId() {
      return accountId;
    }

    public Boolean getPending() {
      return pending;
    }

    public String getPendingTransactionId() {
      return pendingTransactionId;
    }

    public String getTransactionType() {
      return transactionType;
    }

//    public PaymentMeta getPaymentMeta() {
//      return paymentMeta;
//    }

    public String getDate() {
      return date;
    }

    public String getName() {
      return name;
    }

    public Double getAmount() {
      return amount;
    }

    public String getIsoCurrencyCode() {
      return isoCurrencyCode;
    }

    public String getUnofficialCurrencyCode() {
      return unofficialCurrencyCode;
    }

//    public List<String> getCategory() {
//      return category;
//    }

    public String getCategoryId() {
      return categoryId;
    }

//    public Location getLocation() {
//      return location;
//    }

    public String getOriginalDescription() {
      return originalDescription;
    }

    public String getAccountOwner() {
      return accountOwner;
    }

	public Long  getGenId() {
		return genId;
	}

	public void setGenId(Long  genId) {
		this.genId = genId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}

	public void setUnofficialCurrencyCode(String unofficialCurrencyCode) {
		this.unofficialCurrencyCode = unofficialCurrencyCode;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOriginalDescription(String originalDescription) {
		this.originalDescription = originalDescription;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}

	public void setPendingTransactionId(String pendingTransactionId) {
		this.pendingTransactionId = pendingTransactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}
    
}