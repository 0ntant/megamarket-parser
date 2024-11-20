package mapper;

import app.entity.MegaProduct;
import app.entity.Page;
import app.mapper.ProductMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MegaProductMapperModule
{
    JsonMapper mapper = new JsonMapper();

    @Test
    void mapPage_total_limit_offset() throws IOException
    {
        //given
        File file = new File("./src/test/resources/catalogSearch");
        //then
        Page page = ProductMapper.pageMap(mapper.readTree(file));

        //expected
        assertEquals(25721,page.getTotal());
        assertEquals(0,page.getOffset());
        assertEquals(44,page.getLimit());
    }

    @Test
    void offerMapProduct_198273_with_sale() throws IOException
    {
        //given
        File file = new File("./src/test/resources/productOffers198273");
        //then

        MegaProduct megaProduct = ProductMapper.offerMap(mapper.readTree(file));
        //expected

        assertEquals(BigDecimal.valueOf(29999), megaProduct.getPrice());
        assertEquals(BigDecimal.valueOf(22888), megaProduct.getSalePrice());
        assertEquals(BigDecimal.valueOf(458), megaProduct.getSberPrimeAmount());
        assertEquals(BigDecimal.valueOf(2062), megaProduct.getSberThnxAmount());
        assertEquals(9.0, megaProduct.getSberThnxPercent());
    }

    @Test
    void offerMapProduct_739_without_sale() throws IOException
    {
        //given
        File file = new File("./src/test/resources/productOffers600013496003_739");

        //then
        MegaProduct megaProduct = ProductMapper.offerMap(mapper.readTree(file));

        //expected
        assertEquals(BigDecimal.valueOf(14999), megaProduct.getPrice());
        assertEquals(BigDecimal.valueOf(14999), megaProduct.getSalePrice());
        assertEquals(BigDecimal.valueOf(300), megaProduct.getSberPrimeAmount());
        assertEquals(BigDecimal.valueOf(0), megaProduct.getSberThnxAmount());
        assertEquals(0, megaProduct.getSberThnxPercent());
    }


    @Test
    void infoMapProduct_198273() throws IOException
    {
        //given
        File file = new File("./src/test/resources/productCardMainInfo19827");

        //then
        MegaProduct megaProduct = ProductMapper.infoMap(mapper.readTree(file));

        //expected
        assertEquals("Смарт-часы Huawei GT 3 Pro ODN-B19 Light Titanium / Gray Leather", megaProduct.getName());
        assertEquals("https://megamarket.ru/catalog/details/smart-chasy-huawei-gt-3-pro-ond-b19-light-titanium-gray-leather-100030319618/", megaProduct.getUrl());
    }

    @Test
    void infoMapProduct_739() throws IOException
    {
        File file = new File("./src/test/resources/productCardMainInfo600013496003_739");

        //then
        MegaProduct megaProduct = ProductMapper.infoMap(mapper.readTree(file));

        //expected
        assertEquals("Смарт-часы Huawei Watch GT 4 серебристый/коричневый", megaProduct.getName());
        assertEquals("https://megamarket.ru/catalog/details/smart-chasy-huawei-watch-gt-4-serebristyy-korichnevyy-600013496003/", megaProduct.getUrl());

    }

    @Test
    void infoMapProductsList_catalogSearch_collectionId() throws IOException
    {
        //given
        File file = new File("./src/test/resources/catalogSearch");

        //then
        List<MegaProduct> megaProducts = ProductMapper.infoMapList(mapper.readTree(file));

        //expected
        assertEquals(44, megaProducts.size());
        for(MegaProduct megaProduct : megaProducts)
        {
            assertNotNull(megaProduct.getUrl());
            assertFalse(megaProduct.getUrl().isEmpty());

            assertNotNull(megaProduct.getName());
            assertFalse(megaProduct.getName().isEmpty());
        }
    }

    @Test
    void infoMapTotalProducts_return25752() throws IOException
    {
        //given
        File file = new File("./src/test/resources/catalogSearch");

        //then
        String totalProducts = ProductMapper.totalCatalogSearch(mapper.readTree(file));
        int total = Integer.parseInt(totalProducts);

        //expected
        assertEquals(25721, total);
    }
}
