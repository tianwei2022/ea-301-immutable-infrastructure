create table book_order
(
  id           varchar(36)                          not null primary key,
  user_id      varchar(36)                          not null comment '用户id',
  order_number varchar(18)                          not null comment '订单编号',
  total_price  decimal(12, 2)                       not null comment '总价格',
  status       varchar(10)                          not null comment '订单状态',
  complete_at  datetime default current_timestamp() null comment '完成时间',
  cancel_at    datetime default current_timestamp() null comment '取消时间',
  created_at   datetime default current_timestamp() null comment '创建时间',
  updated_at   datetime default current_timestamp() null comment '更新时间'
) comment '订单';

create table order_item
(
  id         varchar(36)                          not null primary key,
  order_id   varchar(36)                          not null comment '订单id',
  book_id    varchar(36)                          not null comment '图书id',
  quantity   long                                 not null comment '数量',
  deal_price decimal(6, 2)                        not null comment '成交价格',
  created_at datetime default current_timestamp() null comment '创建时间',
  updated_at datetime default current_timestamp() null comment '更新时间'
) comment '订单项';