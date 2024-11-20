package service;

import app.Main;
import app.entity.Category;
import app.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class CategoryServiceIT
{

    @Autowired
    CategoryService categoryService;

//    @Test
//    void getCategory_posuda_return13626()
//    {
//        //given
//        String url = "/catalog/posuda-801/";
//
//        //then
//        Category category = categoryService.getCategory(url);
//        //expected
//
//        assertEquals("X13626", category.getParentId());
//        assertEquals("13626", category.getCollectionId());
//    }
//
//    @Test
//    void getSubCategories_posuda_return83Subcategories()
//    {
//        //given
//        String parentId = "X13636";
//
//        //then
//        List<Category> subcategories = categoryService.getSubCategories(parentId);
//        //expected
//
//        assertEquals(83, subcategories.size());
//
//    }
}
