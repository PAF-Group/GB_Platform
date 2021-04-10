#Users DB creation query
CREATE DATABASE Users;

#User Agreement table creation
CREATE TABLE UserAgreement (
  agreement_id VARCHAR(10) NOT NULL,
  description TEXT(1000),
  file_location VARCHAR(2083),
  created_at  DATE NOT NULL,
  updated_at DATE NOT NULL,
  PRIMARY KEY(agreement_id)
);

#Researcher table creation
CREATE TABLE Reseacher (
  user_id VARCHAR(10) NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20),
  user_phone VARCHAR(15),
  user_agreement VARCHAR(10) NOT NULL,
  user_email VARCHAR(100) NOT NULL,
  password VARCHAR(20) NOT NULL,
  acc_status VARCHAR(10) NOT NULL,
  created_at  DATE NOT NULL,
  updated_at DATE NOT NULL,
  PRIMARY KEY(user_id),
  CONSTRAINT researcher_fk FORIEGN KEY(user_agreement) 
  REFERENCES UserAgreement(agreement_id)
  ON UPDATE CASCADE
);

#Researcher - Research Fields table creation
CREATE TABLE ReseacherFields (
  user_id VARCHAR(10) NOT NULL,
  field VARCHAR(20) NOT NULL,
  created_at  DATE NOT NULL,
  updated_at DATE NOT NULL,
  PRIMARY KEY(user_id, field),
  CONSTRAINT researcher_fields_fk FORIEGN KEY(user_id) 
  REFERENCES Researcher(user_id)
  ON UPDATE CASCADE
  ON DELETE CASCADE
);

#Funding Body table creation
CREATE TABLE Funder (
  user_id VARCHAR(10) NOT NULL,
  name VARCHAR(30) NOT NULL,
  tele VARCHAR(15),
  type VARCHAR(20),
  address VARCHAR(100),
  user_agreement VARCHAR(10),
  user_email VARCHAR(100) NOT NULL,
  password VARCHAR(20) NOT NULL,
  acc_status VARCHAR(10) NOT NULL,
  created_at  DATE NOT NULL,
  updated_at DATE NOT NULL,
  PRIMARY KEY(user_id),
  CONSTRAINT funder_fk FORIEGN KEY(user_agreement) 
  REFERENCES UserAgreement(agreement_id)
  ON UPDATE CASCADE
);

#Buyer table creation
CREATE TABLE Buyer (
  user_id VARCHAR(10) NOT NULL,
  name VARCHAR(40) NOT NULL,
  buyer_phone VARCHAR(15),
  user_agreement VARCHAR(10),
  user_email VARCHAR(100) NOT NULL,
  password VARCHAR(20) NOT NULL,
  acc_status VARCHAR(10) NOT NULL,
  created_at  DATE NOT NULL,
  updated_at DATE NOT NULL,
  PRIMARY KEY(user_id),
  CONSTRAINT buyer_fk FORIEGN KEY(user_agreement) 
  REFERENCES UserAgreement(agreement_id)
  ON UPDATE CASCADE
);

#Buyer - Interested categories table creation
CREATE TABLE BuyerCategories (
  user_id VARCHAR(10) NOT NULL,
  category VARCHAR(20),
  created_at  DATE NOT NULL,
  updated_at DATE NOT NULL,
  PRIMARY KEY(user_id, category),
  CONSTRAINT buyer_category_fk FORIEGN KEY(user_id) 
  REFERENCES Buyer(user_id)
  ON UPDATE CASCADE
  ON DELETE CASCADE
);

#GB Management table creation
CREATE TABLE GBMemeber (
  user_id VARCHAR(10) NOT NULL,
  emp_id VARCHAR(10) NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20),
  type VARCHAR(20),
  phone VARCHAR(15),
  user_email VARCHAR(100) NOT NULL,
  password VARCHAR(20) NOT NULL,
  acc_status VARCHAR(10) NOT NULL,
  created_at  DATE NOT NULL,
  updated_at DATE NOT NULL,
  PRIMARY KEY(user_id)
);

#Administrator table creation
CREATE TABLE Administrator(
  user_id VARCHAR(10) NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20),
  phone VARCHAR(15),
  user_email VARCHAR(100) NOT NULL,
  password VARCHAR(20) NOT NULL,
  acc_status VARCHAR(10) NOT NULL,
  created_at  DATE NOT NULL,
  updated_at DATE NOT NULL,
  PRIMARY KEY(user_id)
);
