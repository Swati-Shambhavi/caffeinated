package com.caffeinated.productcraftsmanservice.queue;

import com.caffeinated.productcraftsmanservice.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class ProductEventListener{
    @Autowired
    IProductService productService;
        @RabbitListener(queues = QueueConfig.QUEUE)
        public void listener(ProductStockUpdateMessage message) {
           log.info("message received {}", kv("message",message));
            productService.updateProductStock(message);
        }
}
