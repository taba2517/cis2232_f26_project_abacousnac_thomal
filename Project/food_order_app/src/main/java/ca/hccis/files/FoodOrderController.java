package ca.hccis.files;

import ca.hccis.files.entity.FoodOrder;
import ca.hccis.files.repository.FoodOrderRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
