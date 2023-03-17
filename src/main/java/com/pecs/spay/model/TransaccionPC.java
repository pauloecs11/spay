package com.pecs.spay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="transaccionesPC")
public class TransaccionPC{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String token;
	private String vci;
	private double amount;
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String buyOrder;
	private String sessionID;
	private String cardNumber;
	private String acountingDate;
	private String transactionDate;
	private String authorizationCode;
	private String paymentTypeCode;
	private byte responseCode;
	private double installmentsAmount;
	private byte installmentsNumber;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getVci() {
		return vci;
	}
	public void setVci(String vci) {
		this.vci = vci;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getBuyOrder() {
		return buyOrder;
	}
	public void setBuyOrder(String buyOrder) {
		this.buyOrder = buyOrder;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getAcountingDate() {
		return acountingDate;
	}
	public void setAcountingDate(String acountingDate) {
		this.acountingDate = acountingDate;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getPaymentTypeCode() {
		return paymentTypeCode;
	}
	public void setPaymentTypeCode(String paymentTypeCode) {
		this.paymentTypeCode = paymentTypeCode;
	}
	public byte getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(byte responseCode) {
		this.responseCode = responseCode;
	}
	public double getInstallmentsAmount() {
		return installmentsAmount;
	}
	public void setInstallmentsAmount(double installmentsAmount) {
		this.installmentsAmount = installmentsAmount;
	}
	public byte getInstallmentsNumber() {
		return installmentsNumber;
	}
	public void setInstallmentsNumber(byte installmentsNumber) {
		this.installmentsNumber = installmentsNumber;
	}
	
	public TransaccionPC() {
		
	}
	
	
	
	
	
	public TransaccionPC(Integer id, String token, String vci, double amount, String status, String buyOrder,
			String sessionID, String cardNumber, String acountingDate, String transactionDate, String authorizationCode,
			String paymentTypeCode, byte responseCode, double installmentsAmount, byte installmentsNumber) {
		super();
		this.id = id;
		this.token = token;
		this.vci = vci;
		this.amount = amount;
		this.status = status;
		this.buyOrder = buyOrder;
		this.sessionID = sessionID;
		this.cardNumber = cardNumber;
		this.acountingDate = acountingDate;
		this.transactionDate = transactionDate;
		this.authorizationCode = authorizationCode;
		this.paymentTypeCode = paymentTypeCode;
		this.responseCode = responseCode;
		this.installmentsAmount = installmentsAmount;
		this.installmentsNumber = installmentsNumber;
	}
	@Override
	public String toString() {
		return "TransaccionPC [id=" + id + ", token=" + token + ", vci=" + vci + ", amount=" + amount + ", status="
				+ status + ", buyOrder=" + buyOrder + ", sessionID=" + sessionID + ", cardNumber=" + cardNumber
				+ ", acountingDate=" + acountingDate + ", transactionDate=" + transactionDate + ", authorizationCode="
				+ authorizationCode + ", paymentTypeCode=" + paymentTypeCode + ", responseCode=" + responseCode
				+ ", installmentsAmount=" + installmentsAmount + ", installmentsNumber=" + installmentsNumber + "]";
	}
	
	
	
	
	
	
	
	
	
}
