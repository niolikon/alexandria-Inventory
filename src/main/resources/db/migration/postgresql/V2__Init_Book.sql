--
-- Database: products
--

-- --------------------------------------------------------
-- Structure of sub-table product-book
-- --------------------------------------------------------

ALTER TABLE product ADD COLUMN 	book_pages 			INTEGER;
ALTER TABLE product ADD COLUMN 	book_isbn 			VARCHAR(16);
ALTER TABLE product ADD COLUMN 	book_author_id 		INTEGER;
ALTER TABLE product ADD COLUMN 	book_publisher_id 	INTEGER;

CREATE INDEX book_isbn ON product (book_isbn);

ALTER TABLE product ADD CONSTRAINT book_author_fk 
FOREIGN KEY (book_author_id) REFERENCES person (id) 
ON DELETE CASCADE;

ALTER TABLE product ADD CONSTRAINT book_publisher_fk 
FOREIGN KEY (book_publisher_id) REFERENCES company (id)
ON DELETE CASCADE;




