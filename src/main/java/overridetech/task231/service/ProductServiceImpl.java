package overridetech.task231.service;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overridetech.task231.dto.ProductDto;
import overridetech.task231.model.Product;
import overridetech.task231.repository.ProductRepository;
import overridetech.task231.util.ProductMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductMapper productMapper;

    @Override
    @Modifying
    public void saveProduct(ProductDto productDto) {
        Product product = new Product();
        productMapper.mapProductFromDto(productDto, product);
        productRepository.save(product);
    }

    @Override
    public List<ProductDto> findAllBySellerId(Long sellerId) {
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> productList = productRepository.findBySellerIdOrderByIdAsc(sellerId);
        productList.forEach(product -> {
            ProductDto productDto = new ProductDto();
            productMapper.mapDtoFromProduct(product, productDto);
            productDtoList.add(productDto);
        });
        return productDtoList;
    }

    @Override
    @Transactional
    public void deleteProductById(long l) {
        productRepository.deleteById(l);
    }

    @Override
    public void patchProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).get();
        productMapper.mapProductFromDto(productDto, product);
        productRepository.save(product);
    }

    @Override
    public ProductDto findProductById(Long productId) {
        ProductDto productDto = new ProductDto();
        Product product = productRepository.findById(productId).get();
        productMapper.mapDtoFromProduct(product, productDto);
        return productDto;
    }

    @Override
    public List<ProductDto> findAllByOrderByIdAsc() {
        List<ProductDto> productDtoList = new ArrayList<>();
        productRepository.findAllByOrderByIdAsc().forEach(product -> {
            ProductDto productDto = new ProductDto();
            productMapper.mapDtoFromProduct(product, productDto);
            productDtoList.add(productDto);
        });
        return productDtoList;
    }

    @Override
    public void commitOrder(JSONObject orderedMap) {
        orderedMap.forEach((k, v) -> {
            Product product = productRepository.findById(Long.parseLong(k)).get();
            int remainInStock = product.getQuantity();
            product.setQuantity(remainInStock - Integer.parseInt(v.toString()));
            productRepository.save(product);
        });
    }
}
