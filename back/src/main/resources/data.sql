INSERT INTO ROLE(id, name) VALUES (1, 'USER');

INSERT INTO APP_USER(id, name, username, password) VALUES (1, 'Nada Zaric', 'nada', 'dadb88e6762e8c091d58053acb3f322d27b95cd7a2ba3b3a9b9bcd45aa852f899bcd55e16b29cdea9d48f746fa1f2c26f744787248341bdb41a9842c17b52098');
INSERT INTO APP_USER_ROLES(user_id, roles_id) VALUES (1, 1);