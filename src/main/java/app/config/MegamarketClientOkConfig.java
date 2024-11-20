package app.config;

import app.integration.megamarket.MegamarketClientOk;
import app.logging.LoggingOkInterceptor;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.zhkl0228.impersonator.ImpersonatorApi;
import com.github.zhkl0228.impersonator.ImpersonatorFactory;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClientFactory;
import okhttp3.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class MegamarketClientOkConfig
{
    @Bean
    MegamarketClientOk megamarketClientOk()
    {
        ImpersonatorApi api = ImpersonatorFactory.android();
        api.newSSLContext(null, null);

        OkHttpClientFactory factory = OkHttpClientFactory.create(api);
        OkHttpClient client = factory.newHttpClient();
        OkHttpClient clientWithInterceptor = new OkHttpClient.Builder(client)
                .addInterceptor(new LoggingOkInterceptor())
                .build();

        return MegamarketClientOk.builder()
                .client(clientWithInterceptor)
                .requestTemplate(requestTemplate())
                .mapper(new JsonMapper())
                .build();
    }

    private Request.Builder requestTemplate()
    {
        return new Request.Builder()
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("Sec-Fetch-User", "?1")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("accept-language", "en")
                .addHeader("authority", "megamarket.ru")
                .addHeader("Content-Type", "application/json")
                .addHeader("origin", "https://megamarket.ru")
                .addHeader("referer", "https://megamarket.ru/")
                .addHeader("x-requested-with", "XMLHttpRequest");
    }
}
