package app.integration.dataReceiver;

import integration.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductPublisher
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.producer.exc}")
    private String receiverServiceExc;

    public void sendProductToReceiver(Product product)
    {
        try
        {
            rabbitTemplate.convertAndSend(
                    receiverServiceExc,
                    product
            );
            log.info("[PRODUCER] send product url={} name={} price={} to receiverService",
                    product.url(),
                    product.name(),
                    product.price()
            );
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
        }
    }
}
