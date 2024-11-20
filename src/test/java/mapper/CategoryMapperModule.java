package mapper;

import app.entity.Category;
import app.mapper.CategoryMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryMapperModule
{
    JsonMapper mapper = new JsonMapper();

    @Test
    void mapCategoriesList_returnCategory() throws IOException {

        //given
        File file = new File("./src/test/resources/urlParse");

        //then
        Category category = CategoryMapper.map(mapper.readTree(file),"CategoryURl path");

        //expected
        assertEquals("13626", category.getCollectionId());
        assertEquals("X13626", category.getParentId());
    }

    @Test
    void mapCategoriesList_return83Categories() throws IOException {

        //given
        File file = new File("./src/test/resources/catalogeMenu");

        //then
        List<Category> categoryList = CategoryMapper.mapList(mapper.readTree(file));

        //expected
        assertEquals(83, categoryList.size());
        for (Category category : categoryList)
        {
            assertNotNull(category.getParentId());
            assertFalse(category.getParentId().isEmpty());

            assertNotNull(category.getCollectionId());
            assertFalse(category.getCollectionId().isEmpty());
        }
    }
}
