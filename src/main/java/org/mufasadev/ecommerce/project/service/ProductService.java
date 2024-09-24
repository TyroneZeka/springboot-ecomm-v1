package org.mufasadev.ecommerce.project.service;

import org.mufasadev.ecommerce.project.models.Product;
import org.mufasadev.ecommerce.project.payload.ProductDTO;
import org.mufasadev.ecommerce.project.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
    ProductResponse getAllProducts();
    ProductResponse getByCategory(Long categoryId);
    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);
}
