--定义输入流
CREATE INPUT STREAM market (
   mem__u__id LONG,
   inviter__u__type INT,
   inviter__u__code STRING,
   created__u__time LONG,
   first__u__login__u__time LONG,
   first__u__order__u__time LONG,
   first__u__any__u__pay__u__time LONG
    )
SOURCE FlexQInput PROPERTIES ("zookeepers" = "10.204.96.2:5181,10.212.2.109:5181,10.212.2.110:5181","prefix" = "/meta", "topic" = "luckycrm_member_first_info_topic", "groupId" = "lucky_alg_channel_member4")
--并发度2
parallel 2;

--定义输出流
CREATE OUTPUT STREAM result (
    invatation_code STRING,
    register_cnt INT,
    login_cnt INT,
    order_cnt INT,
    send_time LONG
    )
SINK FlexQOutput
    PROPERTIES ("zookeepers" = "10.204.96.2:5181,10.212.2.109:5181,10.212.2.110:5181","prefix" = "/meta" ,"topic" = "lucky_analysis_channel_member")
--并发度2
parallel 2;

--定义美团无登陆情况  会不会多出来~~~
INSERT INTO STREAM result
SELECT inviter__u__code as invatation_code, '0' as register_cnt, count(1) as login_cnt, count(1) as order_cnt, currenttimemillis() as send_time
FROM market
(inviter__u__type is not null and inviter__u__code is not null and inviter__u__type=2)[RANGE 60 SECONDS BATCH]
WHERE from_unixtime(created__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
            first__u__login__u__time is null and
      ((from_unixtime(first__u__order__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and first__u__any__u__pay__u__time is null) or
      (from_unixtime(first__u__any__u__pay__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and first__u__order__u__time is null))
GROUP BY inviter__u__type,inviter__u__code;

INSERT INTO STREAM result
SELECT (case when inviter__u__type=3 then 'COMPANYPULLNEW'
when inviter__u__type=1 then 'MEMBERPULLNEW'
when inviter__u__type=-1 then 'NATURALLYNEW' else null end) as invatation_code, '0' as register_cnt, count(1) as login_cnt, count(1) as order_cnt, currenttimemillis() as send_time
FROM market
(inviter__u__type is not null and inviter__u__type!=2)[RANGE 60 SECONDS BATCH]
WHERE from_unixtime(created__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      first__u__login__u__time is null and
      ((from_unixtime(first__u__order__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and first__u__any__u__pay__u__time is null) or
      (from_unixtime(first__u__any__u__pay__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and first__u__order__u__time is null))
GROUP BY inviter__u__type;

--定义业务逻辑
INSERT INTO STREAM result
SELECT inviter__u__code as invatation_code, '0' as register_cnt, '0' as login_cnt, count(1) as order_cnt, currenttimemillis() as send_time
FROM market
(inviter__u__type is not null and inviter__u__code is not null and inviter__u__type=2)[RANGE 60 SECONDS BATCH]
WHERE from_unixtime(created__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      from_unixtime(first__u__login__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      ((from_unixtime(first__u__order__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and first__u__any__u__pay__u__time is null) or
      (from_unixtime(first__u__any__u__pay__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and first__u__order__u__time is null))
GROUP BY inviter__u__type,inviter__u__code;

INSERT INTO STREAM result
SELECT (case when inviter__u__type=3 then 'COMPANYPULLNEW'
when inviter__u__type=1 then 'MEMBERPULLNEW'
when inviter__u__type=-1 then 'NATURALLYNEW' else null end) as invatation_code, '0' as register_cnt, '0' as login_cnt, count(1) as order_cnt, currenttimemillis() as send_time
FROM market
(inviter__u__type is not null and inviter__u__type!=2)[RANGE 60 SECONDS BATCH]
WHERE from_unixtime(created__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      from_unixtime(first__u__login__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      ((from_unixtime(first__u__order__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and first__u__any__u__pay__u__time is null) or
      (from_unixtime(first__u__any__u__pay__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and first__u__order__u__time is null))
GROUP BY inviter__u__type;

INSERT INTO STREAM result
SELECT inviter__u__code as invatation_code, '0' as register_cnt, count(1) as login_cnt, '0' as cost_cnt, currenttimemillis() as send_time
FROM market
(inviter__u__type is not null and inviter__u__code is not null and inviter__u__type=2)
[RANGE 60 SECONDS BATCH]
WHERE from_unixtime(created__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      from_unixtime(first__u__login__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      first__u__order__u__time is null and
      first__u__any__u__pay__u__time is null
GROUP BY inviter__u__type,inviter__u__code
--并发度2
parallel 2;

INSERT INTO STREAM result
SELECT (case when inviter__u__type=3 then 'COMPANYPULLNEW'
when inviter__u__type=1 then 'MEMBERPULLNEW'
when inviter__u__type=-1 then 'NATURALLYNEW' else null end) as invatation_code, '0' as register_cnt, count(1) as login_cnt, '0' as cost_cnt, currenttimemillis() as send_time
FROM market
(inviter__u__type is not null and inviter__u__type!=2)
[RANGE 60 SECONDS BATCH]
WHERE from_unixtime(created__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      from_unixtime(first__u__login__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      first__u__order__u__time is null and
      first__u__any__u__pay__u__time is null
GROUP BY inviter__u__type
--并发度2
parallel 2;

INSERT INTO STREAM result
SELECT inviter__u__code  as invatation_code, count(inviter__u__code) as register_cnt, '0' as login_cnt, '0' as cost_cnt, currenttimemillis() as send_time
FROM market
(inviter__u__type=2 and inviter__u__code is not null)[RANGE 60 SECONDS BATCH]
WHERE from_unixtime(created__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      first__u__login__u__time is null and
      first__u__order__u__time is null and
      first__u__any__u__pay__u__time is null
GROUP BY inviter__u__type,inviter__u__code
--并发度2
parallel 2;

INSERT INTO STREAM result
SELECT (case when  inviter__u__type=3 then 'COMPANYPULLNEW'
when  inviter__u__type=1 then 'MEMBERPULLNEW'
when inviter__u__type=-1 then 'NATURALLYNEW' else null end) as invatation_code, count(1) as register_cnt, '0' as login_cnt, '0' as cost_cnt, currenttimemillis() as send_time
FROM market
(inviter__u__type is not null and inviter__u__type!=2)[RANGE 60 SECONDS BATCH]
WHERE from_unixtime(created__u__time, 'yyyy-MM-dd') = from_unixtime(currenttimemillis(), 'yyyy-MM-dd') and
      first__u__login__u__time is null and
      first__u__order__u__time is null and
      first__u__any__u__pay__u__time is null
GROUP BY inviter__u__type
--并发度2
parallel 2;

--定义拓扑任务名字
SUBMIT APPLICATION market_channel_stat_new;



