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
    (9780061120084, 'To Kill a Mockingbird', '1960-07-11', 600, 50, 550, 'Classic', true, 10),
    (9780062316097, 'Animal Farm', '1945-08-17', 400, 40, 360, 'Political Satire', true, 1),
    (9780385490818, 'The Testaments', '2019-09-10', 300, 30, 270, 'Dystopian', true, 2),
    (9780345342966, 'The Martian Chronicles', '1950-05-01', 250, 20, 230, 'Science Fiction', true, 3),
    (9780060850524, 'Island', '1962-04-26', 200, 10, 190, 'Utopian Fiction', true, 4),
    (9781631067286, 'Around the World in 80 Days', '1873-01-30', 350, 35, 315, 'Adventure', true, 5),
    (9780140449136, 'The Brothers Karamazov', '1880-11-26', 300, 30, 270, 'Philosophical Fiction', true, 6),
    (9780141439563, 'David Copperfield', '1850-05-01', 400, 40, 360, 'Classic', true, 7),
    (9780062791838, 'The Murder of Roger Ackroyd', '1926-06-19', 200, 20, 180, 'Mystery', true, 8),
    (9780439554930, 'Harry Potter and the Chamber of Secrets', '1998-07-02', 750, 75, 675, 'Fantasy', true, 9),
    (9780061120084, 'Go Set a Watchman', '2015-07-14', 500, 50, 450, 'Classic', true, 10),
    (9780486270661, 'Animal Farm', '1945-08-17', 400, 40, 360, 'Political Satire', true, 1),
    (9780393351860, 'The Handmaid''s Tale', '1985-02-17', 400, 40, 360, 'Dystopian', true, 2),
    (9780062079971, 'Fahrenheit 451', '1953-10-19', 350, 30, 320, 'Science Fiction', true, 3),
    (9780060890095, 'Brave New World', '1932-09-01', 300, 20, 280, 'Science Fiction', true, 4),
    (9780486270623, 'Twenty Thousand Leagues Under the Sea', '1870-01-01', 200, 10, 190, 'Adventure', true, 5),
    (9780141395036, 'Crime and Punishment', '1866-01-01', 300, 30, 270, 'Psychological Fiction', true, 6),
    (9780486426791, 'Great Expectations', '1861-12-01', 250, 20, 230, 'Classic', true, 7),
    (9780451527813, 'Murder on the Orient Express', '1934-01-01', 150, 10, 140, 'Mystery', true, 8),
    (9780545790352, 'Harry Potter and the Sorcerer''s Stone', '1997-06-26', 800, 100, 700, 'Fantasy', true, 9),
    (9780743273565, 'To Kill a Mockingbird', '1960-07-11', 600, 50, 550, 'Classic', true, 10);

INSERT INTO book_author (book_id, author_id)
VALUES
    (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
    (6, 6), (7, 7), (8, 8), (9, 9), (10, 10),
    (11, 1), (12, 2), (13, 3), (14, 4), (15, 5),
    (16, 6), (17, 7), (18, 8), (19, 9), (20, 10),
    (21, 1), (22, 2), (23, 3), (24, 4), (25, 5),
    (26, 6), (27, 7), (28, 8), (29, 9), (30, 10);