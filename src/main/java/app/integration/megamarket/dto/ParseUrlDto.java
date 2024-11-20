package app.integration.megamarket.dto;

public record ParseUrlDto(String url, AuthDto auth)
{
    public ParseUrlDto(String url)
    {
        this(url, AuthDto.defaultAuth());
    }
}
