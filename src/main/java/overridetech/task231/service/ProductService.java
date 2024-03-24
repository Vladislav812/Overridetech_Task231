package overridetech.task231.service;

import net.minidev.json.JSONObject;
import overridetech.task231.dto.ProductDto;

import java.util.List;

public interface ProductService {
    void saveProduct(ProductDto productDto);

    List<ProductDto> findAllBySellerId(Long sellerId);

    void deleteProductById(long l);

    void patchProduct(ProductDto productDto);

    ProductDto findProductById(Long productId);

    List<ProductDto> findAllByOrderByIdAsc();
    void commitOrder(JSONObject orderedMap);
}
