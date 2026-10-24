package ca.hccis.files.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class FoodOrderDAO {

    private final JdbcTemplate jdbcTemplate;

    public FoodOrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // V2.1: Search orders by customer name
    public List<Map<String, Object>> findByCustomerName(
            String customerName) {

        String sql = """
                SELECT customerName, mealName, quantity,
                       orderType, orderDate, totalCost
                FROM food_order
                WHERE LOWER(customerName) LIKE LOWER(?)
                ORDER BY orderDate DESC
                """;

        return jdbcTemplate.queryForList(
                sql, "%" + customerName.trim() + "%"
        );
    }

    // V2.2: Find orders by date range and order type
    public List<Map<String, Object>> findOrdersByCriteria(
            String startDate,
            String endDate,
            String orderType) {

        String sql = """
                SELECT customerName, mealName, quantity,
                       orderType, orderDate, totalCost
                FROM food_order
                WHERE orderDate BETWEEN ? AND ?
                  AND (? = 'All' OR LOWER(orderType) = LOWER(?))
                ORDER BY orderDate ASC
                """;

        return jdbcTemplate.queryForList(
                sql,
                startDate,
                endDate,
                orderType,
                orderType
        );
    }

    // V2.3: Calculate sales summary
    public Map<String, Object> getSalesSummary(
            String startDate,
            String endDate,
            String orderType) {

        String sql = """
                SELECT
                    COUNT(*) AS totalOrders,
                    COALESCE(SUM(quantity), 0) AS totalQuantity,
                    COALESCE(SUM(totalCost), 0) AS totalRevenue
                FROM food_order
                WHERE orderDate BETWEEN ? AND ?
                  AND (? = 'All' OR LOWER(orderType) = LOWER(?))
                """;

        return jdbcTemplate.queryForMap(
                sql,
                startDate,
                endDate,
                orderType,
                orderType
        );
    }

    // V2.4: Retrieve pickup and delivery statistics for the chart
    public List<Map<String, Object>> getSalesByOrderType(
            String startDate,
            String endDate) {

        String sql = """
                SELECT
                    orderType,
                    COUNT(*) AS totalOrders,
                    COALESCE(SUM(totalCost), 0) AS totalRevenue
                FROM food_order
                WHERE orderDate BETWEEN ? AND ?
                GROUP BY orderType
                ORDER BY orderType
                """;

        return jdbcTemplate.queryForList(
                sql, startDate, endDate
        );
    }
}
