create database CandyShop;
use CandyShop;
select * from Users;
CREATE TABLE Roles (
    ID_Role INT NOT NULL AUTO_INCREMENT,
    Name_Role VARCHAR(115),
    PRIMARY KEY (ID_Role),
    UNIQUE (Name_Role)
);

CREATE TABLE Users (
    ID_User INT NOT NULL AUTO_INCREMENT,
    First_Name VARCHAR(100) NOT NULL,
    Second_Name VARCHAR(100) NOT NULL,
    Middle_Name VARCHAR(100) NOT NULL,
    Gender VARCHAR(1) NOT NULL,
    Login VARCHAR(32) NOT NULL,
    Password TEXT NOT NULL,
    PhoneNumber VARCHAR(16) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Role_ID INT NOT NULL,
    EncryptKey TEXT NOT NULL,
    PRIMARY KEY (ID_User)
);

CREATE TABLE Treaty (
    ID_Treaty INT NOT NULL AUTO_INCREMENT,
    Treaty_Number VARCHAR(15) NOT NULL,
    Date_of_conclusion DATE NOT NULL,
    Amount DECIMAL(20,4) NOT NULL,
    TreatyContent TEXT NOT NULL,
    User_ID INT NOT NULL,
    PRIMARY KEY (ID_Treaty),
    UNIQUE (Treaty_Number),
    FOREIGN KEY (User_ID) REFERENCES Users (ID_User)
);

CREATE TABLE ProductsTypes (
    ID_ProductType INT NOT NULL AUTO_INCREMENT,
    ProductTypeName VARCHAR(20) NOT NULL,
    PRIMARY KEY (ID_ProductType),
    UNIQUE (ProductTypeName)
);

CREATE TABLE ShelfLifes (
    ID_ShelfLife INT NOT NULL AUTO_INCREMENT,
    ShelfLifeYear VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID_ShelfLife),
    UNIQUE (ShelfLifeYear)
);

CREATE TABLE IngerdientTypes (
    ID_IngredientType INT NOT NULL AUTO_INCREMENT,
    IngredientTypeName VARCHAR(30) NOT NULL,
    PRIMARY KEY (ID_IngredientType),
    UNIQUE (IngredientTypeName)
);

CREATE TABLE Ingredients (
    ID_Ingredient INT NOT NULL AUTO_INCREMENT,
    Ingredient_Article INT NOT NULL,
    Ingredient_Name VARCHAR(200) NOT NULL,
    IngredientType_ID INT NOT NULL,
    PRIMARY KEY (ID_Ingredient),
    UNIQUE (Ingredient_Name),
    FOREIGN KEY (IngredientType_ID) REFERENCES IngerdientTypes (ID_IngredientType)
);

CREATE TABLE Products (
    ID_Product INT NOT NULL AUTO_INCREMENT,
    ProductType_ID INT NOT NULL,
    ShelfLife_ID INT NOT NULL,
    Ingredient_ID INT NOT NULL,
    Product_Cost DECIMAL(12,4) NOT NULL,
    ProductCount INT NOT NULL,
    ProductName VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID_Product),
    UNIQUE (ProductName),
    FOREIGN KEY (ProductType_ID) REFERENCES ProductsTypes (ID_ProductType),
    FOREIGN KEY (ShelfLife_ID) REFERENCES ShelfLifes (ID_ShelfLife),
    FOREIGN KEY (Ingredient_ID) REFERENCES Ingredients (ID_Ingredient)
);


CREATE TABLE History (
    ID_History INT NOT NULL AUTO_INCREMENT,
    Treaty_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Product_Count_History INT NOT NULL,
    PRIMARY KEY (ID_History),
    FOREIGN KEY (Treaty_ID) REFERENCES Treaty (ID_Treaty),
    FOREIGN KEY (Product_ID) REFERENCES Products (ID_Product)
);

insert into Roles (Name_Role) values ('Админ'), ('Продавец'), ('Покупатель');

INSERT INTO ProductsTypes (ProductTypeName) VALUES 
('Конфеты'),
('Выпечка'),
('Батончики');

INSERT INTO ShelfLifes (ShelfLifeYear) VALUES 
('1 год'),
('2 года'),
('3 года');

INSERT INTO IngerdientTypes (IngredientTypeName) VALUES 
('Наружный'),
('Внутренний');

INSERT INTO Ingredients (Ingredient_Article, Ingredient_Name, IngredientType_ID) VALUES 
(12345, 'Нуга', 1),
(67890, 'Пудинг', 2),
(54321, 'Шоколад', 2);

INSERT INTO Products (ProductType_ID, ShelfLife_ID, Ingredient_ID, Product_Cost, ProductCount, ProductName) VALUES 
(1, 1, 1, 10.99, 100, 'Конфета с нугой'),
(2, 2, 2, 20.49, 50, 'Конфета с пудингом'),
(3, 3, 3, 15.75, 75, 'Конфета с шоколадом');







