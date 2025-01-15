INSERT INTO ROLE(id, name) VALUES (1, 'USER');

INSERT INTO APP_USER(id, name, username, password) VALUES (1, 'Nada Zaric', 'nada', '$2a$12$7hc0JeJJx4ynUkfw1N528.pjD1/pIxXUEuf2mZhyANcJl6Td7vN3C');
INSERT INTO APP_USER_ROLES(user_id, roles_id) VALUES (1, 1);