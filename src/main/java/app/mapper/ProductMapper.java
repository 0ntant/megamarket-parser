package app.mapper;

import app.entity.MegaProduct;
import app.entity.Page;
import com.fasterxml.jackson.databind.JsonNode;
import integration.dto.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper
{
    public static Product map(MegaProduct megaProduct)
    {
        return new Product(
                megaProduct.getUrl(),
                megaProduct.getName(),
                megaProduct.getSalePrice(),
                megaProduct.getPrice(),
                megaProduct.getSberThnxPercent(),
                megaProduct.getSberThnxAmount(),
                megaProduct.getSberPrimeAmount()
        );
    }

    public static Page pageMap(JsonNode json)
    {
        return Page.builder()
                .total(json.path("total").asInt())
                .offset(json.path("offset").asInt())
                .limit(json.path("limit").asInt())
                .build();
    }

    public static String totalCatalogSearch(JsonNode json)
    {
        return json
                .path("total")
                .asText();
    }

    public static List<MegaProduct> infoMapList(JsonNode json)
    {
        List<MegaProduct> megaProducts = new ArrayList<>();
        json = json.get("items");
        for (JsonNode product : json)
        {
            megaProducts.add(infoMap(product));
        }
        return megaProducts;
    }

    public static MegaProduct infoMap(JsonNode json)
    {
        return MegaProduct.builder()
                .name(title(json))
                .url(webUrl(json))
                .build();
    }

    private static String title(JsonNode json)
    {
        return json
                .path("goods")
                .path("title")
                .asText();
    }

    private static String webUrl(JsonNode json)
    {
        return json
                .path("goods")
                .path("webUrl")
                .asText();
    }

    public static MegaProduct offerMap(JsonNode json)
    {
        json = json.path("offers").get(0);
        MegaProduct megaProduct = new MegaProduct();
        BigDecimal oldPrice = oldPrice(json);

        if (oldPrice.equals(BigDecimal.ZERO))
        {
            megaProduct.setPrice(price(json));
            megaProduct.setSalePrice(finalPrice(json));
        }
        else
        {
            megaProduct.setPrice(oldPrice);
            megaProduct.setSalePrice(finalPrice(json));
        }

        if (!isBonusInfoGroupsEmpty(json))
        {
            megaProduct.setSberThnxPercent(bonusPercent(json));
            megaProduct.setSberThnxAmount(bonusTotalAmount(json));
            megaProduct.setSberPrimeAmount(primeBonusAmount(json));
        }
        return megaProduct;
    }

    private static BigDecimal finalPrice(JsonNode json)
    {
        return BigDecimal.valueOf(json
                .path("finalPrice")
                .asInt()
        );
    }

    private static BigDecimal oldPrice(JsonNode json)
    {
        return BigDecimal.valueOf(json
                .path("oldPrice")
                .asInt()
        );
    }

    private static BigDecimal price(JsonNode json)
    {
        return BigDecimal.valueOf(json
                .path("price")
                .asInt()
        );
    }

    private static Double bonusPercent(
            JsonNode jsonBonusGroup)
    {
        JsonNode bonusInfoItems = jsonBonusGroup.get("bonusInfoGroups");
        for (JsonNode bonusInfoItem : bonusInfoItems)
        {
            if (bonusInfoItem.path("type").asText().equals("PAYMENT_TYPE_BONUS"))
            {
                return bonusInfoItem.path("percent").asDouble();
            }
        }
        return (double) 0;
    }

    private static BigDecimal bonusTotalAmount(
            JsonNode jsonBonusGroup)
    {
        JsonNode bonusInfoItems = jsonBonusGroup.get("bonusInfoGroups");
        for (JsonNode bonusInfoItem : bonusInfoItems)
        {
            if (bonusInfoItem.path("type").asText().equals("PAYMENT_TYPE_BONUS"))
            {
                return BigDecimal.valueOf(
                        bonusInfoItem.path("totalAmount").asInt()
                );
            }
        }
        return BigDecimal.ZERO;
    }

    private static boolean isBonusInfoGroupsEmpty(JsonNode json)
    {
        return json.path("bonusInfoGroups").isEmpty();
    }

    private static BigDecimal primeBonusAmount(JsonNode jsonBonusGroup)
    {
        JsonNode bonusInfoItems = jsonBonusGroup.get("bonusInfoGroups");
        for (JsonNode bonusInfoItem : bonusInfoItems)
        {
            if (bonusInfoItem.path("type").asText().equals("PRIME_BONUS"))
            {
                return BigDecimal.valueOf(
                        bonusInfoItem.path("totalAmount").asInt()
                );
            }
        }
        return BigDecimal.ZERO;
    }
}
