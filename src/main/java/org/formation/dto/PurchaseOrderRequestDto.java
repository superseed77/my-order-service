package org.formation.dto;

public class PurchaseOrderRequestDto {

	private Integer userId;
	private String productId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "PurchaseOrderRequestDto [userId=" + userId + ", productId=" + productId + "]";
	}

}
