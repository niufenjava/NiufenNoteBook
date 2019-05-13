---------------------------------------------------------------------

SELECT  
CONCAT(p.trade_no,'-',f.order_actually_pay_money) as 支付流水号 
FROM lucky_order.t_order o 
LEFT JOIN lucky_order.t_order_finance f ON o.id = f.order_id 
LEFT JOIN lucky_order.t_order_pay p ON o.id = p.order_id 
where o.DT >= '2018-08-30' AND o.DT <= '2018-08-30' 
and p.pay_from = 2 
and o.customer_type = 1 
and o.status = 50

---------------------------汇总---------------------------------------
SELECT  
 o.DT,
 p.pay_from,
sum(f.order_actually_pay_money)
FROM lucky_order.t_order o 
LEFT JOIN lucky_order.t_order_finance f ON o.id = f.order_id 
LEFT JOIN lucky_order.t_order_pay p ON o.id = p.order_id 
where o.DT >= '2018-08-01' AND o.DT <= '2018-08-31' 
and o.customer_type = 1 
and o.status = 50
group by o.DT,p.pay_from

-----------------原始报表----------------------
SELECT 
CONCAT(o.order_no,' ') as 订单号,
e.shop_name as 门店名称, 
o.pay_time as 支付时间, 
o.finish_time as 完成时间, 
CASE f.order_actually_pay_money WHEN NULL THEN 0 ELSE f.order_actually_pay_money END as 实收金额, 
CONCAT(p.trade_no,' ') as 支付流水号 
FROM lucky_order.t_order o 
LEFT JOIN lucky_order.t_order_finance f ON o.id = f.order_id 
LEFT JOIN lucky_order.t_order_extend e ON o.id = e.order_id 
LEFT JOIN lucky_order.t_order_pay p ON o.id = p.order_id 
where o.DT >= '2018-08-30' AND o.DT <= '2018-08-30' 
and p.pay_from = 2 
and o.customer_type = 1 
and o.status = 50

---------------------------
SELECT 
o.order_no as 订单号,
o.member_phone as 客户电话, 
CASE o.type WHEN 2 THEN '自取' WHEN 1 THEN '外送' ELSE o.type END as 订单类型,
city.name 门店城市, 
tsi.shop_no as 门店编码,
 tsi.sequence_number  AS  门店序号,
e.shop_name as 门店名称, 
CASE f.order_actually_pay_money WHEN NULL THEN 0 ELSE f.order_actually_pay_money END as 实收金额,
CASE f.delivery_pay_money WHEN NULL THEN 0 ELSE f.delivery_pay_money END as 配送费实收金额,
(ifnull(f.total_origin_money,0)+ifnull(f.total_addition_money,0)+ifnull(f.delivery_money,0)) as 订单金额,
CASE p.pay_from WHEN 1 THEN '支付宝' WHEN 2 THEN '微信'  WHEN 3 THEN '银联' WHEN 0 THEN '无'  ELSE p.pay_from END as 支付方式, 
CASE o.eat_type WHEN 1 THEN '堂食' WHEN 2 THEN '打包' ELSE o.eat_type END as 就餐形式, 
CASE o.customer_type WHEN 1 THEN '个人' WHEN 2 THEN '企业' ELSE o.customer_type END as 业务类型, 
CASE o.status
 WHEN 10	THEN '已新建'
 WHEN 20	THEN '已付款'
 WHEN 50	THEN '已完成'
 WHEN 0 THEN '已取消'
 ELSE o.status END as 订单状态,
CASE o.express_status
 WHEN 10	THEN '不可配送'
 WHEN 20	THEN '待接单'
 WHEN 30	THEN '已接单'
 WHEN 40	THEN '已取餐'
 WHEN 50	THEN '已出发'
 WHEN 60 THEN '已到达'
 WHEN 70 THEN '已完成配送'
 WHEN 80 THEN '派单失败'
 WHEN 90 THEN '已改派'
 WHEN 100 THEN '已取消' 
 WHEN 110 THEN '客户自取' 
 WHEN 120 THEN '系统改派' 
 ELSE o.express_status END as 配送单状态,
d.accept_time  as 咖啡师接单时间,
ost.expect_time  as 预计制作完成时间,
d.finish_time  as 实际制作完成时间,
o.pay_time  as 支付时间,
o.finish_time as 完成时间,
CASE co.level WHEN 1 THEN '满意' WHEN 0 THEN '不满意' ELSE  co.level END  as 满意度评分, 
em.name as 配送人员,
ex.expect_time as 预计配送时间,
ex.arrive_time as 实际配送时间,
re.reason as 取消原因,
co.label as 不满意原因,
d.product_user_name as 咖啡师姓名,
p.trade_no as 支付流水号
FROM lucky_order.t_order o 
LEFT JOIN lucky_order.t_order_finance f ON o.id = f.order_id 
LEFT JOIN lucky_order.t_order_extend e ON o.id = e.order_id
LEFT JOIN lucky_order.t_order_pay p ON o.id = p.order_id
LEFT JOIN lucky_order.t_order_coffee_product d ON o.id = d.order_id
LEFT JOIN lucky_order.t_order_comment co ON o.id = co.order_id
LEFT JOIN lucky_order.t_order_express ex ON o.id = ex.order_id
LEFT JOIN lucky_order.t_ehr_employee em ON ex.express_user = em.id
LEFT JOIN lucky_order.t_order_cancel ca ON o.id=ca.order_id
LEFT JOIN lucky_order.t_order_cancel_reason re ON ca.cancel_reason_id = re.id
LEFT JOIN lucky_order.t_order_self_take ost ON o.id = ost.order_id
LEFT JOIN lucky_operation.t_shop_info tsi ON tsi.department_id = o.dep_id
LEFT JOIN lucky_admin.t_base_city city ON city.id = o.city_id
where o.DT >= '$startTime$' AND o.DT <= '$endTime$' 