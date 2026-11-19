package ca.hccis.files.repository;

import ca.hccis.files.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository
        extends JpaRepository<FoodOrder, Integer> {

}
