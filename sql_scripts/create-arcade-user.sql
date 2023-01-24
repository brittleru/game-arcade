drop user if exists arcadeadmin@localhost;

flush privileges;

CREATE USER 'arcadeadmin'@'localhost' IDENTIFIED BY 'arcadeadmin';

GRANT ALL PRIVILEGES ON * . * TO 'arcadeadmin'@'localhost';

ALTER USER 'arcadeadmin'@'localhost' IDENTIFIED BY 'arcadeadmin';