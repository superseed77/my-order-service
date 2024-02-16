package org.formation.controller;

import org.formation.dto.PurchaseOrderRequestDto;
import org.formation.dto.PurchaseOrderResponseDto;
import org.formation.service.OrderFulfillmentService;
import org.formation.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {

	private OrderFulfillmentService orderFulfillmentService;

	private OrderQueryService queryService;

	public PurchaseOrderController(OrderFulfillmentService orderFulfillmentService, OrderQueryService queryService) {
		this.orderFulfillmentService = orderFulfillmentService;
		this.queryService = queryService;
	}

	@PostMapping
	Mono<PurchaseOrderResponseDto> order(
			@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
		return this.orderFulfillmentService.processOrder(requestDtoMono);
	}

	@GetMapping("user/{userId}")
	Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable int userId) {
		return this.queryService.getProductsByUserId(userId);
	}
	
//	@PostMapping
//	Mono<ResponseEntity<PurchaseOrderResponseDto>> order(
//			@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
//		return this.orderFulfillmentService.processOrder(requestDtoMono).map(ResponseEntity::ok)
//				.onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build()).onErrorReturn(
//						WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
//	}

}
