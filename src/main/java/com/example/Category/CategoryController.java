package com.example.lab12.Category;

import com.example.lab12.Product.Product;
import com.example.lab12.Product.ProductRepository;
import com.example.lab12.Category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("/{categoryId}/products")
    public Category addProductsToCategory(@PathVariable Long categoryId, @RequestBody List<Long> productIds) {
        jdk.jfr.Category category = (jdk.jfr.Category) categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> products = productRepository.findAllById(productIds);
        products.forEach(product -> product.setCategory(category));

        productRepository.saveAll(products);
        return categoryRepository.findById(categoryId).get();
    }
}