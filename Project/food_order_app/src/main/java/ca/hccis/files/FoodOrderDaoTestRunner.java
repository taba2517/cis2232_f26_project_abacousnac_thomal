package ca.hccis.files;

import ca.hccis.files.dao.FoodOrderDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodOrderDaoTestRunner {
    @Bean
    CommandLineRunner testFoodOrderDAO(FoodOrderDAO foodOrderDAO) {
        return args -> {
            System.out.println("===== FOOD ORDER DAO TEST =====");
            System.out.println("\n1. Customer search:");
            System.out.println(
                    foodOrderDAO.findByCustomerName("")
            );
            System.out.println("\n2. Date range search:");
            System.out.println(
                    foodOrderDAO.findOrdersByCriteria(
                            "2026-01-01",
                            "2026-12-31",
                            "All"
                    )
            );
            System.out.println("\n3. Sales summary:");
            System.out.println(
                    foodOrderDAO.getSalesSummary(
                            "2026-01-01",
                            "2026-12-31",
                            "All"
                    )
            );
            System.out.println("\n4. Sales by order type:");
            System.out.println(
                    foodOrderDAO.getSalesByOrderType(
                            "2026-01-01",
                            "2026-12-31"
                    )
            );
            System.out.println("\n===== DAO TEST COMPLETE =====");
        };
    }
}
