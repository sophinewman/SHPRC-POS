create table Category (
	categoryID INT PRIMARY KEY,
	categoryName TEXT,
	categoryColor TEXT
);

create table Product (
	productID INT PRIMARY KEY,
	productName TEXT,
	price INT,
	cost INT,
	categoryID INT REFERENCES Category
);

create table Purchase (
	PurchaseID INT PRIMARY KEY,
	time TEXT,
	total INT,
	creditUsed INT,
	clientAffiliation INT,
	CHECK (creditUsed <= total)
);

create table PurchasedProduct (
	PurchaseID INT REFERENCES Purchase,
	productID INT REFERENCES Product,
	PRIMARY KEY (PurchaseID, productID)
);

create table Client (
	SUID INT PRIMARY KEY,
	creditAvailable INT,
	pregnancyTest INT
);

create table PTestSubsidy (
	AMOUNT INT
);
