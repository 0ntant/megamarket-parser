package app.integration.megamarket.dto;

public record CatalogSearchDto(Integer requestVersion,
                               Integer limit,
                               Integer offset,
                               String selectedAssumedCollectionId,
                               String collectionId,
                               AuthDto auth)
{
    public CatalogSearchDto(String collectionId,
                            Integer limit,
                            Integer offset)
    {
        this(
                10,
                limit,
                offset,
                collectionId,
                collectionId,
                AuthDto.defaultAuth()
        );
    }
}
