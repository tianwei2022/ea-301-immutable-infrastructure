INSERT INTO book (id, title, description, author, asin, price, published_at, visible, deleted,
                  visible_at, created_at, updated_at)
VALUES ('c0a81ff2-8581-1dc7-8185-817e88ba0000', '结构领域驱动设计',
        '阐释领域驱动设计知识体系，精炼领域驱动设计统一过程，解决领域驱动设计落地难题', '张逸',
        '9787115566232', 149.90, '2021-09-01', 0, 0, null, current_time,
        current_time),
       ('a04b02de-8d11-11ed-afd7-bbbbb937f043', '追寻逝去的时光 第一卷 去斯万家那边',
        '在人类文学史上意识流小说的开山之作', '[法]普鲁斯特 著 周克希 译',
        '97875594437792', 50.00, '2020-03-01', 1, 0, current_time, current_time,
        current_time);

INSERT INTO book_tag (id, book_id, value, created_at, updated_at)
VALUES ('c0a81ff2-8581-1dc7-8185-817e8a400001', 'c0a81ff2-8581-1dc7-8185-817e88ba0000', '计算机',
        '2023-01-05 18:33:52', '2023-01-05 18:33:52'),
       ('c0a81ff2-8581-1dc7-8185-817e8a400002', 'c0a81ff2-8581-1dc7-8185-817e88ba0000', '软件工程',
        '2023-01-05 18:33:52', '2023-01-05 18:33:52'),
       ('a04b0360-8d11-11ed-afda-cbfe667678a2', 'a04b02de-8d11-11ed-afd7-bbbbb937f043', '法国文学',
        '2023-01-05 18:33:52', '2023-01-05 18:33:52')
;

