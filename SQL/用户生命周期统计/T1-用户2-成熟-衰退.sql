%sql
SELECT
	count(DISTINCT his.id)
FROM
	(
		SELECT DISTINCT
			mem.id
		FROM
			(
				SELECT
					member.id,
					count(DISTINCT o.id) memcount
				FROM
					lucky_crm.t_member member
				LEFT JOIN lucky_order.t_order o ON o.member_id = member.id
				LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
				LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
				left join (
				    select
    				    member.id,
    				    count(DISTINCT o.id) memcount
				    FROM
    					lucky_crm.t_member member
    				LEFT JOIN lucky_order.t_order o ON o.member_id = member.id
    				LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
    				LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
    				WHERE
    					o. STATUS = 50
    				AND member. STATUS = 1
    				AND o.dep_id != 4901
    				AND o.dep_id != 325571
    				AND oc.situation != 2
    				AND dim.dt >= '2019-01-01'
    				AND dim.dt <= '2019-01-31'
    				GROUP BY
    					member.id

				) nowMonth on member.id = nowMonth.id
				WHERE
					o. STATUS = 50
				AND member. STATUS = 1
				AND o.dep_id != 4901
				AND o.dep_id != 325571
				AND oc.situation != 2
				AND dim.dt <= '2019-01-31'
				and (nowMonth.memcount < 8 or nowMonth.id is null)
				GROUP BY
					member.id
				HAVING
					memcount > 10
			) mem

	) his
LEFT JOIN (
	SELECT DISTINCT
		o.member_id
	FROM
		lucky_order.t_order o
	LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
	LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
	WHERE
		o. STATUS = 50
	AND o.dep_id != 4901
	AND o.dep_id != 325571
	AND oc.situation != 2
	AND dim.dt > date_sub('2019-01-31', 30)
	AND dim.dt <= '2019-01-31'
) non ON non.member_id = his.id
LEFT JOIN (
	SELECT DISTINCT
		t0.member_id as memberId
	FROM
		(
			SELECT DISTINCT his.member_id
            FROM (
                   SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-01-31'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-01-31',30 )
                  AND dim.dt <= '2018-01-31'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-01-31 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-02-28'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-02-28',30 )
                  AND dim.dt <= '2018-02-28'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-02-28 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-03-31'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-03-31',30 )
                  AND dim.dt <= '2018-03-31'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-03-31 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-04-30'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-04-30',30 )
                  AND dim.dt <= '2018-04-30'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-04-30 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-05-31'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-05-31',30 )
                  AND dim.dt <= '2018-05-31'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-05-31 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-06-30'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-06-30',30 )
                  AND dim.dt <= '2018-06-30'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-06-30 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-07-31'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-07-31',30 )
                  AND dim.dt <= '2018-07-31'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-07-31 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-08-31'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-08-31',30 )
                  AND dim.dt <= '2018-08-31'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-08-31 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-09-30'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-09-30',30 )
                  AND dim.dt <= '2018-09-30'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-09-30 23:59:59',his.CT)) >=8


            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-10-31'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-10-31',30 )
                  AND dim.dt <= '2018-10-31'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-10-31 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-11-30'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-11-30',30 )
                  AND dim.dt <= '2018-11-30'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-11-30 23:59:59',his.CT)) >=8

            union

            SELECT DISTINCT his.member_id
            FROM (
                SELECT
                     o.member_id,
                     count(DISTINCT o.id) memcount,
                     MIN(o.create_time) AS CT
                   FROM
                     lucky_order.t_order o
                     LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                     LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                   WHERE
                     o.STATUS = 50
                     AND o.dep_id != 4901
                     AND o.dep_id != 325571
                     AND oc.situation != 2
                     AND dim.dt <= '2018-12-31'
                   GROUP BY
                     o.member_id
                       HAVING memcount > 10
                 ) his
              left join (
                SELECT
                  distinct o.member_id
                FROM
                  lucky_order.t_order o
                  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                  LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
                WHERE
                  o.STATUS = 50
                  AND o.dep_id != 4901
                  AND o.dep_id != 325571
                  AND oc.situation != 2
                  AND dim.dt > date_sub('2018-12-31',30 )
                  AND dim.dt <= '2018-12-31'
                ) non on non.member_id = his.member_id
            WHERE non.member_id IS NOT NULL
            and (his.memcount/months_between('2018-12-31 23:59:59',his.CT)) >=8
		) t0
) t1 ON t1.memberId = his.id
WHERE
	non.member_id IS NOT NULL
AND t1.memberId IS NOT NULL