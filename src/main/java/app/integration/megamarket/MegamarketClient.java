package app.integration.megamarket;

import app.integration.megamarket.dto.AuthDto;
import app.integration.megamarket.dto.ParseUrlDto;
import app.integration.megamarket.dto.alarmScreen.AlarmScreenDto;
import app.integration.megamarket.dto.alarmScreen.AuthAlarmDto;
import app.mapper.MegamarketMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class MegamarketClient
{
    private RestOperations client;

    public JsonNode parseUrl(String url)
    {
        JsonMapper mapper = new JsonMapper();
        int[] emptyArr = {};
        AuthDto authParseDto = new AuthDto(
                "50",
                "WEB",
                0,
                "UNKNOWN_OS"

        );
        ParseUrlDto parseUrlDto = new ParseUrlDto(url, authParseDto);
        try
        {
            ResponseEntity<String> response ;
            do
            {
                response =  client.postForEntity("/api/mobile/v1/urlService/url/parse",
                        parseUrlDto,
                        String.class
                );

                if( response.getStatusCode().is3xxRedirection() )
                {
                    response =  client.postForEntity("/api/mobile/v1/urlService/url/parse",
                            parseUrlDto,
                            String.class
                    );
                }
            }
            while
            (
                    response.getStatusCode().is3xxRedirection()
            );

            return mapper.readTree(response.getBody());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private boolean isVpnError(JsonNode json)
    {
        return MegamarketMapper.errorCode(json) == 7;
    }

    public void alarmScreen()
    {
        AlarmScreenDto alarmDto = new AlarmScreenDto(
                new AuthAlarmDto(
                        "Web",
                        "alarm"
                )
        );
        try
        {
            client.postForObject("/api/mobile/v1/cxappadmin/alarmScreen/get",
                    alarmDto,
                    JsonNode.class
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
