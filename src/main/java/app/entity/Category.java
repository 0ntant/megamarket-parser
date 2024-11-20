package app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category
{
    String url;
    String parentId;
    String collectionId;

    List<Category> subCategories;
    List<MegaProduct> megaProducts;
}
