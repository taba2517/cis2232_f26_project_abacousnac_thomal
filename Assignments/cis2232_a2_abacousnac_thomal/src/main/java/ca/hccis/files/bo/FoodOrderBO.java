package ca.hccis.files.bo;

import ca.hccis.files.entity.FoodOrder;

/**
 * Business object class for the Food Ordering and Catering System.
 *
 * This class contains the business calculation for a food order.
 *
 * @author Thomal Abacousnac
 * @since 20261002
 */
public class FoodOrderBO {

    /**
     * Calculates the total cost of a food order.
     *
     * Subtotal = Price Per Meal * Quantity
     * Total Cost = Subtotal + Delivery Fee
     *
     * If the order is for pickup, the delivery fee is not added.
     *
     * @param foodOrder the food order to calculate
     * @return the total cost of the order
     */
    public double calculate(FoodOrder foodOrder) {

        double subtotal = foodOrder.getPricePerMeal()
                * foodOrder.getQuantity();

        double deliveryFee = foodOrder.getDeliveryFee();

        if ("Pickup".equalsIgnoreCase(foodOrder.getOrderType())) {
            deliveryFee = 0;
        }

        return subtotal + deliveryFee;
    }
}