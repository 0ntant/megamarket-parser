package app.integration.megamarket;

import app.exception.InvalidTLSHandshake;
import app.integration.megamarket.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import okhttp3.*;
import org.bouncycastle.tls.TlsException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class MegamarketClientOk
{
    OkHttpClient client;
    Request.Builder requestTemplate;
    JsonMapper mapper;

    public JsonNode parseUrl(String url)
    {
        RequestBody requestBody = RequestBody.create(
                getParseUrlDto(url),
                MediaType.parse("application/json")
        );

        Request request = requestTemplate
                .post(requestBody)
                .url("https://megamarket.ru/api/mobile/v1/urlService/url/parse")
                .build();

        return executeRequest(request);
    }

    public JsonNode catalogMenu(String parentId)
    {
        RequestBody requestBody = RequestBody.create(
                getMenuDto(parentId),
                MediaType.parse("application/json")
        );

        Request request = requestTemplate
                .post(requestBody)
                .url("https://megamarket.ru/api/mobile/v1/catalogService/catalog/menu")
                .build();

        return executeRequest(request);
    }

    public JsonNode productOffersGet(String goodsId)
    {
        RequestBody requestBody = RequestBody.create(
                getProductOffersDto(goodsId),
                MediaType.parse("application/json")
        );

        Request request = requestTemplate
                .post(requestBody)
                .url("https://megamarket.ru/api/mobile/v1/catalogService/productOffers/get")
                .build();

        return executeRequest(request);
    }

    public JsonNode productCardMainInfoGet(String goodsId)
    {
        RequestBody requestBody = RequestBody.create(
                getProductCardMainInfoDto(goodsId),
                MediaType.parse("application/json")
        );

        Request request = requestTemplate
                .post(requestBody)
                .url("https://megamarket.ru/api/mobile/v1/catalogService/productCardMainInfo/get")
                .build();

        return executeRequest(request);
    }

    public JsonNode catalogSearch(String collectionId,
                                  Integer limit,
                                  Integer offset)
    {
        RequestBody requestBody = RequestBody.create(
                getCatalogSearchDto(collectionId,
                        limit,
                        offset
                ),
                MediaType.parse("application/json")
        );

        Request request = requestTemplate
                .post(requestBody)
                .url("https://megamarket.ru/api/mobile/v1/catalogService/catalog/search")
                .build();

        return executeRequest(request);
    }

    public JsonNode catalogSearch(String collectionId)
    {
        RequestBody requestBody = RequestBody.create(
                getCatalogSearchDto(collectionId),
                MediaType.parse("application/json")
        );

        Request request = requestTemplate
                .post(requestBody)
                .url("https://megamarket.ru/api/mobile/v1/catalogService/catalog/search")
                .build();

        return executeRequest(request);
    }

    private JsonNode executeRequest(Request request)
    {
        try (Response response = client.newCall(request).execute())
        {
            String responseBody = response.body().string();
            return mapper.readTree(responseBody);
        }
        catch (TlsException exception)
        {
            throw new InvalidTLSHandshake(exception.getMessage());
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private String getCatalogSearchDto(String collectionId,
                                       Integer limit,
                                       Integer offset)
    {
        try
        {
            CatalogSearchDto dto = new CatalogSearchDto(collectionId,
                    limit,
                    offset
            );
            return mapper.writeValueAsString(dto);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private String getCatalogSearchDto(String collectionId)
    {
        try
        {
            CatalogSearchDto dto = new CatalogSearchDto(collectionId,44,0);
            return mapper.writeValueAsString(dto);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private String getProductCardMainInfoDto(String goodsId)
    {
        try
        {
            ProductCardMainInfoDto dto = new  ProductCardMainInfoDto(goodsId);
            return mapper.writeValueAsString(dto);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private String getProductOffersDto(String goodsId)
    {
        try
        {
            ProductOffersDto dto = new ProductOffersDto(goodsId);
            return mapper.writeValueAsString(dto);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private String getMenuDto(String parentId)
    {
        try
        {
            MenuDto dto = new MenuDto(parentId);
            return mapper.writeValueAsString(dto);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private String getParseUrlDto(String url)
    {
        try
        {
            ParseUrlDto dto = new ParseUrlDto(url);
            return mapper.writeValueAsString(dto);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
