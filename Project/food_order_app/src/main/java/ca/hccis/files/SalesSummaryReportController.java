package ca.hccis.files;

import ca.hccis.files.dao.FoodOrderDAO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class SalesSummaryReportController {

    private final FoodOrderDAO foodOrderDAO;
    private final ReportFileService reportFileService;

    public SalesSummaryReportController(
            FoodOrderDAO foodOrderDAO,
            ReportFileService reportFileService) {
        this.foodOrderDAO = foodOrderDAO;
        this.reportFileService = reportFileService;
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

            List<Map<String, Object>> orders =
                    foodOrderDAO.findOrdersByCriteria(
                            start, end, orderType);

            Map<String, Object> summary =
                    foodOrderDAO.getSalesSummary(
                            start, end, orderType);

            List<Map<String, Object>> salesByOrderType =
                    foodOrderDAO.getSalesByOrderType(start, end);

            model.addAttribute("orders", orders);
            model.addAttribute("summary", summary);
            model.addAttribute("salesByOrderType", salesByOrderType);
            model.addAttribute("reportGenerated", true);

            try {
                Path reportFile =
                        reportFileService.saveSalesSummaryReport(
                                start, end, orderType,
                                orders, summary, salesByOrderType);

                model.addAttribute("reportSaved", true);
                model.addAttribute("reportFileName",
                        reportFile.getFileName().toString());

            } catch (IOException e) {
                model.addAttribute("reportSaved", false);
                model.addAttribute("reportFileError",
                        "Unable to save the report: " + e.getMessage());
            }

        } else {
            model.addAttribute("reportGenerated", false);
        }

        return "reports/sales-summary";
    }
}
