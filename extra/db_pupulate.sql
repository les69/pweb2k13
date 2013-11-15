-- adding users

INSERT INTO PWEB.USERS (USERNAME, PASSWORD) VALUES ('sec', 'exterminate');
INSERT INTO PWEB.USERS (USERNAME, PASSWORD) VALUES ('davros', 'dalek');
INSERT INTO PWEB.USERS (USERNAME, PASSWORD) VALUES ('boxxy', 'yousee');
INSERT INTO PWEB.USERS (USERNAME, PASSWORD) VALUES ('paolo', 'rossi');
INSERT INTO PWEB.USERS (USERNAME, PASSWORD) VALUES ('jast', 'rotcod');
INSERT INTO PWEB.USERS (USERNAME, PASSWORD) VALUES ('caan', 'timewar');
INSERT INTO PWEB.USERS (USERNAME, PASSWORD) VALUES ('marco', 'polo');

-- adding groups

INSERT INTO PWEB.GROUPS ("NAME", ACTIVE, ID_OWNER) VALUES ('Cult of Skaro', true, 1);
INSERT INTO PWEB.GROUPS ("NAME", ACTIVE, ID_OWNER) VALUES ('Supreme beings', true, 2);
INSERT INTO PWEB.GROUPS ("NAME", ACTIVE, ID_OWNER) VALUES ('Sphere', true, 3);

-- adding people to groups

INSERT INTO PWEB.GROUPUSER (ID_GROUP, ID_USER, ACTIVE) VALUES (1, 1, true);
INSERT INTO PWEB.GROUPUSER (ID_GROUP, ID_USER, ACTIVE) VALUES (2, 2, true);
INSERT INTO PWEB.GROUPUSER (ID_GROUP, ID_USER, ACTIVE) VALUES (3, 3, true);

-- adding invites

INSERT INTO PWEB.INVITE (ID_GROUP, ID_USER, INVITE_DATE) VALUES (1, 5, '2013-11-10');
INSERT INTO PWEB.INVITE (ID_GROUP, ID_USER, INVITE_DATE) VALUES (2, 1, '2013-11-12');
INSERT INTO PWEB.INVITE (ID_GROUP, ID_USER, INVITE_DATE) VALUES (2, 5, '2013-11-12');
INSERT INTO PWEB.INVITE (ID_GROUP, ID_USER, INVITE_DATE) VALUES (3, 4, '2013-11-13');

-- adding posts

INSERT INTO PWEB.POST (VISIBLE, DATE_POST, MESSAGE, FILE_STRING, ID_GROUP, ID_USER) VALUES (DEFAULT, CURRENT_TIMESTAMP, 'We must exterminate the doctor!!', NULL, 1, 1);
INSERT INTO PWEB.POST (VISIBLE, DATE_POST, MESSAGE, FILE_STRING, ID_GROUP, ID_USER) VALUES (DEFAULT, CURRENT_TIMESTAMP, 'I obey. Exterminate!', NULL, 1, 6);
INSERT INTO PWEB.POST (VISIBLE, DATE_POST, MESSAGE, FILE_STRING, ID_GROUP, ID_USER) VALUES (DEFAULT, CURRENT_TIMESTAMP, 'Ohai! I''m Boxxy, you see?', NULL, 3, 3);
