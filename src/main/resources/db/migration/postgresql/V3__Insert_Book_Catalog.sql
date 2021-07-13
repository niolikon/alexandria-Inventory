-- --------------------------------------------------------
-- Data for table person
-- --------------------------------------------------------

INSERT INTO person (id, name, surname) 
VALUES 
(1, 'Howard', 'Lovecraft');

INSERT INTO person (id, name, surname) 
VALUES 
(2, 'J.R.R.', 'Tolkien');

INSERT INTO person (id, name, surname) 
VALUES 
(3, 'J.K.', 'Rowling');

-- --------------------------------------------------------
-- Data for table company
-- --------------------------------------------------------

INSERT INTO company (id, name) 
VALUES 
(1, 'Mondadori');

INSERT INTO company (id, name) 
VALUES 
(2, 'Einaudi');

INSERT INTO company (id, name) 
VALUES 
(3, 'Panini');

-- --------------------------------------------------------
-- Data for table product
-- --------------------------------------------------------

INSERT INTO product (id, name, description, label, price, type, 
					 book_pages, book_isbn, book_author_id, book_publisher_id) 
VALUES (1, 'Il colore venuto dallo spazio', 'La vita di una famiglia è sconvolta dalla caduta di un meteorite.','NEW', 4.90, 'book', 
		192, '978-8868838256', 1, 3);

INSERT INTO product (id, name, description, label, price, type, 
					 book_pages, book_isbn, book_author_id, book_publisher_id) 
VALUES (2, 'Harry Potter e la pietra filosofale', 'Harry Potter è un ragazzo normale, o quantomeno è convinto di esserlo, anche se a volte provoca strani fenomeni', 'NEW', 9.50, 'book', 
		277, '978-8831003380', 3, 1);

INSERT INTO product (id, name, description, label, price, type, 
					 book_pages, book_isbn, book_author_id, book_publisher_id) 
VALUES (3, 'Racconti Incompiuti', 'Impegnato a lungo nella progettazione e stesura del Signore degli Anelli J.R.R. Tolkien continuò per decenni a sviluppare temi e personaggi, rimasti per lunghi anni nel cassetto.', 'HOT', 13.50, 'book',
		702, '978-8845274039', 2, 2);
