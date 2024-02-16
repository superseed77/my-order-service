package org.formation.service;

import java.time.Duration;

import org.formation.client.ProductClient;
import org.formation.client.UserClient;
import org.formation.dto.PurchaseOrderRequestDto;
import org.formation.dto.PurchaseOrderResponseDto;
import org.formation.dto.RequestContext;
import org.formation.repository.PurchaseOrderRepository;
import org.formation.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@Service
public class OrderFulfillmentService {

	private PurchaseOrderRepository orderRepository;

	private ProductClient productClient;

	private UserClient userClient;

	public OrderFulfillmentService(PurchaseOrderRepository orderRepository, ProductClient productClient,
			UserClient userClient) {
		this.orderRepository = orderRepository;
		this.productClient = productClient;
		this.userClient = userClient;
	}
	
	
	public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
		//On crée un requestContext vide
		return requestDtoMono.map(RequestContext::new)
				// que l'on pqsse a la méthode privée
				// qui lance la requete 
				.flatMap(this::productRequestResponse)
				//ici on a le product et la request
				.doOnNext(EntityDtoUtil::setTransactionRequestDto)
				// on envoie la request sur le service user
				// pour voir si le montant est ok
				.flatMap(this::userRequestResponse)
				//on a la reponse sur la transaction
				// et setTransactionResponseDto nous nous le status 
				// COMPLETED  ou  FAILED
				// le requestCOntext a ete bien utile pour faire 
				//le lien entre les 2 requetes
				.map(EntityDtoUtil::getPurchaseOrder)
				//sauvegardons en base
				.map(this.orderRepository::save) // ici c'est bloquant ! (JPA)
				.map(EntityDtoUtil::getPurchaseOrderResponseDto)
				// on execute cela avec un pool de threat elastique 
				// mais limité: n'affectera pas l'aplli même si bloqué
				//sinon la thread d'evenement sera bloqué par JPA!
				.subscribeOn(Schedulers.boundedElastic());
				
				
	}

	private Mono<RequestContext> productRequestResponse(RequestContext rc) {
		return this.productClient.getProductById(rc.getPurchaseOrderRequestDto()
				.getProductId())
				.doOnNext(rc::setProductDto)
				
				.thenReturn(rc);
	}

	
	private Mono<RequestContext> userRequestResponse(RequestContext rc) {
		return this.userClient.authorizeTransaction(rc.getTransactionRequestDto())
				.doOnNext(rc::setTransactionResponseDto)
				.thenReturn(rc);
	}
	
//	public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
//		//On crée un requestContext vide
//		return requestDtoMono.map(RequestContext::new)
//				// que l'on pqsse a la méthode privée
//				// qui lance la requete 
//				.flatMap(this::productRequestResponse)
//				
//				.doOnNext(EntityDtoUtil::setTransactionRequestDto)
//				.flatMap(this::userRequestResponse)
//				.map(EntityDtoUtil::getPurchaseOrder)
//				.map(this.orderRepository::save) // ici c'est bloquant ! (JPA)
//				.map(EntityDtoUtil::getPurchaseOrderResponseDto)
//				.subscribeOn(Schedulers.boundedElastic());
//	}
//
//	private Mono<RequestContext> productRequestResponse(RequestContext rc) {
//		return this.productClient.getProductById(rc.getPurchaseOrderRequestDto()
//				.getProductId())
//				.doOnNext(rc::setProductDto)
//				.retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
//				.thenReturn(rc);
//	}


}
