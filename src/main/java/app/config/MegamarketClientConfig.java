package app.config;

import app.integration.megamarket.MegamarketClient;
import app.logging.LoggingRestClientInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MegamarketClientConfig
{
    @Value("${megamarket.cookie}")
    String cookie;

    @Value("${megamarket.user-agent}")
    String userAgent;

    @Bean
    MegamarketClient megamarketClient()
    {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .additionalInterceptors(new LoggingRestClientInterceptor())
                .defaultHeader("Cookie", cookie)
                .defaultHeader("User-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Safari/605.1.15")
                .defaultHeader("accept", "application/json")
                .defaultHeader("sec-fetch-site", "same-origin")
                .defaultHeader("sec-fetch-mode", "cors")
                .defaultHeader("Sec-Fetch-User", "?1")
                .defaultHeader("sec-fetch-dest", "empty")
                .defaultHeader("accept-language", "en")
                .defaultHeader("authority", "megamarket.ru")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("origin", "https://megamarket.ru")
                .defaultHeader("referer", "https://megamarket.ru/")
                .defaultHeader("x-requested-with", "XMLHttpRequest")
                .rootUri("https://megamarket.ru")
                .build();
        return MegamarketClient.builder()
                .client(restTemplate)
                .build();
    }
}
