package ca.hccis.files;

import ca.hccis.files.dao.FoodOrderDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerOrderReportController {
    private final FoodOrderDAO foodOrderDAO;
    public CustomerOrderReportController(FoodOrderDAO foodOrderDAO) {
        this.foodOrderDAO = foodOrderDAO;
    }

    @GetMapping("/reports/customer-orders")
    public String showCustomerOrderReport(
            @RequestParam(required = false, defaultValue = "") String customerName,
            Model model) {
        model.addAttribute("customerName", customerName);
        model.addAttribute(
                "orders",
                foodOrderDAO.findByCustomerName(customerName)
        );
        return "reports/customer-orders";
    }
}
