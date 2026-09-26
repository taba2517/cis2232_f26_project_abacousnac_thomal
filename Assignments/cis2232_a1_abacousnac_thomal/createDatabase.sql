CREATE DATABASE food_ordering;

USE food_ordering;

CREATE TABLE food_order (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            customerName VARCHAR(100),
                            phoneNumber VARCHAR(20),
                            mealName VARCHAR(100),
                            quantity INT,
                            pricePerMeal DECIMAL(10,2),
                            orderType VARCHAR(20),
                            deliveryFee DECIMAL(10,2),
                            orderDate DATE,
                            totalCost DECIMAL(10,2)
);