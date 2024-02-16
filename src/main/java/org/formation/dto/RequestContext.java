package org.formation.dto;

public class RequestContext {

    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;
    

    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }

	public PurchaseOrderRequestDto getPurchaseOrderRequestDto() {
		return purchaseOrderRequestDto;
	}

	public void setPurchaseOrderRequestDto(PurchaseOrderRequestDto purchaseOrderRequestDto) {
		this.purchaseOrderRequestDto = purchaseOrderRequestDto;
	}

	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public TransactionRequestDto getTransactionRequestDto() {
		return transactionRequestDto;
	}

	public void setTransactionRequestDto(TransactionRequestDto transactionRequestDto) {
		this.transactionRequestDto = transactionRequestDto;
	}

	public TransactionResponseDto getTransactionResponseDto() {
		return transactionResponseDto;
	}

	public void setTransactionResponseDto(TransactionResponseDto transactionResponseDto) {
		this.transactionResponseDto = transactionResponseDto;
	}

	@Override
	public String toString() {
		return "RequestContext [purchaseOrderRequestDto=" + purchaseOrderRequestDto + ", productDto=" + productDto
				+ ", transactionRequestDto=" + transactionRequestDto + ", transactionResponseDto="
				+ transactionResponseDto + "]";
	}
    
	
    
}
