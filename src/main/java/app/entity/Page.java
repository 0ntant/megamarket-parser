package app.entity;

//"total": "25721",
//"offset": "0",
//"limit": "44",


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Page
{
    Integer total;
    Integer offset;
    Integer limit;
}
