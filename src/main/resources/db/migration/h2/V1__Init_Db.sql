--
-- Database: products
--

DROP TABLE company IF EXISTS;
DROP TABLE person IF EXISTS;
DROP TABLE product IF EXISTS;
DROP TABLE image IF EXISTS;
DROP TABLE packet IF EXISTS;

-- --------------------------------------------------------
-- Structure of table company
-- --------------------------------------------------------

CREATE TABLE company (
  id 		INTEGER IDENTITY PRIMARY KEY,
  name 		VARCHAR(100)
);
CREATE INDEX company_name ON company (name);

ALTER TABLE company ADD CONSTRAINT company_unicity
UNIQUE(name);


-- --------------------------------------------------------
-- Structure of table person
-- --------------------------------------------------------

CREATE TABLE person (
  id 		INTEGER IDENTITY 		PRIMARY KEY,
  name 		VARCHAR(100),
  surname 	VARCHAR(100)
);
CREATE INDEX person_fullname ON person (name, surname);

ALTER TABLE person ADD CONSTRAINT person_unicity
UNIQUE(name, surname);


-- --------------------------------------------------------
-- Structure of table product
-- --------------------------------------------------------

CREATE TABLE product (
  id 			INTEGER IDENTITY 	PRIMARY KEY,
  name 			VARCHAR(100),
  description 	VARCHAR,
  label 		VARCHAR,
  price			DECIMAL(20,2),
  type			VARCHAR(10)
);

CREATE INDEX product_name ON product (name);
CREATE INDEX product_description ON product (description);

ALTER TABLE product ADD CONSTRAINT product_unicity
UNIQUE(name);


-- --------------------------------------------------------
-- Structure of table image
-- --------------------------------------------------------

CREATE TABLE image (
  id 			INTEGER IDENTITY 	PRIMARY KEY,
  mimetype		VARCHAR(100),
  data 			BLOB,
  product_id	INTEGER
);

ALTER TABLE image ADD CONSTRAINT image_product_fk 
FOREIGN KEY (product_id) REFERENCES product (id)
ON DELETE CASCADE;


-- --------------------------------------------------------
-- Structure of table packet
-- --------------------------------------------------------

CREATE TABLE packet (
  id 			INTEGER IDENTITY 	PRIMARY KEY,
  location 		VARCHAR(100) 		NOT NULL,
  size 			VARCHAR(100) 		NOT NULL,
  weight 		INT 				NOT NULL,
  barcode 		VARCHAR(40) 		NOT NULL,
  product_id	INTEGER 			NOT NULL,
  order_eid		VARCHAR(100),
  shipment_eid	VARCHAR(100)
);

CREATE INDEX packet_barcode ON packet (barcode);

ALTER TABLE packet ADD CONSTRAINT packet_unicity
UNIQUE(barcode);

ALTER TABLE packet ADD CONSTRAINT packet_product_fk 
FOREIGN KEY (product_id) REFERENCES product (id)
ON DELETE CASCADE;





