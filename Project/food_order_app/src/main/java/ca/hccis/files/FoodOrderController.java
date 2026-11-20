package ca.hccis.files;

import ca.hccis.files.entity.FoodOrder;
import ca.hccis.files.repository.FoodOrderRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import java.math.BigDecimal;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class FoodOrderController {

    private final FoodOrderRepository foodOrderRepository;

    public FoodOrderController(
            FoodOrderRepository foodOrderRepository) {
        this.foodOrderRepository = foodOrderRepository;
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {

        List<FoodOrder> orders = foodOrderRepository.findAll();

        model.addAttribute("orders", orders);

        return "food-orders";
    }

    @GetMapping("/orders/new")
    public String showAddOrderForm(Model model) {

        model.addAttribute("foodOrder", new FoodOrder());

        return "food-order-form";
    }

    @PostMapping("/orders/save")
    public String saveOrder(
            @Valid FoodOrder foodOrder,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("foodOrder", foodOrder);
            return "food-order-form";
        }

        BigDecimal mealTotal = foodOrder.getPricePerMeal()
                .multiply(BigDecimal.valueOf(foodOrder.getQuantity()));

        BigDecimal deliveryFee = foodOrder.getDeliveryFee();

        foodOrder.setTotalCost(mealTotal.add(deliveryFee));

        foodOrderRepository.save(foodOrder);

        return "redirect:/orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String showEditOrderForm(
            @PathVariable int id,
            Model model) {

        FoodOrder foodOrder = foodOrderRepository
                .findById(id)
                .orElse(null);

        if (foodOrder == null) {
            return "redirect:/orders";
        }

        model.addAttribute("foodOrder", foodOrder);

        return "food-order-form";
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable int id) {

        foodOrderRepository.deleteById(id);

        return "redirect:/orders";
    }
}
