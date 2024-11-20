package app.integration.megamarket.dto;

public record ProductCardMainInfoDto(String goodsId, String merchantId, AuthDto auth)
{
    public ProductCardMainInfoDto(String goodsId)
    {
        this(goodsId, "0", AuthDto.defaultAuth());
    }
}
