package mapper;

import app.mapper.MegamarketMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MegamarketMapperModule
{
    @Test
    void extract_goods_ids()
    {
        //given
        String[] urls = {
                "https://someAddress.com/catalog/details/smart-chasy-huawei-gt-3-pro-ond-b19-light-titanium-gray-leather-100030319618/",
                "https://someAddress.com/catalog/details/smart-chasy-huawei-watch-gt-4-serebristyy-korichnevyy-600013496003_123423/"
        };
        //then
        String goodsId1 = MegamarketMapper.goodsId(urls[0]);
        String goodsId2 = MegamarketMapper.goodsId(urls[1]);

        //expected
        assertEquals("100030319618", goodsId1);
        assertEquals("600013496003_123423", goodsId2);
    }

    @Test
    void extract_collection_ids()
    {
        String[] urls = {
                "https://someAddress.ru/catalog/posuda-dlya-prigotovleniya-pishi/",
                "https://someAddress.ru/catalog/catalog/posuda-801/"
        };
        //then
        String collectionId1 = MegamarketMapper.collectionId(urls[0]);
        String collectionId2 = MegamarketMapper.collectionId(urls[1]);

        //expected
        assertEquals("/catalog/posuda-dlya-prigotovleniya-pishi/", collectionId1);
        assertEquals("/catalog/posuda-801/", collectionId2);
    }
}
