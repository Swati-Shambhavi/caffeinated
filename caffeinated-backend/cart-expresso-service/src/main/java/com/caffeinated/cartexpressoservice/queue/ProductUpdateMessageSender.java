package com.caffeinated.cartexpressoservice.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdateMessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendProductUpdateMessage(ProductUpdateMessage updateMessage) {
        rabbitTemplate.convertAndSend(QueueConfig.EXCHANGE, QueueConfig.ROUTING_KEY, updateMessage);
    }
}
