DROP TABLE IF EXISTS
	instructor;
    
CREATE TABLE instructor (
	ID INTEGER NOT NULL,
	firstName VARCHAR(20), 
	lastName VARCHAR(20), 
	email VARCHAR(50),
    passW VARCHAR(50),
    PRIMARY KEY (ID)
	);

DROP TABLE IF EXISTS
	course;

CREATE TABLE course (
	ID INTEGER NOT NULL,
    department VARCHAR(50),
    coursenumber INTEGER NOT NULL,
    coursename VARCHAR(50),
    credits INTEGER NOT NULL,
    section INTEGER NOT NULL,
    instructor VARCHAR(50),
    offeryear INTEGER NOT NULL,
    offerquarter VARCHAR(50),
    maxroom INTEGER NOT NULL,
    consent tinyint,
    PRIMARY KEY (ID)
    );

DROP TABLE IF EXISTS
	student;
    
CREATE TABLE student (
	ID INTEGER NOT NULL,
	firstName VARCHAR(20), 
	lastName VARCHAR(20), 
	email VARCHAR(50),
    passW VARCHAR(50),
    restriction tinyint,
    type VARCHAR(50),
    PRIMARY KEY (ID)
	);
    
    
DROP TABLE IF EXISTS
	roster;
    
CREATE TABLE roster (
	ID INTEGER NOT NULL,
	courseoneID INTEGER NOT NULL, 
	coursetwoID INTEGER NOT NULL, 
	coursethreeID INTEGER NOT NULL,
    gradeone VARCHAR(20),
    gradetwo VARCHAR(20),
    gradethree VARCHAR(20),
    PRIMARY KEY (ID)
	);
    
    

	
    
    
    
    
	
    
    


