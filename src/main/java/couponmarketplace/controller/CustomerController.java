package couponmarketplace.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import couponmarketplace.dto.PurchaseResponse;
import couponmarketplace.service.ProductService;
import couponmarketplace.service.PurchaseService;

@Controller
public class CustomerController {

    private final ProductService productService;
    private final PurchaseService purchaseService;

    public CustomerController(ProductService productService, PurchaseService purchaseService) {
        this.productService = productService;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.getAllAvailableProducts());
        return "customer";
    }

    @PostMapping("/purchase/{id}")
    public String purchase(@PathVariable UUID id, Model model) {
        try {
            PurchaseResponse response = purchaseService.purchaseCouponDirect(id);
            model.addAttribute("response", response);
            return "purchase-success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    /*
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", productService.getAllProducts().stream());
        model.addAttribute("createCouponRequest", new CreateCouponRequest());
        return "admin";
    }

    @PostMapping("/admin/create")
    public String createCoupon(@ModelAttribute CreateCouponRequest request, Model model) {
        couponService.createCoupon(request);
        return "redirect:/admin";
    }
     */
}
