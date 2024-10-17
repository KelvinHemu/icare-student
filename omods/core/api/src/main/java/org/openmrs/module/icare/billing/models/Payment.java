package org.openmrs.module.icare.billing.models;

import org.codehaus.jackson.annotate.JsonSetter;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.module.icare.billing.Utils.PaymentStatus;
import org.openmrs.module.icare.core.Item;

import javax.persistence.*;
import java.util.*;

/**
 * BlPayment generated by hbm2java
 */
@Entity
@Table(name = "bl_payment")
public class Payment extends BaseOpenmrsData implements java.io.Serializable {
	
	@Id
	@GeneratedValue()
	@Column(name = "payment_id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_type_id", nullable = false)
	private Concept paymentType;
	
	@Column(name = "reference_number", length = 65535)
	private String referenceNumber;
	
	@Column(name = "received_by", length = 65535)
	private String receivedBy;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id.payment", cascade = { CascadeType.PERSIST })
	private List<PaymentItem> items = new ArrayList<PaymentItem>(0);
	
	@Column(name = "status", nullable = true, length = 16)
	private PaymentStatus status;
	
	public Payment() {
		
	}
	
	public Invoice getInvoice() {
		return this.invoice;
	}
	
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public String getReferenceNumber() {
		return this.referenceNumber;
	}
	
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	
	public String getReceivedBy() {
		return this.receivedBy;
	}
	
	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}
	
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonSetter("invoice")
	public void setInvoiceJSON(Map<String, Object> invoiceMap) {
		Invoice invoice = new Invoice();
		invoice.setUuid((String) invoiceMap.get("uuid"));
		this.setInvoice(invoice);
	}
	
	@JsonSetter("paymentType")
	public void setPaymentType(Map<String, Object> paymentTypeMap) {
		Concept paymentType = new Concept();
		paymentType.setUuid((String) paymentTypeMap.get("uuid"));
		this.setPaymentType(paymentType);
	}
	
	@JsonSetter("items")
	public void setDiscountItems(List<Map<String, Object>> items) {
		List<PaymentItem> newItems = new ArrayList<PaymentItem>();
		for (Map<String, Object> item : items) {
			PaymentItem i = new PaymentItem();
			Item itemObj = new Item();
			itemObj.setUuid((String) ((Map<String, Object>) item.get("item")).get("uuid"));
			i.setItem(itemObj);
			
			Order order = new Order();
			order.setUuid((String) ((Map<String, Object>) item.get("order")).get("uuid"));
			i.setOrder(order);
			
			if (item.get("amount") instanceof Integer) {
				i.setAmount(Double.valueOf((Integer) item.get("amount")));
			} else if (item.get("amount") instanceof Double) {
				i.setAmount((Double) item.get("amount"));
			} else if (item.get("amount") instanceof String) {
				i.setAmount(Double.valueOf((String) item.get("amount")));
			}
			
			newItems.add(i);
		}
		this.setItems(newItems);
	}
	
	public Concept getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(Concept paymentType) {
		this.paymentType = paymentType;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> paymentMap = new HashMap<String, Object>();
		paymentMap.put("uuid", this.getUuid());
		paymentMap.put("referenceNumber", this.getReferenceNumber());
		
		Map<String, Object> creatorObject = new HashMap<String, Object>();
		
		if (this.getCreator() != null) {
			creatorObject.put("uuid", this.getCreator().getUuid());
			creatorObject.put("display", this.getCreator().getDisplayString());
		}
		
		paymentMap.put("creator", creatorObject);
		
		paymentMap.put("created", this.getDateCreated());
		
		paymentMap.put("voided", this.getVoided());
		
		/*Map<String, Object> invoiceMap = new HashMap<String, Object>();
		invoiceMap.put("uuid", this.getInvoice().getUuid());
//		paymentMap.put("invoice", invoiceMap);*/
		paymentMap.put("receivedBy", this.getReceivedBy());
		Map<String, Object> paymentTypeMap = new HashMap<String, Object>();
		paymentTypeMap.put("uuid", this.getPaymentType().getUuid());
		paymentTypeMap.put("name", this.getPaymentType().getDisplayString());
		paymentMap.put("paymentType", paymentTypeMap);
		
		List<Map<String, Object>> invoiceItems = new ArrayList<Map<String, Object>>();
		for (PaymentItem paymentItem : this.getItems()) {
			invoiceItems.add(paymentItem.getMap());
		}
		paymentMap.put("items", invoiceItems);
        
		Map<String,Object> visitMap = new HashMap<>();
		visitMap.put("uuid",this.getInvoice().getVisit().getUuid());
		paymentMap.put("visit",visitMap);

		return paymentMap;
	}
	
	public List<PaymentItem> getItems() {
		return items;
	}
	
	public void setItems(List<PaymentItem> items) {
		this.items = items;
	}
	
	public PaymentStatus getStatus() {
		return status;
	}
	
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
}
