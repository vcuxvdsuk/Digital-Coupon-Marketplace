package couponmarketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import couponmarketplace.dto.CreateCouponRequest;
import couponmarketplace.service.CouponService;
import couponmarketplace.service.ProductService;
import jakarta.validation.Valid;

@Controller
public class AdminPageController {

    private final ProductService productService;
    private final CouponService couponService;

    public AdminPageController(ProductService productService, CouponService couponService) {
        this.productService = productService;
        this.couponService = couponService;
    }

    @PostMapping("/admin/create")
    public String createCoupon(
            @Valid @ModelAttribute CreateCouponRequest request,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("products", productService.getAllProducts());
            return "admin";
        }

        couponService.createCoupon(request);

        model.addAttribute("createCouponRequest", new CreateCouponRequest());
        model.addAttribute("products", productService.getAllProducts());
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("createCouponRequest", new CreateCouponRequest());
        model.addAttribute("products", productService.getAllProducts());

        return "admin";
    }
}
