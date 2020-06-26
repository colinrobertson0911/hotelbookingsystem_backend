insert into users(userId, username, password, firstName, lastName, address, email) values (USER_SEQ.nextval, 'admin1', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Administrator', 'Administrator', 'Edinburgh', 'admin@email.com');
insert into users(userId, username, password, firstName, lastName, address, email) values (USER_SEQ.nextval, 'hotelOwner1', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Tom', 'Smith', '1, nowhere, London', 'owner1@email.com');
insert into users(userId, username, password, firstName, lastName, address, email) values (USER_SEQ.nextval, 'hotelOwner2', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Mike', 'Brown', '1, everywhere, Paris', 'owner2@email.com');
insert into users(userId, username, password, firstName, lastName, address, email) values (USER_SEQ.nextval, 'customer1', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Harry', 'Wilson', '1, somewhere, Glasgow, g24 0nt', 'harry@email.com');
insert into users(userId, username, password, firstName, lastName, address, email) values (USER_SEQ.nextval, 'customer2', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Sally', 'Wilson', '1, somewhere, Glasgow, g24 0nt', 'sally@email.com');

insert into customer(userId) values (4);
insert into customer(userId) values (5);

insert into hotelOwner(userId) values (2);
insert into hotelOwner(userId) values (3);

insert into role(roleId, roleName) values (ROLE_SEQ.nextval, 'ROLE_ADMIN');
insert into role(roleId, roleName) values (ROLE_SEQ.nextval, 'ROLE_HOTELOWNER');
insert into role(roleId, roleName) values (ROLE_SEQ.nextval, 'ROLE_CUSTOMER');

insert into user_role(userId, roleId) values (1,1);
insert into user_role(userId, roleId) values (2,2);
insert into user_role(userId, roleId) values (3,2);
insert into user_role(userId, roleId) values (4,3);
insert into user_role(userId, roleId) values (5,3);

insert into hotel(hotelId, hotelName, numOfRooms, address, postcode, city, amenities, starRating, airportTransfers, transferprice, verified) values (HOTEL_SEQ.nextval, 'Travelodge Glasgow', 2,'1 main street', 'g43 6pq', 'Glasgow','none', 3, true, 20, true);
insert into hotel(hotelId, hotelName, numOfRooms, address, postcode, city, amenities, starRating, airportTransfers, transferprice, verified) values (HOTEL_SEQ.nextval, 'Yotel', 1,'some street','EH71 7FA', 'Edinburgh','bowling alley', 4, true, 20, true);
insert into hotel(hotelId, hotelName, numOfRooms, address, postcode, city, amenities, starRating, airportTransfers, transferprice, verified) values (HOTEL_SEQ.nextval, 'Radisson Blue', 2,'123 argyle street','G3 6OP', 'Glasgow','Conference Rooms, Bars, Near Central Station', 4, false, 20, true);
insert into hotel(hotelId, hotelName, numOfRooms, address, postcode, city, amenities, starRating, airportTransfers, transferprice, verified) values (HOTEL_SEQ.nextval, 'Radisson Red', 1,'456 argyle street','G3 6RP', 'Glasgow','Conference Rooms, Bars, Near Central Station', 4, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Waelchi Inc', 1, '8392 Northfield Place', 'G1 567', 'Syrup - Monin, Amaretta', 'Baratan', 5, true, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Collins-Cartwright', 1, '9 Golf Junction', 'G1 567', 'Broom And Brush Rack Black', 'Xinglongquan', 2, true, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Monahan-Kshlerin', 1, '8 High Crossing Junction', 'G1 567', 'Bread - Frozen Basket Variety', 'Bayan Ewenke Minzu', 6, false, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Reynolds Inc', 1, '501 Judy Street', 'G1 567', 'Ostrich - Prime Cut', 'Duut', 5, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Rogahn-McCullough', 1, '54 Southridge Center', 'G1 567', 'Beef - Bones, Marrow', 'Aktogay', 3, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Collier, Champlin and Robel', 1, '1848 Knutson Point', '865-0005', 'Sprouts Dikon', 'Tamana', 10, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Kiehn-Pfannerstill', 1, '66 Derek Lane', '4600', 'Chicken - White Meat, No Tender', 'Kranj', 2, true, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Fahey-Bergnaum', 1, '0 Warrior Hill', 'G1 567', 'Pail - 4l White, With Handle', 'Marsh Harbour', 6, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Zboncak, Ullrich and Lockman', 1, '3438 Anthes Hill', 'G1 567', 'Beef - Ox Tongue, Pickled', 'Pirot', 4, false, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Schimmel, Williamson and Satterfield', 1, '6 Springs Street', 'G1 567', 'Beans - Navy, Dry', 'Krasnotorka', 1, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Klein-Beier', 1, '008 Comanche Avenue', '442895', 'Bread - Roll, Whole Wheat', 'Serdobsk', 3, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Larson, Ortiz and Harris', 1, '259 Mayer Place', 'G1 567', 'Lemonade - Island Tea, 591 Ml', 'Janeng', 3, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Gorczany-Erdman', 1, '7999 Northridge Center', 'G1 567', 'Muffin - Mix - Bran And Maple 15l', 'Chornobay', 6, true, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Torp LLC', 1, '102 Trailsway Hill', 'G1 567', 'Bols Melon Liqueur', 'Bīrganj', 8, false, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Deckow-Barton', 1, '63229 Sunnyside Terrace', '4770-370', 'Olive - Spread Tapenade', 'Alminhas', 9, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Mayer LLC', 1, '8 Lakewood Terrace', 'G1 567', 'Juice - Prune', 'Moba', 9, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Kuphal and Sons', 1, '298 Springs Trail', '05-180', 'Carbonated Water - White Grape', 'Pomiechówek', 10, true, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Konopelski-Renner', 1, '12 Rockefeller Avenue', 'G1 567', 'Cheese - Mozzarella, Buffalo', 'Tanahgrogot', 10, false, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Prosacco Group', 1, '3 Sachtjen Park', 'G1 567', 'Nut - Walnut, Pieces', 'Ñuñoa', 10, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Lynch, Feeney and Huels', 1, '316 Kings Way', 'G1 567', 'Mustard - Pommery', 'Tiechang', 4, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Champlin LLC', 1, '22 Schmedeman Court', 'G1 567', 'Lettuce - Baby Salad Greens', 'Bayḩān', 8, false, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Tremblay LLC', 1, '9383 Crowley Place', '95710', 'Mortadella', 'San Francisco', 5, false, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Hermiston-Schoen', 1, '19386 Burning Wood Pass', 'G1 567', 'Snapple Lemon Tea', 'Boju', 5, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Turcotte, Hyatt and Trantow', 1, '744 Daystar Crossing', '60406 CEDEX', 'Cinnamon - Stick', 'Noyon', 3, false, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Mayer, Kozey and McCullough', 1, '84 Havey Circle', '37-543', 'Glaze - Apricot', 'Laszki', 6, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Halvorson Group', 1, '67950 Blue Bill Park Court', 'G1 567', 'Ham - Procutinni', 'Peraía', 7, true, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Goldner, Labadie and Armstrong', 1, '47 Kim Court', 'G1 567', 'Mikes Hard Lemonade', 'Ojos de Agua', 10, true, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Kutch, Upton and Raynor', 1, '5997 Sachs Trail', 'G1 567', 'Compound - Orange', 'Meishan', 8, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Mills-Johnson', 1, '0 Harper Circle', '427650', 'Smoked Paprika', 'Krasnogorskoye', 1, true, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Funk LLC', 1, '52491 Lakeland Alley', 'G1 567', 'Clams - Bay', 'Gwio Kura', 5, true, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Nienow, Ruecker and Fay', 1, '6 Oakridge Trail', 'G1 567', 'Lamb - Whole, Fresh', 'Kyurdarmir', 6, false, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Fritsch-Wiegand', 1, '79 Paget Point', '96700-000', 'Cheese - Wine', 'São Jerônimo', 8, true, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Pouros-Nader', 1, '1 Crowley Parkway', 'G1 567', 'Curry Powder Madras', 'Renhe', 8, true, 20, false);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'D''Amore-Harber', 1, '57677 Stone Corner Road', 'G1 567', 'Milk Powder', 'Lame', 2, false, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Parisian-Davis', 1, '6 Jenna Road', 'G1 567', 'Coconut Milk - Unsweetened', 'Ak’ordat', 4, true, 20, true);
insert into hotel (hotelId, hotelName, numOfRooms, address, postcode, amenities, city, starRating, airportTransfers, transferPrice, verified) values (HOTEL_SEQ.nextval, 'Padberg-Schowalter', 1, '73 Mallory Point', 'G1 567', 'Cups 10oz Trans', 'Zaporizhzhya', 10, true, 20, true);

insert into room(roomId, roomType, price) values (ROOM_SEQ.nextval, 'STANDARD', '60.00');
insert into room(roomId, roomType, price) values (ROOM_SEQ.nextval, 'LUXURY', '80.00');
insert into room(roomId, roomType, price) values (ROOM_SEQ.nextval, 'DELUXE', '100.00');
insert into room(roomId, roomType, price) values (ROOM_SEQ.nextval, 'SUITE', '120.00');

insert into hotel_room(hotel_hotelId, room_roomId) values (1,1);
insert into hotel_room(hotel_hotelId, room_roomId) values (1,4);
insert into hotel_room(hotel_hotelId, room_roomId) values (2,2);
insert into hotel_room(hotel_hotelId, room_roomId) values (2,3);
insert into hotel_room(hotel_hotelId, room_roomId) values (3,1);
insert into hotel_room(hotel_hotelId, room_roomId) values (4,1);

insert into hotelOwner_hotel(userId, hotelId) values (2, 1);
insert into hotelOwner_hotel(userId, hotelId) values (2, 2);
insert into hotelOwner_hotel(userId, hotelId) values (3, 3);
insert into hotelOwner_hotel(userId, hotelId) values (3, 4);

insert into booking(bookingId, checkInDate, checkOutDate, roomType, extras, hotel, roomPrice, extrasPrice, totalPrice) values (BOOKING_SEQ.nextval, TO_DATE('2020/07/23', 'yyyy/mm/dd'), TO_DATE('2020/07/27', 'yyyy/mm/dd'), 'STANDARD', 'AIRPORTTRANSFER', 'Travelodge Glasgow', '60.00', '20.00','440.00');
insert into booking(bookingId, checkInDate, checkOutDate, roomType, extras, hotel, roomPrice, extrasPrice, totalPrice) values (BOOKING_SEQ.nextval, TO_DATE('2020/07/15', 'yyyy/mm/dd'), TO_DATE('2020/07/25', 'yyyy/mm/dd'), 'STANDARD', 'AIRPORTTRANSFER', 'Travelodge Glasgow', '60.00', '20.00','440.00');
insert into booking(bookingId, checkInDate, checkOutDate, roomType, extras, hotel, roomPrice, extrasPrice, totalPrice) values (BOOKING_SEQ.nextval, TO_DATE('2020/07/20', 'yyyy/mm/dd'), TO_DATE('2020/07/30', 'yyyy/mm/dd'), 'STANDARD', 'NO_EXTRAS', 'Radisson Blue', '60.00', '0.00','540.00');
insert into booking(bookingId, checkInDate, checkOutDate, roomType, extras, hotel, roomPrice, extrasPrice, totalPrice) values (BOOKING_SEQ.nextval, TO_DATE('2020/07/20', 'yyyy/mm/dd'), TO_DATE('2020/07/30', 'yyyy/mm/dd'), 'STANDARD', 'NO_EXTRAS', 'Radisson Blue', '60.00', '0.00','540.00');

insert into hotel_booking(hotelId, bookingId) values (1, 1);
insert into hotel_booking(hotelId, bookingId) values (1, 2);
insert into hotel_booking(hotelId, bookingId) values (3, 3);
insert into hotel_booking(hotelId, bookingId) values (3, 4);

insert into customer_booking(userId, bookingId) values (4, 1);
insert into customer_booking(userId, bookingId) values (4, 2);
insert into customer_booking(userId, bookingId) values (4, 3);
insert into customer_booking(userId, bookingId) values (4, 4);

insert into review(reviewId, hotelId, userId, message, score) values (REVIEW_SEQ.nextval, 1, 4, 'The hotel was great', 5);
insert into review(reviewId, hotelId, userId, message, score) values (REVIEW_SEQ.nextval, 1, 5, 'The hotel was ok', 4);
insert into review(reviewId, hotelId, userId, message, score) values (REVIEW_SEQ.nextval, 2, 4, 'The hotel was brilliant', 5);
insert into review(reviewId, hotelId, userId, message, score) values (REVIEW_SEQ.nextval, 3, 5, 'The hotel was dirty', 2);
insert into review(reviewId, hotelId, userId, message, score) values (REVIEW_SEQ.nextval, 4, 4, 'The hotel was awful', 1);
