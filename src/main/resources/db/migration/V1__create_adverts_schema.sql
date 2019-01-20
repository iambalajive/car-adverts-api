create table FUEL_TYPE (
      ID int not null primary key,
      FUEL_TYPE_DESC varchar not null
);

create table VEHICLE_CONDITION (
    ID int not null primary key,
    CONDITION varchar not null
);

create table CAR_ADVERTS (
      ID UUID not null primary key,
      FUEL_TYPE_ID int not null,
      TITLE varchar not null,
      PRICE int not null,
      CONDITION_TYPE_ID int not null,
      MILEAGE int null,
      FIRST_REG date null
);



ALTER TABLE CAR_ADVERTS ADD CONSTRAINT CAR_ADVERTS_FK1 FOREIGN KEY (FUEL_TYPE_ID) REFERENCES FUEL_TYPE (id);
ALTER TABLE CAR_ADVERTS ADD CONSTRAINT CAR_ADVERTS_FK2 FOREIGN KEY (CONDITION_TYPE_ID) REFERENCES VEHICLE_CONDITION (id);


