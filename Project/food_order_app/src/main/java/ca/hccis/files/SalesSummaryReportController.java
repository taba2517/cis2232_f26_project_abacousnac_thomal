package ca.hccis.files;

import ca.hccis.files.dao.FoodOrderDAO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class SalesSummaryReportController {
    private final FoodOrderDAO foodOrderDAO;
    public SalesSummaryReportController(FoodOrderDAO foodOrderDAO) {
        this.foodOrderDAO = foodOrderDAO;
    }

    @GetMapping("/reports/sales-summary")
    public String showSalesSummaryReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,
            @RequestParam(required = false, defaultValue = "All")
            String orderType,

            Model model) {

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("orderType", orderType);

        if (startDate != null && endDate != null
                && !endDate.isBefore(startDate)) {
            String start = startDate.toString();
            String end = endDate.toString();
            model.addAttribute(
                    "orders",
                    foodOrderDAO.findOrdersByCriteria(
                            start, end, orderType
                    )
            );
            model.addAttribute(
                    "summary",
                    foodOrderDAO.getSalesSummary(
                            start, end, orderType
                    )
            );
            model.addAttribute(
                    "salesByOrderType",
                    foodOrderDAO.getSalesByOrderType(start, end)
            );
            model.addAttribute("reportGenerated", true);
        } else {
            model.addAttribute("reportGenerated", false);
        }
        return "reports/sales-summary";
    }
}
