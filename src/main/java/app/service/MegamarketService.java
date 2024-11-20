package app.service;

import app.entity.Page;
import app.exception.InvalidTLSHandshake;
import app.exception.LimitReachedException;
import app.integration.megamarket.MegamarketClientOk;
import app.mapper.MegamarketMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Service
public class MegamarketService
{
    final MegamarketClientOk client;
    int retryCount = 22;

    public JsonNode parseUrl(String url)
    {
        return executeWithRetry(() -> client.parseUrl(url), url);
    }

    public JsonNode categoryMenu(String parentId)
    {
        return executeWithRetry(() -> client.catalogMenu(parentId), parentId);
    }

    public JsonNode productCardMainInfoGet(String goodsId)
    {
        return executeWithRetry(() -> client.productCardMainInfoGet(goodsId), goodsId);
    }

    public JsonNode productOffersGet(String goodsId)
    {
        return executeWithRetry(() -> client.productOffersGet(goodsId), goodsId);
    }

    public JsonNode catalogSearch(String collectionId, Page page)
    {
        return executeWithRetry(() -> client.catalogSearch(
                collectionId,
                page.getLimit(),
                page.getOffset()), collectionId);
    }

    public JsonNode catalogSearch(String collectionId)
    {
        return executeWithRetry(() -> client.catalogSearch(collectionId), collectionId);
    }

    private JsonNode executeWithRetry(Supplier<JsonNode> request, String requestParam)
    {
        for (int i = 0; i < retryCount; i++)
        {
            try
            {
                JsonNode response = request.get();
                if (isVpnError(response))
                {
                    log.warn("VPN error for requestParam={}", requestParam);
                    continue;
                }
                return response;
            }
            catch (InvalidTLSHandshake exception)
            {
                i--;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
        throw new LimitReachedException(
                "RequestParam=%s retryCount=%d"
                        .formatted(requestParam, retryCount)
        );
    }

    private boolean isVpnError(JsonNode json)
    {
        return MegamarketMapper.errorCode(json) == 7;
    }
}
