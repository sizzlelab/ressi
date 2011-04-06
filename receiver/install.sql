CREATE USER ressi IDENTIFIED BY 'ressi_password';

CREATE DATABASE research_test CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE research_development CHARACTER SET utf8 COLLATE utf8_general_ci; 
CREATE DATABASE research_production CHARACTER SET utf8 COLLATE utf8_general_ci;

GRANT all privileges ON research_development.* TO 'ressi'@'localhost' IDENTIFIED BY 'ressi_password';
GRANT all privileges ON research_production.* TO 'ressi'@'localhost' IDENTIFIED BY 'ressi_password';
GRANT all privileges ON research_test.* TO 'ressi'@'localhost' IDENTIFIED BY 'ressi_password';
