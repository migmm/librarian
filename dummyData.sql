-- Insert 10 Authors
INSERT INTO Author (name, surname, status)
VALUES
    ('George', 'Orwell', true),
    ('Margaret', 'Atwood', true),
    ('Ray', 'Bradbury', true),
    ('Aldous', 'Huxley', true),
    ('Jules', 'Verne', true),
    ('Fyodor', 'Dostoevsky', true),
    ('Charles', 'Dickens', true),
    ('Agatha', 'Christie', true),
    ('J.K.', 'Rowling', true),
    ('Harper', 'Lee', true);


-- Insert 10 Vendors (Publishing Companies)
INSERT INTO Vendor (name, status)
VALUES
    ('Penguin Random House', true),
    ('HarperCollins', true),
    ('Simon & Schuster', true),
    ('Macmillan Publishers', true),
    ('Hachette Book Group', true),
    ('Oxford University Press', true),
    ('Random House', true),
    ('Vintage Books', true),
    ('Houghton Mifflin Harcourt', true),
    ('Scholastic Corporation', true);


-- Insert 10 Books with Authors and Vendors
INSERT INTO Book (ISBN, title, year, books_quantity, borrowed_books, books_left, genre, status, vendor_id)
VALUES
    (9780451524935, '1984', '1949-06-08', 500, 50, 450, 'Dystopian', true, 1),
    (9780385472579, 'The Handmaid''s Tale', '1985-02-17', 400, 40, 360, 'Dystopian', true, 2),
    (9781451673319, 'Fahrenheit 451', '1953-10-19', 350, 30, 320, 'Science Fiction', true, 3),
    (9780061120084, 'Brave New World', '1932-09-01', 300, 20, 280, 'Science Fiction', true, 4),
    (9780142427288, 'Twenty Thousand Leagues Under the Sea', '1870-01-01', 200, 10, 190, 'Adventure', true, 5),
    (9780199536409, 'Crime and Punishment', '1866-01-01', 300, 30, 270, 'Psychological Fiction', true, 6),
    (9781505370295, 'Great Expectations', '1861-12-01', 250, 20, 230, 'Classic', true, 7),
    (9780062572903, 'Murder on the Orient Express', '1934-01-01', 150, 10, 140, 'Mystery', true, 8),
    (9780544003415, 'Harry Potter and the Sorcerer''s Stone', '1997-06-26', 800, 100, 700, 'Fantasy', true, 9),
    (9780061120084, 'To Kill a Mockingbird', '1960-07-11', 600, 50, 550, 'Classic', true, 10);

-- Link the Books to Authors
INSERT INTO book_author (book_id, author_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);