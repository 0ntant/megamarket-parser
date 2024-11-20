package service;

import app.entity.Category;
import app.entity.MegaProduct;
import app.service.DataReceiverService;
import org.springframework.beans.factory.annotation.Autowired;

import app.Main;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class DataReceiverServiceIT
{
    @Autowired
    DataReceiverService dataReceiverService;


    @Test
    void getCategoryFullInfo()
    {
        //given
        String url = "/catalog/posuda-801/";

        //then
        Category category = dataReceiverService.getCategoryFullInfo(url);

        //expected
        assertEquals("X13626", category.getParentId());
        assertEquals("13626", category.getCollectionId());

        for(Category subCategory : category.getSubCategories())
        {
            assertNotNull(subCategory.getParentId());
            assertFalse(subCategory.getParentId().isEmpty());

            assertNotNull(subCategory.getCollectionId());
            assertFalse(subCategory.getCollectionId().isEmpty());
        }

        for(MegaProduct megaProduct : category.getMegaProducts())
        {
            assertNotNull(megaProduct.getUrl());
            assertFalse(megaProduct.getUrl().isEmpty());

            assertNotNull(megaProduct.getName());
            assertFalse(megaProduct.getName().isEmpty());

            assertNotNull(megaProduct.getSberThnxAmount());

            assertNotNull(megaProduct.getSberThnxPercent());

            assertNotNull(megaProduct.getSberPrimeAmount());

            assertNotNull(megaProduct.getPrice());

            assertNotNull(megaProduct.getSalePrice());
        }
    }

    @Test
    void getProductFullInfo_19827()
    {
        //given
        String goodsId = "100030319618_198273";

        //then
        MegaProduct megaProduct = dataReceiverService.getProductFullInfo(goodsId);

        //expected
        assertEquals("Смарт-часы Huawei GT 3 Pro ODN-B19 Light Titanium / Gray Leather", megaProduct.getName());
        assertEquals("https://megamarket.ru/catalog/details/smart-chasy-huawei-gt-3-pro-ond-b19-light-titanium-gray-leather-100030319618/", megaProduct.getUrl());
        assertEquals(BigDecimal.valueOf(29999), megaProduct.getPrice());
        assertEquals(BigDecimal.valueOf(22888), megaProduct.getSalePrice());
        assertEquals(BigDecimal.valueOf(458), megaProduct.getSberPrimeAmount());
        assertEquals(BigDecimal.valueOf(2062), megaProduct.getSberThnxAmount());
        assertEquals(9.0, megaProduct.getSberThnxPercent());
    }

    @Test
    void productsMainInfo_collectionId_14193()
    {
        //given
        String collectionId = "14193";

        //then
        List<MegaProduct> productsMainInfo = dataReceiverService.getProductsMainInfo(collectionId);

        //expected
        assertEquals(44, productsMainInfo.size());
        for (MegaProduct megaProduct : productsMainInfo)
        {
            assertNotNull(megaProduct.getName());
            assertNotNull(megaProduct.getUrl());

            assertNull(megaProduct.getPrice());
            assertNull(megaProduct.getSalePrice());
            assertNull(megaProduct.getSberPrimeAmount());
            assertNull(megaProduct.getSberThnxAmount());
            assertNull(megaProduct.getSberThnxPercent());
        }
    }


    @Test
    void getCategory_posuda_return13626()
    {
        //given
        String url = "/catalog/posuda-801/";

        //then
        Category category = dataReceiverService.getCategory(url);
        //expected

        assertEquals("X13626", category.getParentId());
        assertEquals("13626", category.getCollectionId());
    }

    @Test
    void getSubCategories_posuda_return83Subcategories()
    {
        //given
        String parentId = "X13636";

        //then
        List<Category> subcategories = dataReceiverService.getSubCategories(parentId);
        //expected

        assertEquals(83, subcategories.size());

    }
}
