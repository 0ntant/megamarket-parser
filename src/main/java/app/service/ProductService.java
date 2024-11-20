package app.service;

import app.entity.MegaProduct;
import app.entity.Page;
import app.mapper.ProductMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService
{
    public Page getPage(JsonNode json)
    {
        return ProductMapper.pageMap(json);
    }

    public List<MegaProduct> getProductsMainInfo(JsonNode json)
    {
        return ProductMapper.infoMapList(json);
    }

    public MegaProduct getOffer(JsonNode json)
    {
        return ProductMapper.offerMap(json);
    }

    public MegaProduct getMainInfo(JsonNode json)
    {
        return ProductMapper.infoMap(json);
    }

    public MegaProduct mergeData(MegaProduct megaProductMainInfo,
                          MegaProduct megaProductOfferData)
    {
        megaProductOfferData.setName(megaProductMainInfo.getName());
        megaProductOfferData.setUrl(megaProductMainInfo.getUrl());

        return megaProductOfferData;
    }
}
