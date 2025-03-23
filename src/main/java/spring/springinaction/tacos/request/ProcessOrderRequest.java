package spring.springinaction.tacos.request;

import spring.springinaction.tacos.domain.model.Order;

public record ProcessOrderRequest(
        String deliveryName,
        String deliveryStreet,
        String deliveryCity,
        String deliveryState,
        String deliveryZip,
        String ccNumber,
        String ccExpiration,
        String ccCVV
) {
    public Order toOrder() {
        Order order = new Order();
        order.setDeliveryName(deliveryName);
        order.setDeliveryStreet(deliveryStreet);
        order.setDeliveryCity(deliveryCity);
        order.setDeliveryState(deliveryState);
        order.setDeliveryZip(deliveryZip);
        order.setCcNumber(ccNumber);
        order.setCcExpiration(ccExpiration);
        order.setCcCVV(ccCVV);
        return order;
    }
}
