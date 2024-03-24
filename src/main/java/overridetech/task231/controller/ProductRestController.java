package overridetech.task231.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import overridetech.task231.dto.ProductDto;
import overridetech.task231.service.ProductService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Secured("ROLE_seller")
public class ProductRestController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductDto> getAllProductsBySellerId(@RequestParam("seller") Long sellerId, HttpServletResponse response) {
        response.setHeader("Content-Type", "image");
        return productService.findAllBySellerId(sellerId);
    }


    @GetMapping("/products/list")
    @Secured("ROLE_user")
    public List<ProductDto> getAllProducts() throws Exception {
        return productService.findAllByOrderByIdAsc();
    }

    @GetMapping("/product/{productId}")
    public ProductDto getProduct(@PathVariable(value = "productId") Long productId) {
        System.out.println();
        return productService.findProductById(productId);
    }


    @PostMapping("/products")
    public void createNewProduct(@RequestBody ProductDto productDto) {
        productService.saveProduct(productDto);
    }

    @DeleteMapping("/products")
    public void deleteProduct(@RequestBody String productId) {
        productService.deleteProductById(Long.parseLong(productId));
    }

    @PatchMapping("/products")
    public void patchProduct(@RequestBody ProductDto productDto) {
        productService.patchProduct(productDto);
    }

    @PostMapping("/commitorder")
    @Secured("ROLE_user")
    public void commitOrder(@RequestBody JSONObject orderedMap) {
        productService.commitOrder(orderedMap);
    }
}
