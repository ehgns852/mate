-- upload file
INSERT INTO upload_file(file_path, store_filename, upload_filename)
VALUES ('abcdefdafdf', 'vcxvuzivgusdif', 'vzxcviohsdofhodsfn');

INSERT INTO upload_file(file_path, store_filename, upload_filename)
VALUES ('vocxivhoihox', 'dsoivhosvoinvozv', 'cxohvoxhovoicv');

INSERT INTO upload_file(file_path, store_filename, upload_filename)
VALUES ('vxcmvxpcvisiodf', 'voixcnovxniocnv', 'vioohioihsiodhfidsf');

INSERT INTO upload_file(file_path, store_filename, upload_filename)
VALUES ('dspfmodisfiojfd', 'xcviuxcbivbdsf', 'vosouvhdshoifsofdso');


INSERT INTO user_profile(city, street, zipcode, age, gender, nick_name, phone_number, provider, provider_id, upload_file_id)
VALUES ('seoul', 'gangnam-ro', 12345, 22, 'MAN', 'choi', '010-1234-5678', 'kakao', '12345', 1);

INSERT INTO user_profile(city, street, zipcode, age, gender, nick_name, phone_number, provider, provider_id, upload_file_id)
VALUES ('incheon', 'yeonsu-ro', 23456, 30, 'MAN', 'park', '010-2345-5678', 'google', '23456', 2);

INSERT INTO user_profile(city, street, zipcode, age, gender, nick_name, phone_number, provider, provider_id, upload_file_id)
VALUES ('daegu', 'dalseo-ro', 77895, 25, 'WOMAN', 'kim', '010-9999-9999', 'kakao', '13457', 3);

INSERT INTO user_profile(city, street, zipcode, age, gender, nick_name, phone_number, provider, provider_id, upload_file_id)
VALUES ('busan', 'haeun-ro', 14552, 35, 'WOMAN', 'seo', '010-4567-7895', 'facebook', '51281', 4);



INSERT INTO member(email, role, created_date, updated_date, user_profile_id)
VALUES ('test@gmail.com', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

INSERT INTO member(email, role, created_date, updated_date, user_profile_id)
VALUES ('test2@gmail.com', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);

INSERT INTO member(email, role, created_date, updated_date, user_profile_id)
VALUES ('test3@gmail.com', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3);

INSERT INTO member(email, role, created_date, updated_date, user_profile_id)
VALUES ('test4@gmail.com', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4);



INSERT INTO post(content, like_count, liked, created_date, updated_date, title, view_count, user_id)
VALUES ('first posting', 0, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'first title', 0, 1);

INSERT INTO post(content, like_count, liked, created_date, updated_date, title, view_count, user_id)
VALUES ('second posting', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'second title', 10, 2);

INSERT INTO post(content, like_count, liked, created_date, updated_date, title, view_count, user_id)
VALUES ('third posting', 0, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'third title', 20, 3);

INSERT INTO post(content, like_count, liked, created_date, updated_date, title, view_count, user_id)
VALUES ('fourth posting', 0, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'fourth title', 10, 4);



INSERT INTO post_like(post_id, user_id)
VALUES (2, 1);

INSERT INTO post_like(post_id, user_id)
VALUES (2, 2);

INSERT INTO post_like(post_id, user_id)
VALUES (2, 3);

INSERT INTO post_like(post_id, user_id)
VALUES (2, 4);



INSERT INTO comment(content, like_count, liked, created_date, updated_date, post_id, user_id)
VALUES ('first comment', 3, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 1);

INSERT INTO comment(content, like_count, liked, created_date, updated_date, post_id, user_id)
VALUES ('second comment', 0, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2);

INSERT INTO comment(content, like_count, liked, created_date, updated_date, post_id, user_id)
VALUES ('third comment', 0, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 3);

INSERT INTO comment(content, like_count, liked, created_date, updated_date, post_id, user_id)
VALUES ('fourth comment', 0, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 4);

INSERT INTO comment(content, like_count, liked, created_date, updated_date, post_id, user_id)
VALUES ('third comment2', 0, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 3);



INSERT INTO comment_like(comment_id, user_id)
VALUES (1, 2);

INSERT INTO comment_like(comment_id, user_id)
VALUES (1, 3);

INSERT INTO comment_like(comment_id, user_id)
VALUES (1, 4);