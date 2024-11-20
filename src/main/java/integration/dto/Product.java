package integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Product(
        @JsonProperty("url")
        String url, // ссылка на товар
        @JsonProperty("name")
        String name, // наименование товара
        @JsonProperty("sale_price")
        BigDecimal salePrice, // стоимость товара со скидкой
        @JsonProperty("price")
        BigDecimal price, // стоимость товара без скидки
        @JsonProperty("sber_thnx_percent")
        Double sberThnxPercent, // размер скидки при оплате Сбером в %
        @JsonProperty("sber_thnx_amount")
        BigDecimal sberThnxAmount, // размер скидки при оплате Сбером в "спасибо"
        @JsonProperty("sper_prime_amount")
        BigDecimal sberPrimeAmount // дополнительное вознаграждение с подпискойСберПрайм
){}
