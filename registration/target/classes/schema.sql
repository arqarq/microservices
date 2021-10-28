CREATE TABLE CUSTOMER (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY NOT NULL,
  FIRST_NAME VARCHAR(255) NOT NULL,
  LAST_NAME VARCHAR(255) NOT NULL,
  ACTIVE BOOLEAN
);


CREATE TABLE ADDRESS (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY NOT NULL,
  CITY VARCHAR(255) NOT NULL,
  STREET VARCHAR(255) NOT NULL,
  CUSTOMER_ID BIGINT NOT NULL
);
