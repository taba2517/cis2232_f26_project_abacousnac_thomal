package ca.hccis.files.entity;

import ca.hccis.util.CisUtility;

/**
 * Represents a food order for the Naija Cuisines
 * Food Ordering and Catering System.
 *
 * @author Thomal Abacousnac
 * @since 20260923
 */
public class FoodOrder {
    private String customerName;
    private String phoneNumber;
    private String mealName;
    private int quantity;
    private double pricePerMeal;
    private String orderType;
    private double deliveryFee;
    private String orderDate;
    private double totalCost;

    /**
     * Default constructor.
     */
    public FoodOrder() {
    }

    /**
     * Gets the information for a new food order.
     * <p>
     * Note:
     * Total Cost calculation is intentionally not performed
     * for this assignment. It will be completed in the
     * unit testing assignment.
     */
    public void getInformation() {
        customerName = CisUtility.getInputString(
                "Customer Name: ");
        phoneNumber = CisUtility.getInputString(
                "Phone Number: ");
        mealName = CisUtility.getInputString(
                "Meal Name: ");
        quantity = CisUtility.getInputInt(
                "Quantity: ");
        pricePerMeal = CisUtility.getInputDouble(
                "Price Per Meal: ");
        orderType = CisUtility.getInputString(
                "Order Type (Pickup/Delivery): ");
        deliveryFee = CisUtility.getInputDouble(
                "Delivery Fee: ");
        orderDate = CisUtility.getInputString(
                "Order Date: ");
        // Calculation is not required for this assignment.
        totalCost = 0;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerMeal() {
        return pricePerMeal;
    }

    public void setPricePerMeal(double pricePerMeal) {
        this.pricePerMeal = pricePerMeal;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return System.lineSeparator()
                + "Food Order"
                + System.lineSeparator()
                + "------------------------------"
                + System.lineSeparator()
                + "Customer Name: " + customerName
                + System.lineSeparator()
                + "Phone Number: " + phoneNumber
                + System.lineSeparator()
                + "Meal Name: " + mealName
                + System.lineSeparator()
                + "Quantity: " + quantity
                + System.lineSeparator()
                + String.format("Price Per Meal: $%.2f", pricePerMeal)
                + System.lineSeparator()
                + "Order Type: " + orderType
                + System.lineSeparator()
                + String.format("Delivery Fee: $%.2f", deliveryFee)
                + System.lineSeparator()
                + "Order Date: " + orderDate
                + System.lineSeparator()
                + String.format("Total Cost: $%.2f", totalCost)
                + System.lineSeparator()
                + "------------------------------";
    }
}