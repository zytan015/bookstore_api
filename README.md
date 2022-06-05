#  **Bookstore_API**

Creating a bookstore RESTFUL API using JAVA Spring Boot with the following information.

A **book** has the following fields:

Field	Type/Format	Description

isbn	string	(ISBN of book)

title	String	(Book title)

authors	Array of author	(Book Author(s))

year	number / int32	(Year of publication)

price	number / double	(Price of Book)

genre	string	(Genre of Book)

An **author** has the following fields: 

Field	Type/Format	Description

name	string	(Author name)

birthday	string/date	(Date of birth of Author)

# **Add Data**
Data is stored in MySQL. 

###### **books table**
insert into bookstore.books (isbn, genre, price, title, year)
value ("9781234567897", "fantasy", "35.90", "Parrot Of The Void", "1970")

insert into bookstore.books (isbn, genre, price, title, year)
value ("9782123456803", "fiction", "12.90", "Spiders Of Fire", "1999")

insert into bookstore.books (isbn, genre, price, title, year)
value ("9928123456908", "horror", "22.85", "Annabelle", "2000")

###### **authors table**
insert into bookstore.author (name, birthday)
value ("Eric", "1977-10-10")

insert into bookstore.author (name, birthday)
value ("Sussie", "1980-2-4")

insert into bookstore.author (name, birthday)
value ("George Eliot", "1955-5-22")

insert into bookstore.author ( name, birthday)
value ("Emily", "1969-11-1")

###### **book_author table**
insert into bookstore.book_author (author_id, book_id)
value ("3", "1")

insert into bookstore.book_author (author_id, book_id)
value ("1", "1")

insert into bookstore.book_author (author_id, book_id)
value ("2", "2")

insert into bookstore.book_author (author_id, book_id)
value ("1", "3")


# **Spring Authorization**

Username: user

Password: password
