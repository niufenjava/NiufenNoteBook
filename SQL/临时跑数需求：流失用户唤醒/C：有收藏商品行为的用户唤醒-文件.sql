%livy.spark
val filepath = "/user/haijun.zhang/usergrowth/msg/dt=2019-03-10/C咖啡/";
var curDay = "2019-03-10";
-- val sqlmon = """
SELECT concat(a.member_id,',',t.phone_no,',',b.name,',',b.create_time)
FROM (
       -- 流失：历史30天未下单
       SELECT
         DISTINCT o.member_id
       FROM
         lucky_order.t_order o
         -- 30天之内有下单用户
         LEFT JOIN (
                     SELECT DISTINCT o.member_id
                     FROM
                       lucky_order.t_order o
                       LEFT JOIN lucky_crm.t_member m ON m.id = o.member_id
                     WHERE
                       o.STATUS = 50
                       AND o.dt > date_sub('2019-03-08', 30) -- 本月
                       AND o.dt <= '2019-03-08' -- 上月
                       AND o.dep_id NOT IN (SELECT department_id
                                            FROM dw_dim.t_test_shop)
                       AND m.status = 1
                       AND m.id IS NOT NULL
                   ) h ON h.member_id = o.member_id
         LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
       WHERE
         o.STATUS = 50
         -- 30天前下单用户
         AND o.dt <= date_sub('2019-03-08', 30)
         AND o.dep_id NOT IN (SELECT department_id
                              FROM dw_dim.t_test_shop)
         AND m.status = 1
         AND m.id IS NOT NULL
         -- 不包含30天内下单的用户
         AND h.member_id IS NULL
       GROUP BY o.member_id
       HAVING count(DISTINCT o.id) > 0
     ) a
  LEFT JOIN (
              SELECT t.mem_id,t.create_time,coo.name
              FROM (
                     SELECT tc.mem_id, max(tc.created_time) as create_time, tc.commodity_id
                     FROM lucky_crm.t_taste_collection tc
                     WHERE tc.created_time > date_sub('2019-03-08', 7)
                           AND tc.created_time <= '2019-03-08'
                     GROUP BY tc.mem_id, tc.commodity_id
                   ) t
                LEFT JOIN lucky_commodity.t_lucky_commodity coo on coo.id = t.commodity_id
              WHERE

                t.commodity_id IN (
                  SELECT co.id
                  FROM lucky_commodity.t_lucky_commodity co
                    LEFT JOIN lucky_commodity.t_lucky_category ca ON ca.id = co.two_category_id
                  WHERE
                    ca.name IN ('瑞纳冰', '现磨咖啡', '经典饮品', '零度拿铁')
                  -- ca.name IN ('鲜榨果蔬汁')
                  -- ca.name = 'boss午餐'
                  -- ca.name = '健康轻食'
                )
                and coo.id is not null
            ) b ON b.mem_id = a.member_id
  LEFT JOIN (
              SELECT t.mem_id,t.create_time
              FROM (
                     SELECT tc.mem_id, max(tc.created_time) as create_time
                     FROM lucky_crm.t_taste_collection tc
                     WHERE tc.created_time > date_sub('2019-03-08', 7)
                           AND tc.created_time <= '2019-03-08'
                             AND  tc.commodity_id IN (
                                      SELECT co.id
                                      FROM lucky_commodity.t_lucky_commodity co
                                        LEFT JOIN lucky_commodity.t_lucky_category ca ON ca.id = co.two_category_id
                                      WHERE
                                        ca.name IN ('瑞纳冰', '现磨咖啡', '经典饮品', '零度拿铁')
                                      -- ca.name IN ('鲜榨果蔬汁')
                                      -- ca.name = 'boss午餐'
                                      -- ca.name = '健康轻食'
                                    )
                     GROUP BY tc.mem_id
                   ) t

            ) c ON c.mem_id = b.mem_id and c.create_time = b.create_time
  LEFT JOIN lucky_crm.t_member t on t.id = a.member_id
WHERE b.mem_id IS NOT NULL
and t.id is not null
and c.mem_id is not null

 """
var cursql = sqlmon.replace("2019-03-08",curDay);
spark.sql(cursql).repartition(1).write.mode(org.apache.spark.sql.SaveMode.Overwrite).option("header", "false").text(filepath)