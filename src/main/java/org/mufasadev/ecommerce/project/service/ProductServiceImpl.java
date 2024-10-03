package org.mufasadev.ecommerce.project.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mufasadev.ecommerce.project.exceptions.APIException;
import org.mufasadev.ecommerce.project.exceptions.ResourceNotFoundException;
import org.mufasadev.ecommerce.project.models.Cart;
import org.mufasadev.ecommerce.project.models.Category;
import org.mufasadev.ecommerce.project.models.Product;
import org.mufasadev.ecommerce.project.payload.CartDTO;
import org.mufasadev.ecommerce.project.payload.ProductDTO;
import org.mufasadev.ecommerce.project.payload.ProductResponse;
import org.mufasadev.ecommerce.project.repository.CartRepository;
import org.mufasadev.ecommerce.project.repository.CategoryRepository;
import org.mufasadev.ecommerce.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;
    private final CartService cartService;

    @Value("${project.images}")
    private String imagePath;


    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        boolean isProductPresent = false;
        List<Product> products = category.getProducts();
        for (Product product : products) {
            if (Objects.equals(product.getProductName(), productDTO.getProductName())) {
                isProductPresent = true;
                break;
            }
        }
        if (!isProductPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setCategory(category);
            product.setImage("default.png");
            double specialPrice = product.getPrice() - ((0.01 * product.getDiscount()) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }else {
            throw new APIException("Product Already Exists!");
        }
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //TODO Remove code duplicate for Pagination and ProductResponse.setContent()
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy);
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Product> productsPage = productRepository.findAll(pageDetails);

        return getProductResponse(productsPage);
    }

    @Override
    public ProductResponse getByCategory(Long categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy);
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Product> productsPage = productRepository.findByCategoryOrderByPriceAsc(category,pageDetails);

        return getProductResponse(productsPage);
    }

    private ProductResponse getProductResponse(Page<Product> productsPage) {
        List<Product> products = productsPage.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContents(productDTOS);
        productResponse.setPageNumber(productsPage.getNumber());
        productResponse.setPageSize(productsPage.getSize());
        productResponse.setTotalElements(productsPage.getTotalElements());
        productResponse.setTotalPages(productsPage.getTotalPages());
        productResponse.setLastPage(productsPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy);
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Product> productsPage = productRepository.findByProductNameLikeIgnoreCase(keyword,pageDetails);
        return getProductResponse(productsPage);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        /*TODO : MAKE SURE THAT THE API CAN UPDATE EVEN WITH MISSING VALUES
        IN CASE OF UPDATING ONLY ONE VARIABLE Todo: Thinking of using if productDTO contains? */

        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","ProductId",productId));
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        double specialPrice = productDTO.getPrice() - ((0.01 * productDTO.getDiscount()) * productDTO.getPrice());
        product.setSpecialPrice(specialPrice);
        product = productRepository.save(product);
        List<Cart> carts = cartRepository.findCartsByProductId(productId);

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).toList();

        cartDTOs.forEach(cart -> cartService.updateProductInCarts(cart.getCartId(), productId));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","ProductId",productId));
        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        carts.forEach(cart -> cartService.deleteProductFromCart(cart.getCartId(), productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","ProductId",productId));
        String path = imagePath;
        String fileName = fileService.uploadImage(path,file);
        product.setImage(fileName);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}