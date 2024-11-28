package com.example.shopapp.controller;

import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.dto.ProductImageDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.response.ProductListResponse;
import com.example.shopapp.response.ProductResponse;
import com.example.shopapp.services.IProductService;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValues;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        //Create Page from page information and limit
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        //Get total page number
        int totalPages = productPage.getTotalPages();
        ProductListResponse productResponses = ProductListResponse.builder()
                .products(productPage.getContent())
                .totalPages(totalPages)
                .build();
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) throws DataNotFoundException {
        try {
            Product existingProduct = productService.getProductById(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        try{
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(String.format("Product with ID: %d. Deleted", id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping(value = "")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages.toString());
            }

            Product product = productService.createProduct(productDTO);

            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long productId,
                                          @RequestParam("files") List<MultipartFile> files) {
        try {
            // Fetch the existing product by ID
            Product existingProduct = productService.getProductById(productId);

            // Ensure that files list is not null
            files = (files == null) ? new ArrayList<>() : files;

            List<ProductImage> productImages = new ArrayList<>();

            // Iterate over the files and validate/upload them
            for (MultipartFile multipartFile : files) {
                if (multipartFile.isEmpty()) {
                    continue; // Skip empty files
                }

                // File size check (max 10MB)
                if (multipartFile.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }

                // Check if the file is an image (must start with "image/")
                String contentType = multipartFile.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }

                // Store the file and get the file path or filename
                String filename = storeFile(multipartFile);

                // Create the ProductImage DTO and save the product image
                ProductImageDTO productImageDTO = ProductImageDTO.builder()
                        .productId(existingProduct.getId())
                        .imageURL(filename)
                        .build();
                ProductImage productImage = productService.createProductImage(productImageDTO);
                productImages.add(productImage);
            }

            // Return a successful response with the uploaded images
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            // In case of an error, return a bad request with the exception message
            return ResponseEntity.badRequest().body("Error uploading images: " + e.getMessage());
        }
    }


    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @PostMapping("/generatefakeproducts")
    public ResponseEntity<String> generateFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 1_000_000; i++) {
            String productName = faker.commerce().productName();
            float price = (float) faker.number().numberBetween(10, 90_000_000);
            String description = faker.lorem().sentence();
            long categoryId = faker.number().numberBetween(1, 4);
            if (productService.existsByName(productName)) {
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price(price)
                    .thumbnail("empty")
                    .description(description)
                    .categoryId(categoryId)
                    .build();
            try {
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok().body("Fake Product generated");
    }
}

