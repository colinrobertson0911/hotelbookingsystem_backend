insert into users(userId, username, password, firstName, lastName) values (USER_SEQ.nextval, 'admin1', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Administrator', 'Administrator');
insert into users(userId, username, password, firstName, lastName) values (USER_SEQ.nextval, 'hotelOwner1', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Tom', 'Smith');
insert into users(userId, username, password, firstName, lastName) values (USER_SEQ.nextval, 'hotelOwner2', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Mike', 'Brown');
insert into users(userId, username, password, firstName, lastName) values (USER_SEQ.nextval, 'customer1', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Harry', 'Wilson');
insert into users(userId, username, password, firstName, lastName) values (USER_SEQ.nextval, 'customer2', '$2a$12$ax483KXnei/I7jStkI.xwe/EMXubNNN6/fhqzmXIRe3nY8pfhbEfq', 'Sally', 'Wilson');

insert into customer(userId, address, email) values (4, '1, somewhere, Glasgow, g24 0nt', 'harry@email.com');
insert into customer(userId, address, email) values (5, '1, somewhere, Glasgow, g24 0nt', 'sally@email.com');

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
insert into review(reviewId, hotelId, userId, message, score) values (REVIEW_SEQ.nextval, 1, 4, 'The hotel was ok', 4);
insert into review(reviewId, hotelId, userId, message, score) values (REVIEW_SEQ.nextval, 1, 4, 'The hotel was brilliant', 5);
insert into review(reviewId, hotelId, userId, message, score) values (REVIEW_SEQ.nextval, 1, 4, 'The hotel was dirty', 2);
insert into review(reviewId, hotelId, userId, message, score) values (REVIEW_SEQ.nextval, 1, 4, 'The hotel was awful', 1);