package org.abrar.storemanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.abrar.storemanagement.dto.ProductRequestDto;
import org.abrar.storemanagement.dto.ProductResponseDto;
import org.abrar.storemanagement.dto.ProductVariantDto;
import org.abrar.storemanagement.entity.ProductVariant;
import org.abrar.storemanagement.service.variant.ProductVariantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productvariant")
@RequiredArgsConstructor
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    @GetMapping("/create/{productId}")
    public String addVariantForm(@PathVariable Long productId, Model model) {
        model.addAttribute("variant", new ProductVariantDto());
        model.addAttribute("productId", productId);
        return "createVariant";
    }

    @PostMapping("/create/{productId}")
    public String addVariant(@PathVariable Long productId,
                             @ModelAttribute ProductVariantDto productVariantDto) {
        productVariantService.addVariant(productId, productVariantDto);
        return "redirect:/productsTest/details/" + productId;
    }
    @PostMapping("/delete/{variantId}")
    public String deleteVariant(@PathVariable Long variantId,
                                @RequestParam Long productId) {
        productVariantService.deleteVariant(variantId);
        return "redirect:/productsTest/" + productId;
    }

    @GetMapping("/edit/{variantId}")
    public String showUpdateVariantForm(
            @PathVariable Long variantId,
            Model model) {

        ProductVariantDto productVariantDto =productVariantService.getVariantById(variantId);

        model.addAttribute("variant", productVariantDto);
        model.addAttribute("variantId", variantId);


        return "updateVariant"; // updateVariant.jsp
    }
    @PostMapping("/edit/{variantId}")
    public String updateVariant(
            @PathVariable Long variantId,
            @Valid @ModelAttribute("variant") ProductVariantDto productVariantDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("variant", productVariantDto);
            model.addAttribute("variantId", variantId);
            return "updateVariant";
        }
        System.out.println(" product id:"+productVariantDto.getVariantId());
        productVariantService.updateVariant(variantId, productVariantDto);
        return "redirect:/productsTest/details/"+productVariantDto.getProductId();
    }

}
