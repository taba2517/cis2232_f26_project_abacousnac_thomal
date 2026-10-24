package ca.hccis.files;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ReportFileService {

    private static final Path OUTPUT_DIRECTORY =
            Path.of("C:\\cis2232");

    private static final DateTimeFormatter FILE_TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private static final DateTimeFormatter DISPLAY_TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Path saveCustomerOrderReport(
            String customerName,
            List<Map<String, Object>> orders) throws IOException {

        StringBuilder report = new StringBuilder();

        report.append("NAIJA CUISINES\n");
        report.append("CUSTOMER ORDER REPORT\n");
        report.append("Generated: ")
                .append(LocalDateTime.now().format(DISPLAY_TIMESTAMP))
                .append("\n");
        report.append("Customer search: ")
                .append(customerName)
                .append("\n\n");

        appendOrderHeaders(report);
        appendOrders(report, orders);

        report.append("\nTotal matching orders: ")
                .append(orders.size())
                .append("\n");

        return writeReport("CustomerOrderReport", report.toString());
    }

    public Path saveSalesSummaryReport(
            String startDate,
            String endDate,
            String orderType,
            List<Map<String, Object>> orders,
            Map<String, Object> summary,
            List<Map<String, Object>> salesByOrderType)
            throws IOException {

        StringBuilder report = new StringBuilder();

        report.append("NAIJA CUISINES\n");
        report.append("SALES SUMMARY REPORT\n");
        report.append("Generated: ")
                .append(LocalDateTime.now().format(DISPLAY_TIMESTAMP))
                .append("\n");
        report.append("Start date: ").append(startDate).append("\n");
        report.append("End date: ").append(endDate).append("\n");
        report.append("Order type filter: ").append(orderType).append("\n\n");

        appendOrderHeaders(report);
        appendOrders(report, orders);

        report.append("\nSALES SUMMARY\n");
        report.append("Total orders: ")
                .append(summary.get("totalOrders")).append("\n");
        report.append("Total quantity: ")
                .append(summary.get("totalQuantity")).append("\n");
        report.append("Total revenue: $")
                .append(money(summary.get("totalRevenue"))).append("\n");

        report.append("\nREVENUE BY ORDER TYPE\n");
        report.append("Order Type | Total Orders | Total Revenue\n");

        for (Map<String, Object> sale : salesByOrderType) {
            report.append(sale.get("orderType"))
                    .append(" | ")
                    .append(sale.get("totalOrders"))
                    .append(" | $")
                    .append(money(sale.get("totalRevenue")))
                    .append("\n");
        }

        return writeReport("SalesSummaryReport", report.toString());
    }

    private void appendOrderHeaders(StringBuilder report) {
        report.append("CUSTOMER | MEAL | QUANTITY | ORDER TYPE | DATE | TOTAL COST\n");
        report.append("--------------------------------------------------------------------------\n");
    }

    private void appendOrders(
            StringBuilder report,
            List<Map<String, Object>> orders) {

        if (orders.isEmpty()) {
            report.append("No matching orders found.\n");
            return;
        }

        for (Map<String, Object> order : orders) {
            report.append(order.get("customerName")).append(" | ")
                    .append(order.get("mealName")).append(" | ")
                    .append(order.get("quantity")).append(" | ")
                    .append(order.get("orderType")).append(" | ")
                    .append(order.get("orderDate")).append(" | $")
                    .append(money(order.get("totalCost")))
                    .append("\n");
        }
    }

    private String money(Object value) {
        if (value == null) {
            return "0.00";
        }

        return new BigDecimal(value.toString())
                .setScale(2, java.math.RoundingMode.HALF_UP)
                .toPlainString();
    }

    private Path writeReport(String reportName, String content)
            throws IOException {

        Files.createDirectories(OUTPUT_DIRECTORY);

        String timestamp = LocalDateTime.now().format(FILE_TIMESTAMP);
        Path outputFile = OUTPUT_DIRECTORY.resolve(
                reportName + "_" + timestamp + ".txt"
        );

        Files.writeString(
                outputFile,
                content,
                StandardCharsets.UTF_8
        );

        return outputFile;
    }
}
