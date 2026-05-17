package couponmarketplace.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import couponmarketplace.dto.CreateCouponRequest;
import couponmarketplace.dto.PurchaseResponse;
import couponmarketplace.service.CouponService;

@Controller
public class CustomerController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", couponService.getAvailableCoupons());
        return "customer";
    }

    @PostMapping("/purchase/{id}")
    public String purchase(@PathVariable UUID id, Model model) {
        try {
            PurchaseResponse response = couponService.purchaseCouponDirect(id);
            model.addAttribute("response", response);
            return "purchase-success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", couponService.getAvailableCoupons());
        model.addAttribute("createCouponRequest", new CreateCouponRequest());
        return "admin";
    }

    @PostMapping("/admin/create")
    public String createCoupon(@ModelAttribute CreateCouponRequest request, Model model) {
        couponService.createCoupon(request);
        return "redirect:/admin";
    }
}