package app.integration.megamarket.dto;

public record AuthDto (
     String locationId,
     String appPlatform,
     int appVersion,
     String os)
{
    public static AuthDto defaultAuth()
    {
        return new AuthDto(
                "50",
                "WEB",
                0,
                "UNKNOWN_OS"
        );
    }
}
