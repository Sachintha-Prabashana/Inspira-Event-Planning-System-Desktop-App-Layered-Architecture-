DROP DATABASE IF EXISTS Inspira;
create database Inspira;

use Inspira;

create table Customer(
                         customer_id varchar(10) primary key,
                         cust_title varchar(5),
                         first_name varchar(30) not null,
                         last_name varchar(30) not null,
                         nic varchar(12) not null,
                         email varchar(60),
                         registration_date date
);

CREATE TABLE Event (
                       event_id VARCHAR(10) PRIMARY KEY,
                       booking_id VARCHAR(10) NOT NULL,
                       event_type VARCHAR(30) NOT NULL,
                       event_name VARCHAR(60) NOT NULL,
                       budget DECIMAL(10,2) NOT NULL,
                       venue VARCHAR(30) NOT NULL,
                       event_date DATE NOT NULL,      -- stores only the date
                       foreign key(booking_id) references Booking(booking_id) on update cascade on delete cascade

);




create table Booking(
                        booking_id varchar(10) primary key,
                        customer_id varchar(10) not null,
                        capacity int not null,
                        venue varchar(30) not null,
                        booking_date Date not null,
                        foreign key(customer_id) references Customer(customer_id) on update cascade on delete cascade
);



create table Payment(
                        payment_id varchar(10) primary key,
                        date date not null,
                        amount decimal(10,2) not null,
                        payment_status varchar(30) not null,
                        booking_id varchar(10) not null,
                        foreign key(booking_id) references Booking(booking_id) on update cascade on delete cascade
);

create table Employee(
                         employee_id varchar(10) primary key,
                         first_name varchar(30) not null,
                         last_name varchar(30) not null,
                         position varchar(20) not null,
                         Join_date Date not null,
                         salary decimal(10,2) not null,
                         email varchar(60) not null,
                         booking_id varchar(10) not null,
                         foreign key(booking_id) references Booking(booking_id) on update cascade on delete cascade
);

create table Supplier(
                         supplier_id varchar(10) primary key,
                         supplier_name varchar(60),
                         email varchar(60) not null

);

create table Item(
                     item_id varchar(10) primary key,
                     item_name varchar(20) not null,
                     description varchar(80),
                     cost decimal(6,2) not null,
                     quantity int not null,
                     supplier_id varchar(10),
                     foreign key(supplier_id) references Supplier(supplier_id) on update cascade on delete cascade
);

create table EventSupplier(
                              event_id varchar(10) not null,
                              supplier_id varchar(10) not null,
                              itemQty int not null,
                              price decimal(5,2) not null,
                              CONSTRAINT FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id) ON UPDATE CASCADE ON DELETE CASCADE,
                              CONSTRAINT FOREIGN KEY (event_id) REFERENCES Event(event_id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table Service(
                        service_id varchar(10) primary key,
                        price decimal(6,2) not null,
                        service_type varchar(20) not null
);


create table BookingService(
                               booking_id varchar(10) not null,
                               service_id varchar(10) not null,
                               date date not null,
                               CONSTRAINT FOREIGN KEY (booking_id) REFERENCES Booking(booking_id) ON UPDATE CASCADE ON DELETE CASCADE,
                               CONSTRAINT FOREIGN KEY (service_id) REFERENCES Service(service_id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table User(
                     user_id varchar(10) primary key,
                     first_name varchar(30) not null,
                     last_name varchar(30) not null,
                     username varchar(30) not null,
                     email varchar(60) not null,
                     password varchar(30) not null
);
