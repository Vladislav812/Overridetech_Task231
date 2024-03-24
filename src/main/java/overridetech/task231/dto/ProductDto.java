package overridetech.task231.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long id;

    private String title;

    private String image;

    private String description;

    private int quantity;

    private double price;

    private Long sellerId;

    private String sellerName;

}
