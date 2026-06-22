INSERT INTO bookings (user_id,event_id,quantity,status,total_price) VALUES
(1,1,2,'CONFIRMED',240.00),
(2,2,1,'CONFIRMED',200.00),
(3,3,3,'PENDING',150.00),
(4,4,1,'CONFIRMED',80.00),
(5,5,2,'CONFIRMED',180.00),
(6,6,4,'PENDING',240.00),
(7,7,2,'CONFIRMED',60.00),
(8,8,1,'CONFIRMED',70.00),
(9,9,1,'CONFIRMED',250.00),
(10,10,2,'PENDING',200.00);


INSERT INTO booking_history (booking_id,status) VALUES
(1,'CREATED'),
(2,'CREATED'),
(3,'CREATED'),
(4,'CREATED'),
(5,'CREATED'),
(6,'CREATED'),
(7,'CREATED'),
(8,'CREATED'),
(9,'CREATED'),
(10,'CREATED');