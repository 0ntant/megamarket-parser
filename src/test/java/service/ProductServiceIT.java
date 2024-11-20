package service;


import app.Main;
import app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Main.class)
public class ProductServiceIT
{
    @Autowired
    ProductService productServ;

//    @Test
//    void productGetInfo_19827()
//    {
//        //given
//        String goodsId = "100030319618_198273";
//
//        //then
//        MegaProduct product = productServ.getProductFullInfo(goodsId);
//
//        //expected
//        assertEquals("Смарт-часы Huawei GT 3 Pro ODN-B19 Light Titanium / Gray Leather",product.getName());
//        assertEquals("https://megamarket.ru/catalog/details/smart-chasy-huawei-gt-3-pro-ond-b19-light-titanium-gray-leather-100030319618/",product.getUrl());
//        assertEquals(BigDecimal.valueOf(29999), product.getPrice());
//        assertEquals(BigDecimal.valueOf(22888), product.getSalePrice());
//        assertEquals(BigDecimal.valueOf(458), product.getSberPrimeAmount());
//        assertEquals(BigDecimal.valueOf(2062),product.getSberThnxAmount());
//        assertEquals(9.0, product.getSberThnxPercent());
//    }
//
//    @Test
//    void productsMainInfo_collectionId_14193()
//    {
//        //given
//        String collectionId = "14193";
//
//        //then
//        List<MegaProduct> productsMainInfo = productServ.getProductsMainInfo(collectionId);
//
//        //expected
//        assertEquals(44, productsMainInfo.size());
//        for (MegaProduct product : productsMainInfo)
//        {
//            assertNotNull(product.getName());
//            assertNotNull(product.getUrl());
//
//            assertNull(product.getPrice());
//            assertNull(product.getSalePrice());
//            assertNull(product.getSberPrimeAmount());
//            assertNull(product.getSberThnxAmount());
//            assertNull(product.getSberThnxPercent());
//        }
//    }
}
