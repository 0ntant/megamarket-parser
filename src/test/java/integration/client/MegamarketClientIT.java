package integration.client;

import app.Main;
import app.integration.megamarket.MegamarketClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Main.class)
public class MegamarketClientIT
{
    @Autowired
    MegamarketClient client;

    @Test
    void parseUrl() throws InterruptedException {
        for (int i = 0; i < 10; i++)
        {
            try
            {
                System.out.printf("try number %d%n", i);
                System.out.println(client.parseUrl("/catalog/posuda-dlya-prigotovleniya-pishi/"));
            }catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                synchronized (Thread.currentThread())
                {
                    Thread.currentThread().wait(10000);
                }
            }
        }
    }

}
