package overridetech.task231.util;

import org.mapstruct.*;
import overridetech.task231.dto.ProductDto;
import overridetech.task231.model.Product;

import java.nio.charset.Charset;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "sellerId", target = "seller.id")
    public void mapProductFromDto(ProductDto productDto, @MappingTarget Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seller.name", target = "sellerName")
    @Mapping(source = "seller.id", target = "sellerId")
    public void mapDtoFromProduct(Product product, @MappingTarget ProductDto productDto);

    final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    default byte[] map(String string) {
        return string != null ? string.getBytes(UTF8_CHARSET) : null;
    }

    default String map(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }
}
