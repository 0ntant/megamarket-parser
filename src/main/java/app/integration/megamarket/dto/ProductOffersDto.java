package app.integration.megamarket.dto;

public record ProductOffersDto(
        String goodsId,
        Integer requestVersion,
        AuthDto authDto
)
{
    public ProductOffersDto(String goodsId)
    {
        this(goodsId, 11, AuthDto.defaultAuth());
    }
}
