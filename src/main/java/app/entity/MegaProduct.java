package app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MegaProduct
{
    String goodsId;
    String url;
    String name;
    BigDecimal salePrice;
    BigDecimal price;
    Double sberThnxPercent;
    BigDecimal sberThnxAmount;
    BigDecimal sberPrimeAmount;
}
