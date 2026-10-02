package ca.hccis.files.bo;

import ca.hccis.files.entity.FoodOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * AI-generated test suite for the FoodOrderBO class.
 *
 * Tests are based on the Food Ordering and Catering System
 * requirements and the FoodOrder entity.
 */
public class FoodOrderBOAITest {

    /**
     * Tests the calculation for a delivery order.
     *
     * Price per meal = $18
     * Quantity = 3
     * Delivery fee = $5
     * Expected total = $59
     */
    @Test
    public void testDeliveryOrderCalculation() {

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
     * Tests the calculation for a pickup order.
     *
     * Price per meal = $18
     * Quantity = 3
     * Pickup should have no delivery fee.
     * Expected total = $54
     */
    @Test
    public void testPickupOrderCalculation() {

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
     * Tests a delivery order with different values.
     *
     * Price per meal = $12
     * Quantity = 5
     * Delivery fee = $7
     * Expected total = $67
     */
    @Test
    public void testDifferentDeliveryOrderCalculation() {

        FoodOrder foodOrder = new FoodOrder();

        foodOrder.setQuantity(5);
        foodOrder.setPricePerMeal(12.00);
        foodOrder.setOrderType("Delivery");
        foodOrder.setDeliveryFee(7.00);

        FoodOrderBO foodOrderBO = new FoodOrderBO();

        double result = foodOrderBO.calculate(foodOrder);

        assertEquals(67.00, result, 0.001);
    }

    /**
     * Tests an order containing one meal.
     *
     * Price per meal = $20
     * Quantity = 1
     * Delivery fee = $5
     * Expected total = $25
     */
    @Test
    public void testSingleMealDeliveryOrder() {

        FoodOrder foodOrder = new FoodOrder();

        foodOrder.setQuantity(1);
        foodOrder.setPricePerMeal(20.00);
        foodOrder.setOrderType("Delivery");
        foodOrder.setDeliveryFee(5.00);

        FoodOrderBO foodOrderBO = new FoodOrderBO();

        double result = foodOrderBO.calculate(foodOrder);

        assertEquals(25.00, result, 0.001);
    }

    /**
     * Tests that a valid calculated total is positive.
     */
    @Test
    public void testCalculatedTotalIsPositive() {

        FoodOrder foodOrder = new FoodOrder();

        foodOrder.setQuantity(2);
        foodOrder.setPricePerMeal(10.00);
        foodOrder.setOrderType("Delivery");
        foodOrder.setDeliveryFee(5.00);

        FoodOrderBO foodOrderBO = new FoodOrderBO();

        double result = foodOrderBO.calculate(foodOrder);

        assertTrue(result > 0);
    }
}