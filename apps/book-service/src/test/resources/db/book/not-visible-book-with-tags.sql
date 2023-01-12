INSERT INTO book (id, title, description, author, asin, price, published_at, visible, deleted,
                  visible_at, created_at, updated_at)
VALUES ('c0a81ff2-8581-1dc7-8185-817e88ba0000', '结构领域驱动设计',
        '阐释领域驱动设计知识体系，精炼领域驱动设计统一过程，解决领域驱动设计落地难题', '张逸',
        '9787115566232', 149.90, '2021-09-01', 0, 0, null, current_time,
        current_time);

INSERT INTO book_tag (id, book_id, value, created_at, updated_at)
VALUES ('c0a81ff2-8581-1dc7-8185-817e8a400001', 'c0a81ff2-8581-1dc7-8185-817e88ba0000', '计算机',
        '2023-01-05 18:33:52', '2023-01-05 18:33:52'),
       ('c0a81ff2-8581-1dc7-8185-817e8a400002', 'c0a81ff2-8581-1dc7-8185-817e88ba0000', '软件工程',
        '2023-01-05 18:33:52', '2023-01-05 18:33:52')
;

