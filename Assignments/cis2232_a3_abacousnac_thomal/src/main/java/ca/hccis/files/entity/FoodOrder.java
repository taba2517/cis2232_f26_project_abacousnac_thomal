package ca.hccis.files.entity;

import ca.hccis.util.CisUtility;

import javax.swing.JOptionPane;

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
        customerName = CisUtility.getInputString("Customer Name: ");
        phoneNumber = CisUtility.getInputString("Phone Number: ");
        mealName = CisUtility.getInputString("Meal Name: ");
        quantity = CisUtility.getInputInt("Quantity: ");
        pricePerMeal = CisUtility.getInputDouble("Price Per Meal: ");
        orderType = CisUtility.getInputString("Order Type (Pickup/Delivery): ");
        orderType = CisUtility.getInputString("Order Type (Pickup/Delivery): ");
        if (orderType.equalsIgnoreCase("Pickup")) {
            deliveryFee = 0;
        } else {
            deliveryFee = CisUtility.getInputDouble("Delivery Fee: ");
        }
        // Calculation is not required for this assignment.
        totalCost = 0;
    }


    /**
     * Gets food order information using JOptionPane.
     */
    public void getInformationJOptionPane() {
        customerName = JOptionPane.showInputDialog("Enter customer name:");
        if (customerName == null) {throw new java.util.concurrent.CancellationException();}
        phoneNumber = JOptionPane.showInputDialog("Enter phone number:");
        if (phoneNumber == null) {throw new java.util.concurrent.CancellationException();}
        mealName = JOptionPane.showInputDialog("Enter meal name:");
        if (mealName == null) {throw new java.util.concurrent.CancellationException();}
        String quantityInput = JOptionPane.showInputDialog("Enter quantity:");
        if (quantityInput == null) {throw new java.util.concurrent.CancellationException();}
        quantity = Integer.parseInt(quantityInput);
        String priceInput = JOptionPane.showInputDialog("Enter price per meal:");
        if (priceInput == null) {throw new java.util.concurrent.CancellationException();}
        pricePerMeal = Double.parseDouble(priceInput);
        String[] orderTypes = {"Pickup", "Delivery"};
        orderType = (String) JOptionPane.showInputDialog(
                null,
                "Select order type:",
                "Food Order",
                JOptionPane.QUESTION_MESSAGE,
                null,
                orderTypes,
                orderTypes[0]
        );
        if (orderType == null) {
            throw new java.util.concurrent.CancellationException();
        }
        if (orderType.equalsIgnoreCase("Pickup")) {
            deliveryFee = 0;
        } else {
            String deliveryInput = JOptionPane.showInputDialog(null, "Enter delivery fee:");
            if (deliveryInput == null) {
                throw new java.util.concurrent.CancellationException();
            }
            deliveryFee = Double.parseDouble(deliveryInput);
        }
        orderDate = JOptionPane.showInputDialog("Enter order date:");
        if (orderDate == null) {throw new java.util.concurrent.CancellationException();}
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
        StringBuilder output = new StringBuilder();
        output.append("Food Order\n");
        output.append("----------------------------------------\n");
        output.append("Customer Name: ").append(customerName).append("\n");
        output.append("Phone Number: ").append(phoneNumber).append("\n");
        output.append("Meal Name: ").append(mealName).append("\n");
        output.append("Quantity: ").append(quantity).append("\n");
        output.append(String.format("Price Per Meal: $%.2f%n", pricePerMeal));
        output.append("Order Type: ").append(orderType).append("\n");
        // Only display the delivery fee for delivery orders.
        if ("Delivery".equalsIgnoreCase(orderType)) {
            output.append(String.format(
                    "Delivery Fee: $%.2f%n",
                    deliveryFee));
        }
        output.append("Order Date: ").append(orderDate).append("\n");
        output.append(String.format("Total Cost: $%.2f%n", totalCost));
        output.append("----------------------------------------");
        return output.toString();
    }
}