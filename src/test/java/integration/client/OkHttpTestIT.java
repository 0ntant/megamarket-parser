package integration.client;

import app.integration.megamarket.dto.alarmScreen.AlarmScreenDto;
import app.integration.megamarket.dto.alarmScreen.AuthAlarmDto;
import app.integration.megamarket.dto.AuthDto;
import app.integration.megamarket.dto.ParseUrlDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.zhkl0228.impersonator.ImpersonatorApi;
import com.github.zhkl0228.impersonator.ImpersonatorFactory;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import java.io.IOException;

public class OkHttpTestIT {

    @Test
    void testRequest() throws JsonProcessingException {
        ImpersonatorApi api = ImpersonatorFactory.android(); //android
        SSLContext context = api.newSSLContext(null, null);


        OkHttpClientFactory factory = OkHttpClientFactory.create(api);
        OkHttpClient client = factory.newHttpClient(); // for TLS/JA3/JA4 fingerprints and HTTP/2 fingerprints impersonation

      //  System.out.println(client.parseUrl("/catalog/posuda-dlya-prigotovleniya-pishi/"));

        AuthDto authParseDto = new AuthDto(
                "50",
                "WEB",
                0,
                "UNKNOWN_OS"

        );

        AlarmScreenDto alarmDto = new AlarmScreenDto(
                new AuthAlarmDto(
                        "WEB",
                        "alarm"
                )
        );
        ParseUrlDto parseUrlDto = new ParseUrlDto("/catalog/posuda-801/", authParseDto);

        JsonMapper jsonMapper = new JsonMapper();
        String jsonBody = jsonMapper.writeValueAsString(parseUrlDto);
        // Создаем тело запроса с типом "application/json"
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        // Строим запрос с POST методом и заголовками
        Request request = new Request.Builder()
                .url("https://megamarket.ru/api/mobile/v1/urlService/url/parse") // Замените на ваш URL
                //.url("https://megamarket.ru/api/mobile/v1/cxappadmin/alarmScreen/get") // Замените на ваш URL
                .post(body)                             // Указываем тело POST-запроса
               // .addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/126.0.6478.108 Mobile/15E148 Safari/604.1")
          //    .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
            //    .addHeader("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:129.0) Gecko/20100101 Firefox/129.0")
           //     .addHeader("cookie", "spid=1728351328611_980b55fa897fc316d0f8db4dbee37721_a23ncrtaegb409ki; device_id=996b90c3-8515-11ef-970a-02d0b1062b7d; sbermegamarket_token=2bece3c4-02b5-4674-85ce-109763487927; ecom_token=2bece3c4-02b5-4674-85ce-109763487927; _sa=SA1.2ff6e022-9a66-45df-bfc1-e1833018b9ce.1728351338; isOldUser=true; adspire_uid=AS.2062851686.1728351340; ssaid=a09288e0-8515-11ef-acb3-0926f2e607e5; uxs_uid=a5c9adc0-8515-11ef-9a5a-bd3f35b6a45e; __zzatw-smm=MDA0dBA=Fz2+aQ==; spsn=1729827434137_7b2276657273696f6e223a22332e332e33222c227369676e223a226335353664383536323565656432363337313437663362633335656161303339222c22706c6174666f726d223a224c696e7578207838365f3634222c2262726f7773657273223a5b226368726f6d65225d2c2273636f7265223a302e377d; spsc=1729827434137_65194e89fa48b2b988f3d6ad7d1e3378_2f6240db0c5e73a1b045514db12d996b; __tld__=null; region_info=%7B%22displayName%22%3A%22%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%B0%D1%8F%20%D0%BE%D0%B1%D0%BB%D0%B0%D1%81%D1%82%D1%8C%22%2C%22kladrId%22%3A%225000000000000%22%2C%22isDeliveryEnabled%22%3Atrue%2C%22geo%22%3A%7B%22lat%22%3A55.755814%2C%22lon%22%3A37.617635%7D%2C%22id%22%3A%2250%22%7D; cfidsw-smm=L0S+2aXBxisZm0GSDo28PIlE5IfVOiJxjWvZmjc/B8qbz98uN3jH8Ls5mtZ3mi0zDuIVvnTEv8kpIW2weBXN7e6O9H1eZL6RJ+4dso1ZvmmnZqh9eU0XBgkzctFVTLwbv7UeRg+qFwmWlbGRAJVGj+/pvZILVT+VpAIWH/yD; cfidsw-smm=L0S+2aXBxisZm0GSDo28PIlE5IfVOiJxjWvZmjc/B8qbz98uN3jH8Ls5mtZ3mi0zDuIVvnTEv8kpIW2weBXN7e6O9H1eZL6RJ+4dso1ZvmmnZqh9eU0XBgkzctFVTLwbv7UeRg+qFwmWlbGRAJVGj+/pvZILVT+VpAIWH/yD; spsc=1729827486355_65db34eef4bde75c1461337a2ea422e7_54cb6ab80f0396d0d9cb839887385807")
//                .addHeader("accept", "application/json")
               // .defaultHeader("accept", "application/json")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("Sec-Fetch-User", "?1")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("accept-language", "en")
                .addHeader("authority", "megamarket.ru")
                .addHeader("Content-Type", "application/json")
                .addHeader("origin", "https://megamarket.ru")
                .addHeader("referer", "https://megamarket.ru/")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .build();

        for (int i = 0 ; i < 10 ; i++)
        {
            System.out.println(jsonBody);
            System.out.println(request.headers());
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                // Выводим тело ответа
                System.out.println(response.body().string());
            } catch (IOException e) {
               System.out.println(e.getMessage());
            }
        }
        // Выполняем запрос

    }
}
