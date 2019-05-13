SELECT
  a.member_id   AS member_id,
  (CASE
        WHEN a.order_history_count = 0 THEN 1
        WHEN a.order_thirty_days_count > 0 AND a.order_history_count <= 3 THEN 2
        WHEN a.order_thirty_days_count > 0 AND a.order_history_count <= 10 AND a.order_history_count > 3 THEN 3
        WHEN a.order_thirty_days_count > 0 AND a.order_history_count > 10 THEN 4
        WHEN a.order_thirty_days_count = 0 AND a.order_history_count > 0 THEN 5
        ELSE 0 END) AS lifecycle_type,
  (CASE WHEN a.order_thirty_days_count > 0 AND a.order_sixty_days_count = a.order_thirty_days_count AND order_sixty_before_days_count > 0 THEN 1 ELSE 0 END) AS rouse_status,
  (CASE WHEN a.order_thirty_days_count > 0 AND a.order_history_count = order_thirty_days_count THEN 1 ELSE 0 END) AS thirty_days_first_order,
  a.order_history_count AS order_history_count,
  a.order_cur_day_count AS order_cur_day_count,
  a.order_cur_month_count AS order_cur_month_count,
  a.order_seven_days_count AS order_seven_days_count,
  a.order_thirty_days_count AS order_thirty_days_count,
  a.order_sixty_days_count AS order_sixty_days_count,
  a.order_sixty_before_days_count AS order_sixty_before_days_count,
  '2018-01-31' AS dt
FROM (
       SELECT
         member.id                                           AS member_id,
         ifnull(tradeOrder.order_history_count, 0)           AS order_history_count,
         ifnull(tradeOrder.order_cur_day_count, 0)          AS order_cur_day_count,
         ifnull(tradeOrder.order_cur_month_count, 0)          AS order_cur_month_count,
         ifnull(tradeOrder.order_seven_days_count, 0)        AS order_seven_days_count,
         ifnull(tradeOrder.order_thirty_days_count, 0)       AS order_thirty_days_count,
         ifnull(tradeOrder.order_sixty_days_count, 0)        AS order_sixty_days_count,
         ifnull(tradeOrder.order_sixty_before_days_count, 0) AS order_sixty_before_days_count
       FROM
         lucky_crm.t_member member
         LEFT JOIN lucky_crm.t_member_profile p ON p.mem_id = member.id
         LEFT JOIN
         (
           SELECT
             o.member_id AS  member_id,
             count(*)    AS  order_history_count,
             SUM(CASE WHEN datediff('2018-01-31', o.dt) = 0 THEN 1 ELSE 0 END) order_cur_day_count,
             SUM(CASE WHEN month(o.dt) = month('2018-01-31') AND year(o.dt)=year('2018-01-31') THEN 1 ELSE 0 END) order_cur_month_count,
             SUM(CASE WHEN datediff('2018-01-31', o.dt) < 7 THEN 1 ELSE 0 END) order_seven_days_count,
             SUM(CASE WHEN datediff('2018-01-31', o.dt) < 30 THEN 1 ELSE 0 END) order_thirty_days_count,
             SUM(CASE WHEN datediff('2018-01-31', o.dt) < 60 THEN 1 ELSE 0 END) order_sixty_days_count,
             SUM(CASE WHEN datediff('2018-01-31', o.dt) >= 60 THEN 1 ELSE 0 END) order_sixty_before_days_count
           FROM lucky_order.t_order o
           WHERE
             o.status = 50 AND
             o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop) AND
             o.dt <= '2018-01-31'
           GROUP BY o.member_id
         ) tradeOrder ON tradeOrder.member_id = member.id
       WHERE
         -- 截止某一天，全部的注册用户
         from_unixtime(unix_timestamp(member.created_time), 'yyyy-MM-dd') <= '2018-01-31'
         AND member.status = 1
     ) a
