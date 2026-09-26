# Food Ordering and Catering System

Food Ordering and Catering System project

## Development Team

Business Client: Sybil

Lead Developer: Thomal

Quality Control: Hagai


## Description

This web application will allow customers to place food and catering orders
for Naija Cuisines, a Nigerian food business in Prince Edward Island. The
customer will be able to enter their name and phone number, choose a meal,
select the quantity, and choose either pickup or delivery. The customer will
also be able to select the date they want the order prepared or delivered.

The application will calculate the subtotal based on the selected meal price
and quantity. If delivery is selected, a delivery fee will be added to the
subtotal to calculate the final total cost.


## Color

Main Color: To be determined by the Business Analyst
Secondary Color: To be determined by the Business Analyst


## Required Fields

customerName String The name of the customer placing the order

phoneNumber String The customer's contact number in case the business needs to confirm the order

mealName String The Nigerian meal selected by the customer

quantity int The number of portions the customer wants to order

pricePerMeal Decimal The price of one portion of the selected meal

orderType String Shows whether the customer wants pickup or delivery

deliveryFee Decimal The extra amount charged when delivery is selected

orderDate Date The date the customer wants the food prepared or delivered

totalCost Decimal The final amount the customer will pay after the order is calculated


## Calculation

The calculation / processing needed when the customer enters a new order will
be to determine the subtotal and total cost.

The subtotal will be calculated by multiplying the price per meal by the
quantity ordered.

Subtotal = Price Per Meal × Quantity

If the customer chooses delivery, the delivery fee will be added to the
subtotal.

Total Cost = Subtotal + Delivery Fee

If the customer chooses pickup, the delivery fee will be $0.

For example, if a customer orders 3 plates of Jollof Rice at $18 per plate,
the subtotal will be $54. If the delivery fee is $5, the final Total Cost
will be $59.


## Report Details

To be determined in future sprint
