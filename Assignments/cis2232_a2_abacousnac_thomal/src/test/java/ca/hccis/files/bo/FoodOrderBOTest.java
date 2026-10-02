package ca.hccis.files.bo;

import ca.hccis.files.entity.FoodOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the FoodOrderBO class.
 */
public class FoodOrderBOTest {

    /**
     * Test 1:
     * This unit test was developed following a
     * Test Driven Development approach.
     *
     * Tests a delivery order using the example
     * from the project requirements.
     */
    @Test
    public void testCalculateDeliveryOrder() {

        FoodOrder foodOrder = new FoodOrder();

        foodOrder.setQuantity(3);
        foodOrder.setPricePerMeal(18.00);
        foodOrder.setOrderType("Delivery");
        foodOrder.setDeliveryFee(5.00);

        FoodOrderBO foodOrderBO = new FoodOrderBO();

        double result = foodOrderBO.calculate(foodOrder);

        assertEquals(59.00, result, 0.001);
    }

    /**
     * Test 2:
     * This unit test was developed following a
     * Test Driven Development approach.
     *
     * Tests a pickup order where the delivery fee
     * should not be added.
     */
    @Test
    public void testCalculatePickupOrder() {

        FoodOrder foodOrder = new FoodOrder();

        foodOrder.setQuantity(3);
        foodOrder.setPricePerMeal(18.00);
        foodOrder.setOrderType("Pickup");
        foodOrder.setDeliveryFee(5.00);

        FoodOrderBO foodOrderBO = new FoodOrderBO();

        double result = foodOrderBO.calculate(foodOrder);

        assertEquals(54.00, result, 0.001);
    }

    /**
     * Test 3:
     * This unit test was developed following a
     * Test Driven Development approach.
     *
     * Tests a different delivery order with
     * different quantity, meal price, and delivery fee.
     */
    @Test
    public void testCalculateDifferentDeliveryOrder() {

        FoodOrder foodOrder = new FoodOrder();

        foodOrder.setQuantity(5);
        foodOrder.setPricePerMeal(12.00);
        foodOrder.setOrderType("Delivery");
        foodOrder.setDeliveryFee(7.00);

        FoodOrderBO foodOrderBO = new FoodOrderBO();

        double result = foodOrderBO.calculate(foodOrder);

        assertEquals(67.00, result, 0.001);
        assertTrue(result > 0);
    }
}
