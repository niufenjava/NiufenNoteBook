%sql
SELECT count(DISTINCT o.id)
FROM
  lucky_order.t_order o
  LEFT JOIN (
    SELECT DISTINCT his.member_id
    FROM
      (
        SELECT DISTINCT mem.member_id
        FROM
          (
            SELECT
              DISTINCT o.member_id,
              count(DISTINCT o.id) memcount
            FROM
              lucky_order.t_order o
              LEFT JOIN lucky_crm.t_member m ON m.id = o.member_id
              LEFT JOIN (
                          SELECT
                            DISTINCT o.member_id,
                            count(DISTINCT o.id) memcount
                          FROM
                            lucky_order.t_order o
                            LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
                          WHERE
                            o.STATUS = 50
                            AND m.STATUS = 1
                            AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                            AND o.dt >= '2018-02-01'
                            AND o.dt <= '2018-02-28'
                          GROUP BY o.member_id
                        ) nowMonth ON m.id = nowMonth.member_id
            WHERE
              o.STATUS = 50
              AND m.STATUS = 1
              AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
              AND o.dt <= '2018-02-28'
              AND (nowMonth.memcount < 8 OR nowMonth.member_id IS NULL)
            GROUP BY
              o.member_id
            HAVING
              memcount > 10
          ) mem
      ) his
      LEFT JOIN (
                  SELECT
                    DISTINCT o.member_id
                  FROM
                    lucky_order.t_order o
                    LEFT JOIN lucky_crm.t_member m ON m.id = o.member_id
                  WHERE
                    o.STATUS = 50
                    AND o.dep_id NOT IN (SELECT department_id
                                         FROM dw_dim.t_test_shop)
                    AND o.dt > date_sub('2018-02-28', 30)
                    AND o.dt <= '2018-02-28'
                    AND m.status = 1
                    AND m.id IS NOT NULL
                ) non ON non.member_id = his.member_id
      LEFT JOIN (
                  SELECT DISTINCT t0.member_id AS memberId
                  FROM
                    (
                      SELECT his.member_id
                      FROM (
                             SELECT
                               o.member_id,
                               count(DISTINCT o.id)  memcount,
                               MIN(o.create_time) AS CT
                             FROM
                               lucky_order.t_order o
                             WHERE
                               o.STATUS = 50
                               AND o.dep_id NOT IN (SELECT department_id
                                                    FROM dw_dim.t_test_shop)
                               AND o.dt <= '2018-01-31'
                             GROUP BY
                               o.member_id
                             HAVING memcount > 10
                           ) his
                        LEFT JOIN (
                                    SELECT
                                      DISTINCT o.member_id
                                    FROM
                                      lucky_order.t_order o
                                    WHERE
                                      o.STATUS = 50
                                      AND o.dep_id NOT IN (SELECT department_id
                                                           FROM dw_dim.t_test_shop)
                                      AND o.dt > date_sub('2018-01-31', 30)
                                      AND o.dt <= '2018-01-31'
                                  ) non ON non.member_id = his.member_id
                        LEFT JOIN lucky_crm.t_member t ON t.id = his.member_id
                      WHERE non.member_id IS NOT NULL
                            AND t.status = 1
                            AND t.id IS NOT NULL
                            AND (his.memcount / months_between('2018-01-31 23:59:59', his.CT)) >= 8

                      --            union

                    ) t0
                ) t1 ON t1.memberId = his.member_id
    WHERE
      non.member_id IS NOT NULL
      AND t1.memberId IS NOT NULL
)cs ON cs.member_id = o.member_id
WHERE
  o.STATUS = 50
  AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
  and o.dt >= '2018-02-01'
  and o.dt <= '2018-02-28'
  AND cs.member_id IS NOT NULL
