package app.integration.megamarket.dto;

public record MenuDto(String parentId, Integer depthLevel, AuthDto auth)
{
    public MenuDto(String parentId)
    {
        this(parentId, 2, AuthDto.defaultAuth());
    }
}
