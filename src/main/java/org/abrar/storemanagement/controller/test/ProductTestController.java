package org.abrar.storemanagement.controller.test;



import lombok.RequiredArgsConstructor;
import org.abrar.storemanagement.dto.ApiResponse;
import org.abrar.storemanagement.dto.ProductRequestDto;
import org.abrar.storemanagement.dto.ProductResponseDto;
import org.abrar.storemanagement.entity.Product;
import org.abrar.storemanagement.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/productsTest")
@RequiredArgsConstructor
public class ProductTestController {

    private final ProductService productService;



    @GetMapping("/getAll")
    public String productListPage(Model model) {

        model.addAttribute(
                "products",
                productService.getAllActiveProducts()
        );

        return "products"; // JSP name
    }
    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        // Pass an empty DTO to the form
        model.addAttribute("product", new ProductRequestDto());
        return "createProduct"; // JSP file name (createProduct.jsp)
    }


    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductRequestDto product,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createProduct";
        }
        productService.createProduct(product);
        return "redirect:/productsTest/getAll";
    }




    @GetMapping("edit/{productId}")
    public String showUpdateForm(@PathVariable Long productId, Model model) {
        ProductResponseDto responseDto = productService.getActiveProductById(productId);


        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setProductName(responseDto.getProductName());
        requestDto.setSellingPrice(responseDto.getSellingPrice());
        requestDto.setTax(responseDto.getTax());
        requestDto.setVersion(responseDto.getVersion());
        requestDto.setVariants(responseDto.getVariants());
        System.out.println(responseDto.getVersion());
        model.addAttribute("product", requestDto); // <-- matches form:form modelAttribute
        model.addAttribute("productId", responseDto.getProductId()); // keep ID for URL

        return "updateProduct";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @ModelAttribute ProductRequestDto dto,
            Model model) {

        try {
            productService.updateProduct(id, dto);
            return "redirect:/productsTest/getAll";
        } catch (RuntimeException ex) {
            // Optimistic lock failed
            model.addAttribute("product", dto);
            model.addAttribute("productId",id);
            model.addAttribute("error", ex.getMessage());
            return "updateProduct";
        }


    }
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.softDeleteProduct(id); // or actual delete
        return "redirect:/productsTest/getAll";
    }


    @GetMapping("/details/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        ProductResponseDto product = productService.getActiveProductById(id);
        model.addAttribute("product", product);
        return "productDetails";
    }
    @GetMapping("/check-name")
    @ResponseBody
    public boolean checkProductName(@RequestParam String name) {
        return productService.productNameExists(name);
    }


}

