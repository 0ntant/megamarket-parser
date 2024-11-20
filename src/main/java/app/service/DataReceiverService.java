package app.service;

import app.entity.Category;
import app.entity.MegaProduct;
import app.entity.Page;
import app.integration.dataReceiver.ProductPublisher;
import app.mapper.MegamarketMapper;
import app.mapper.ProductMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataReceiverService
{
    final MegamarketService megamarketServ;
    final ProductService productServ;
    final CategoryService categoryServ;
    final ProductPublisher publisher;

    @Async
    public void sendCategoryInfoToReceiver(String url)
    {
        String collectionId = MegamarketMapper.collectionId(url);
        Category category = getCategoryFullInfo(collectionId);
        sendCategory(category);
    }

    @Async
    public void sendProductInfoToReceiver(String productUrl)
    {
        String goodsId = MegamarketMapper.goodsId(productUrl);
        MegaProduct megaProduct = getProductFullInfo(goodsId);
        sendProduct(megaProduct);
    }

    public Category getCategoryFullInfo(String url)
    {
        Category category = getCategory(url);
        category.setSubCategories(getSubCategories(category.getParentId()));
        JsonNode categoryJson = getProductsMainInfoJson(category.getCollectionId());
        Page productFirstPage = productServ.getPage(categoryJson);

        log.info("Category url: {} SubCategories: {} Products total: {}",
                category.getUrl(),
                category.getSubCategories().size(),
                productFirstPage.getTotal()
        );

        List<MegaProduct> productsFullInfo = getAllCategoryProductsFullInfo(
                category,
                productFirstPage
        );

        log.info("Get full subcategory info category url: {}",
                category.getUrl()
        );
        for(Category subCategory : category.getSubCategories())
        {
            getCategoryFullInfo(subCategory.getUrl());
        }

        category.setMegaProducts(productsFullInfo);
        return category;
    }

    public List<MegaProduct> getAllCategoryProductsFullInfo(Category category,
                                                            Page productFirstPage)
    {
        List<MegaProduct> productsFullInfo = new ArrayList<>();
        int maxOffset = productFirstPage.getTotal() > 44 ? 44 : productFirstPage.getTotal();
        for(int i = 0; i < productFirstPage.getTotal() / maxOffset; i++)
        {
            Page productOffsetPage = new Page(
                    productFirstPage.getLimit(),
                    i * maxOffset,
                    maxOffset
            );
            for(MegaProduct megaProduct : getProductsMainInfo(category.getCollectionId(), productOffsetPage))
            {
                productsFullInfo.add(getProductFullInfo(megaProduct));
            }
        }
        return productsFullInfo;
    }

    private void sendCategory(Category category)
    {
        List<MegaProduct> megaProducts = category.getMegaProducts();

        for(MegaProduct megaProduct : megaProducts)
        {
            sendProduct(megaProduct);
        }

        for(Category subCategory: category.getSubCategories())
        {
            sendCategory(subCategory);
        }
    }

    private void sendProduct(MegaProduct megaProduct)
    {
        publisher.sendProductToReceiver(ProductMapper.map(megaProduct));
    }

    public Category getCategory(String url)
    {
        return categoryServ.getCategory(
                megamarketServ.parseUrl(url),
                url
        );
    }

    public List<Category> getSubCategories(String parentId)
    {
        return categoryServ.getSubCategories(megamarketServ.categoryMenu(parentId));
    }

    public List<MegaProduct> getProductsMainInfo(String collectionId, Page page)
    {
        log.info("get collectionId: {} page offset: {} page limit: {}",
                collectionId,
                page.getOffset(),
                page.getOffset()
        );
        return productServ
                .getProductsMainInfo(
                        getProductsMainInfoJson(collectionId, page)
                );
    }

    public List<MegaProduct> getProductsMainInfo(String collectionId)
    {
        return productServ
                .getProductsMainInfo(
                        megamarketServ
                                .catalogSearch(
                                        collectionId
                                )
                );
    }

    private JsonNode getProductsMainInfoJson(String collectionId)
    {
        return megamarketServ.catalogSearch(collectionId);
    }

    private JsonNode getProductsMainInfoJson(String collectionId, Page page)
    {
        return megamarketServ.catalogSearch(collectionId, page);
    }

    public MegaProduct getProductFullInfo(String goodsId)
    {
        MegaProduct megaProductMainInfo = getProductMainInfo(goodsId);
        MegaProduct megaProductOfferData = getProductOffer(goodsId);

        return productServ.mergeData(megaProductMainInfo, megaProductOfferData);
    }

    public MegaProduct getProductFullInfo(MegaProduct megaProductMainInfo)
    {
        String goodsId = MegamarketMapper.goodsId(megaProductMainInfo.getUrl());
        return productServ.mergeData(
                megaProductMainInfo,
                getProductOffer(goodsId)
        );
    }

    public MegaProduct getProductOffer(String goodsId)
    {
        return productServ.getOffer(
                megamarketServ.productOffersGet(goodsId)
        );
    }

    private MegaProduct getProductMainInfo(String goodsId)
    {
        return productServ.getMainInfo(
                megamarketServ.productCardMainInfoGet(goodsId)
        );
    }
}
