package ca.hccis.files.entity;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import ca.hccis.util.CisUtility;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a food order for the Naija Cuisines
 * Food Ordering and Catering System.
 *
 * @author Thomal Abacousnac
 * @since 20260923
 */
@Entity
@Table(name = "food_order")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Customer name is required.")
    @Size(max = 100, message = "Customer name cannot exceed 100 characters.")
    @Column(name = "customerName")
    private String customerName;

    @NotBlank(message = "Phone number is required.")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters.")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @NotBlank(message = "Meal name is required.")
    @Size(max = 100, message = "Meal name cannot exceed 100 characters.")
    @Column(name = "mealName")
    private String mealName;

    @Min(value = 1, message = "Quantity must be at least 1.")
    @Column(name = "quantity")
    private int quantity;

    @NotNull(message = "Price per meal is required.")
    @DecimalMin(value = "0.00", message = "Price cannot be negative.")
    @Digits(integer = 8, fraction = 2,
            message = "Price must have no more than 2 decimal places.")
    @Column(name = "pricePerMeal")
    private BigDecimal pricePerMeal;

    @NotBlank(message = "Please select an order type.")
    @Pattern(regexp = "Pickup|Delivery",
            message = "Order type must be Pickup or Delivery.")
    @Column(name = "orderType")
    private String orderType;

    @NotNull(message = "Delivery fee is required.")
    @DecimalMin(value = "0.00", message = "Delivery fee cannot be negative.")
    @Digits(integer = 8, fraction = 2,
            message = "Delivery fee must have no more than 2 decimal places.")
    @Column(name = "deliveryFee")
    private BigDecimal deliveryFee;

    @NotNull(message = "Order date is required.")
    @Column(name = "orderDate")
    private LocalDate orderDate;

    @Column(name = "totalCost")
    private BigDecimal totalCost;



    public FoodOrder() {
    }

    public void getInformation() {
        customerName = CisUtility.getInputString("Customer Name: ");
        phoneNumber = CisUtility.getInputString("Phone Number: ");
        mealName = CisUtility.getInputString("Meal Name: ");
        quantity = CisUtility.getInputInt("Quantity: ");

        pricePerMeal = BigDecimal.valueOf(
                CisUtility.getInputDouble("Price Per Meal: "));

        orderType = CisUtility.getInputString(
                "Order Type (Pickup/Delivery): ");

        deliveryFee = BigDecimal.valueOf(
                CisUtility.getInputDouble("Delivery Fee: "));

        orderDate = LocalDate.parse(
                CisUtility.getInputString("Order Date (YYYY-MM-DD): "));

        totalCost = BigDecimal.ZERO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getPricePerMeal() {
        return pricePerMeal;
    }

    public void setPricePerMeal(BigDecimal pricePerMeal) {
        this.pricePerMeal = pricePerMeal;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Food Order\n"
                + "------------------------------\n"
                + "ID: " + id + "\n"
                + "Customer Name: " + customerName + "\n"
                + "Phone Number: " + phoneNumber + "\n"
                + "Meal Name: " + mealName + "\n"
                + "Quantity: " + quantity + "\n"
                + "Price Per Meal: $" + pricePerMeal + "\n"
                + "Order Type: " + orderType + "\n"
                + "Delivery Fee: $" + deliveryFee + "\n"
                + "Order Date: " + orderDate + "\n"
                + "Total Cost: $" + totalCost + "\n"
                + "------------------------------";
    }
}
