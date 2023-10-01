-- noinspection SqlResolve
INSERT INTO users
VALUES (1, 'address1', 'VASYA1', 'VASYALogin1', 'VASYAPass1'),
       (2, 'address2', 'VASYA2', 'VASYALogin2', 'VASYAPass2'),
       (3, 'address3', 'VASYA3', 'VASYALogin3', 'VASYAPass3')
ON CONFLICT DO NOTHING;

-- noinspection SqlResolve
INSERT INTO items
VALUES (1, 1.0, true, 'description1', '2004-12-31', 50.0, '2004-12-31', 'title1', 1),
       (2, 2.0, false, 'description2', '2004-12-31', 100.0, '2004-12-31', 'title2', 1),
       (3, 3.0, false, 'description3', '2004-12-31', 120.0, '2004-12-31', 'title3', 2)
ON CONFLICT DO NOTHING;


-- noinspection SqlResolve
INSERT INTO bids
VALUES (1, '2004-12-31', 10.0, 1, 3),
       (2, '2004-12-31', 20.0, 1, 2),
       (3, '2004-12-31', 30.0, 2, 3)
ON CONFLICT DO NOTHING;
