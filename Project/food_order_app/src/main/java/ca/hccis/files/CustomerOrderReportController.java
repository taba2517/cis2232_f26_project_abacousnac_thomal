package ca.hccis.files;

import ca.hccis.files.dao.FoodOrderDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerOrderReportController {

    private final FoodOrderDAO foodOrderDAO;
    private final ReportFileService reportFileService;

    public CustomerOrderReportController(
            FoodOrderDAO foodOrderDAO,
            ReportFileService reportFileService) {
        this.foodOrderDAO = foodOrderDAO;
        this.reportFileService = reportFileService;
    }

    @GetMapping("/reports/customer-orders")
    public String showCustomerOrderReport(
            @RequestParam(required = false, defaultValue = "")
            String customerName,
            Model model) {

        List<Map<String, Object>> orders =
                foodOrderDAO.findByCustomerName(customerName);

        model.addAttribute("customerName", customerName);
        model.addAttribute("orders", orders);

        try {
            Path reportFile = reportFileService.saveCustomerOrderReport(
                    customerName, orders);

            model.addAttribute("reportSaved", true);
            model.addAttribute("reportFileName",
                    reportFile.getFileName().toString());

        } catch (IOException e) {
            model.addAttribute("reportSaved", false);
            model.addAttribute("reportFileError",
                    "Unable to save the report: " + e.getMessage());
        }

        return "reports/customer-orders";
    }
}
