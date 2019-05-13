%sql
SELECT 
	a3.*, 
	-- 折扣率
	IFNULL( ROUND(( a3.selfCommodityPayMoney +  a3.selfCommodityInviteNetIncome * 1.06)/selfCommodityGmv  ,2) ,0)  AS selfCommodityDiscountRate,
	IFNULL( ROUND(( a3.buyDrinkPayMoney +  a3.buyDrinkInviteNetIncome * 1.06)/buyDrinkGmv  ,2) ,0)  AS buyDrinkDiscountRate,
	IFNULL( ROUND(( a3.buyFoodPayMoney +  a3.buyFoodInviteNetIncome * 1.06)/buyFoodGmv  ,2) ,0)  AS buyFoodDiscountRate,
	IFNULL( ROUND(( a3.inviteNetIncome +  a3.deliveryNetIncome * 1.06)/selfCommodityGmv  ,2) ,0)  AS commodityDiscountRate,

	-- 调整后净收入
	(a3.selfCommodityNetIncome + a3.selfCommodityInviteNetIncome) AS selfCommodityAdjustedNetIncome,
	(a3.buyDrinkNetIncome + a3.buyDrinkInviteNetIncome) AS buyDrinkAdjustedNetIncome,
	(a3.buyFoodNetIncome + a3.buyFoodInviteNetIncome) AS buyFoodAdjustedNetIncome,
	((a3.selfCommodityNetIncome + a3.selfCommodityInviteNetIncome)
			+ (a3.buyDrinkNetIncome + a3.buyDrinkInviteNetIncome)
			+ (a3.buyFoodNetIncome + a3.buyFoodInviteNetIncome))
			 AS commodityAdjustedNetIncome,
	(a3.netIncome + a3.inviteNetIncome) AS adjustedNetIncome

FROM (
	SELECT
		co2.depId AS shop,
		 shopInfo.shop_name AS shopName,
	--	IFNULL(shopInfo.district_id,0) AS area,
		shopInfo.city_id AS city,
		 shopInfo.city_name AS cityName,

		-- GMV
		co2.selfCommodityGmv AS selfCommodityGmv,
		co2.buyDrinkGmv AS buyDrinkGmv,
		co2.buyFoodGmv AS buyFoodGmv,
		co2.commodityGmv AS commodityGmv,
		IFNULL(or2.deliveryGmv,0) AS deliveryGmv,
		IFNULL((co2.commodityGmv + or2.deliveryGmv),0) AS gmv,

		-- 实收
		co2.selfCommodityPayMoney AS selfCommodityPayMoney,
		co2.buyDrinkPayMoney AS buyDrinkPayMoney,
		co2.buyFoodPayMoney AS buyFoodPayMoney,
		co2.commodityPayMoney AS commodityPayMoney,
		IFNULL(or2.deliveryPayMoney,0) AS deliveryPayMoney,
		-- 实收(商品实收+外送费实收）
		IFNULL((co2.commodityPayMoney + or2.deliveryPayMoney),0) AS payMoney,

		-- 增值税
		co2.selfCommodityVat AS selfCommodityVat,
		co2.buyDrinkVat AS buyDrinkVat,
		co2.buyFoodVat AS buyFoodVat,
		(co2.selfCommodityVat + co2.buyDrinkVat + co2.buyFoodVat) AS commodityVat,
		IFNULL(or2.deliveryVat,0) AS deliveryVat,

		-- 净收入
		(co2.selfCommodityPayMoney - co2.selfCommodityVat) AS selfCommodityNetIncome,
		(co2.buyDrinkPayMoney - co2.buyDrinkVat) AS buyDrinkNetIncome,
		(co2.buyFoodPayMoney - co2.buyFoodVat) AS buyFoodNetIncome,
		((co2.selfCommodityPayMoney - co2.selfCommodityVat)
			+ (co2.buyDrinkPayMoney - co2.buyDrinkVat)
			+ (co2.buyFoodPayMoney - co2.buyFoodVat)) AS commodityNetIncome,
		IFNULL((or2.deliveryPayMoney - or2.deliveryVat),0) AS deliveryNetIncome,
		IFNULL(((co2.selfCommodityPayMoney - co2.selfCommodityVat)
			+ (co2.buyDrinkPayMoney - co2.buyDrinkVat)
			+ (co2.buyFoodPayMoney - co2.buyFoodVat)
			+ (or2.deliveryPayMoney - or2.deliveryVat)),0) AS netIncome,

		-- 拉新净收入
		co2.selfCommodityInviteNetIncome  AS selfCommodityInviteNetIncome,
		co2.buyDrinkInviteNetIncome  AS buyDrinkInviteNetIncome,
		co2.buyFoodInviteNetIncome  AS buyFoodInviteNetIncome,
		co2.inviteNetIncome  AS inviteNetIncome

	FROM (
		SELECT
		-- 部门id
			co1.dep_id AS depId,

		-- 华丽的分割线------------------------------------
		-- 现制商品GMV
			sum( CASE( co1.situation != 2 and co1.commodity_mode_id = 0)
						WHEN TRUE THEN IFNULL(co1.commodity_origin_money, 0) + IFNULL(co1.addition_money, 0)  ELSE 0 END
			) AS selfCommodityGmv,

		-- 非现制饮品GMV
			sum( CASE( co1.situation != 2 and co1.commodity_mode_id = 1 and co1.one_category_name='饮品')
						WHEN TRUE THEN IFNULL(co1.commodity_origin_money, 0) + IFNULL(co1.addition_money, 0)  ELSE 0 END
			) AS buyDrinkGmv,

		-- 非现制食品GMV
			sum( CASE( co1.situation != 2 and co1.commodity_mode_id = 1 and co1.one_category_name='食品')
						WHEN TRUE THEN IFNULL(co1.commodity_origin_money, 0) + IFNULL(co1.addition_money, 0)  ELSE 0 END
			) AS buyFoodGmv,

		-- 商品GMV
			sum( CASE( co1.situation != 2  and ((co1.commodity_mode_id = 0 ) or (co1.commodity_mode_id = 1 and co1.one_category_name in ('饮品','食品'))))
						WHEN TRUE THEN IFNULL(co1.commodity_origin_money, 0) + IFNULL(co1.addition_money, 0)  ELSE 0 END
			) AS commodityGmv,

		-- 华丽的分割线------------------------------------
		-- 现制商品实收
			sum( CASE( co1.situation != 2 and co1.commodity_mode_id = 0)
					WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
			) AS selfCommodityPayMoney,

		-- 非现制饮品实收
			sum( CASE( co1.situation != 2 and co1.commodity_mode_id = 1 and co1.one_category_name='饮品')
					WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
			) AS buyDrinkPayMoney,

		-- 非现制食品实收
			sum( CASE( co1.situation != 2 and co1.commodity_mode_id = 1 and co1.one_category_name='食品')
					WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
			) AS buyFoodPayMoney,

		-- 商品实收
			sum( CASE( co1.situation != 2  and ((co1.commodity_mode_id = 0 ) or (co1.commodity_mode_id = 1 and co1.one_category_name in ('饮品','食品'))))
					WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
			) AS commodityPayMoney,

		-- 华丽的分割线------------------------------------
		-- 现制商品增值税
		-- 现制 食品 打包 0.16  现在没有现制食品
		-- 现制 食品(堂食)\饮品(打包、堂食) 0.06   非食品、打包 0.06
			sum( ROUND((
						CASE( co1.situation != 2 and co1.commodity_mode_id = 0 and co1.one_category_name ='食品' and co1.eat_type = 1)
							WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
				 	* 0.06) ,2)
			) + sum( ROUND((
						CASE( co1.situation != 2 and co1.commodity_mode_id = 0 and co1.one_category_name ='食品' and co1.eat_type = 2)
							WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
				 	* 0.16) ,2)
			) + sum( ROUND((
						CASE( co1.situation != 2 and co1.commodity_mode_id = 0 and co1.one_category_name !='食品' )
							WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
				 	* 0.06) ,2)
			) AS selfcommodityVat,

		-- 非现制饮品增值税
		-- 外购 饮品 打包 增值税 0.16
		-- 外购 饮品 堂食 增值税 0.06
			sum( ROUND((
						CASE( co1.situation != 2 and co1.commodity_mode_id = 1 and co1.one_category_name ='饮品' and co1.eat_type = 2)
							WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
				 	* 0.16) ,2)
			) + sum( ROUND((
						CASE( co1.situation != 2 and co1.commodity_mode_id = 1 and co1.one_category_name ='饮品' and co1.eat_type = 1)
							WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
				 	* 0.06) ,2)
			) AS buyDrinkVat,

		-- 非现制食品增值税
		-- 外购 食品 打包 增值税 0.16
		-- 外购 食品 堂食 增值税 0.06
			sum( ROUND((
						CASE( co1.situation != 2 and co1.commodity_mode_id = 1 and co1.one_category_name ='食品' and co1.eat_type = 2)
							WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
				 	* 0.16) ,2)
			) + sum( ROUND((
						CASE( co1.situation != 2 and co1.commodity_mode_id = 1 and co1.one_category_name ='食品' and co1.eat_type = 1)
							WHEN TRUE THEN IFNULL(co1.pay_money, 0) + IFNULL(co1.coffeestore_share_money, 0)  ELSE 0 END
				 	* 0.06) ,2)
			) AS buyFoodVat,

		-- 华丽的分割线------------------------------------
		-- 现制商品拉新净收入
			( sum( ROUND((
							CASE co1.situation != 2 AND co1.commodity_mode_id = 0
							 AND coupon.coupon_type = 2 AND coupon.coupon_sub_type = 1
							 AND coupon.discount_price IS NOT NULL AND coupon.discount_price = 0
						   AND couponsend.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
						  WHEN TRUE THEN
						  	IFNULL(co1.commodity_origin_money, 0) + IFNULL(co1.addition_money, 0) ELSE 0 END
						* 0.5 /1.06 ) ,2))
			+ sum( ROUND((
			 			  CASE co1.situation != 2 AND co1.commodity_mode_id = 0
							 AND coffeestoresend.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
						  WHEN TRUE THEN
							 IFNULL(co1.coffeestore_deal_money,0)
						  ELSE 0 END
						* 0.5 /1.06 ) ,2))
			) AS selfCommodityInviteNetIncome,

		-- 非现制饮品拉新净收入
			( sum( ROUND((
							CASE co1.situation != 2 AND co1.commodity_mode_id = 1 and co1.one_category_name='饮品'
							 AND coupon.coupon_type = 2 AND coupon.coupon_sub_type = 1
							 AND coupon.discount_price IS NOT NULL AND coupon.discount_price = 0
						   AND couponsend.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
						  WHEN TRUE THEN
						  	IFNULL(co1.commodity_origin_money, 0) + IFNULL(co1.addition_money, 0) ELSE 0 END
						* 0.5 /1.06 ) ,2))
			+ sum( ROUND((
			 			  CASE co1.situation != 2 AND co1.commodity_mode_id = 1 and co1.one_category_name='饮品'
							 AND coffeestoresend.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
						  WHEN TRUE THEN
							 IFNULL(co1.coffeestore_deal_money,0)
						  ELSE 0 END
						* 0.5 /1.06 ) ,2))
			) AS buyDrinkInviteNetIncome,

		-- 非现制食品拉新净收入
			( sum( ROUND((
							CASE co1.situation != 2 AND co1.commodity_mode_id = 1 and co1.one_category_name='食品'
							 AND coupon.coupon_type = 2 AND coupon.coupon_sub_type = 1
							 AND coupon.discount_price IS NOT NULL AND coupon.discount_price = 0
						   AND couponsend.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
						  WHEN TRUE THEN
						  	IFNULL(co1.commodity_origin_money, 0) + IFNULL(co1.addition_money, 0) ELSE 0 END
						* 0.5 /1.06 ) ,2))
			+ sum( ROUND((
			 			  CASE co1.situation != 2 AND co1.commodity_mode_id = 1 and co1.one_category_name='食品'
							 AND coffeestoresend.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
						  WHEN TRUE THEN
								IFNULL(co1.coffeestore_deal_money,0)
						  ELSE 0 END
						* 0.5 /1.06 ) ,2))
			) AS buyFoodInviteNetIncome,

		-- 拉新净收入
			( sum( ROUND((
							CASE co1.situation != 2 and  ((co1.commodity_mode_id = 0 ) or (co1.commodity_mode_id = 1 and co1.one_category_name in ('饮品','食品')))
							 AND coupon.coupon_type = 2 AND coupon.coupon_sub_type = 1
							 AND coupon.discount_price IS NOT NULL AND coupon.discount_price = 0
						   AND couponsend.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
						  WHEN TRUE THEN
						  	IFNULL(co1.commodity_origin_money, 0) + IFNULL(co1.addition_money, 0) ELSE 0 END
						* 0.5 /1.06 ) ,2))
			+ sum( ROUND((
			 			  CASE co1.situation != 2 and ((co1.commodity_mode_id = 0 ) or (co1.commodity_mode_id = 1 and co1.one_category_name in ('饮品','食品')))
							 AND coffeestoresend.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
						  WHEN TRUE THEN
							 IFNULL(co1.coffeestore_deal_money,0)
						  ELSE 0 END
						* 0.5 /1.06 ) ,2))
			) AS inviteNetIncome
		FROM
			dw_lucky_order.t_order_commodity co1
		LEFT JOIN dw_dim.date_dim dim on co1.dt =dim.dt
		-- 关联订单代金券信息表
		LEFT JOIN lucky_order.t_order_coupon coupon ON ( co1.coupon_id = coupon.coupon_id AND co1.order_id = coupon.order_id)
		LEFT JOIN lucky_marketing.t_mkt_coupon_send_record couponsend ON coupon.coupon_record_no = couponsend.coupon_record_no
		LEFT JOIN lucky_order.t_order_coffeestore coffeestore ON co1.id = coffeestore.order_commodity_id
		LEFT JOIN lucky_coupon_order.t_mkt_coffee_coupon_send_record coffeestoresend ON coffeestore.coffeestore_no = coffeestoresend.coupon_record_no
		WHERE
			co1.STATUS = 50
			AND co1.dep_id != 4901
			AND co1.one_category_name != '测试'
		--	AND co1.dep_id = 114901
		  AND dim.timeflag_year = '2018'
			AND dim.timeflag_month = '10'
		--	AND dim.timeflag_day = '28'
		GROUP BY co1.dep_id
	) co2
	LEFT JOIN (
		SELECT
			or1.order_dep_id AS depId,
			-- 配送费
			sum( IFNULL(or1.finance_delivery_money, 0)) AS deliveryGmv,
			-- 配送费实收
			sum( CASE or1.order_situation != 3	WHEN TRUE THEN IFNULL(or1.finance_delivery_pay_money, 0) ELSE 0 END
			) AS deliveryPayMoney,
			-- 配送费增值税
			sum( IFNULL( ROUND(( (CASE or1.order_situation != 3	WHEN TRUE THEN IFNULL(or1.finance_delivery_pay_money, 0) ELSE 0 END ) 
				* 0.06) ,2) ,0)  ) AS deliveryVat
		FROM
			dw_lucky_order.t_order or1
		LEFT JOIN dw_dim.date_dim dim ON or1.dt = dim.dt
		WHERE
			or1.order_status = 50
			AND or1.order_dep_id != 4901
		--	AND or1.order_dep_id = 114901
			AND dim.timeflag_year = '2018'
			AND dim.timeflag_month = '10'
		--	AND dim.timeflag_day = '28'
			GROUP BY or1.order_dep_id 
	) or2 on co2.depId = or2.depId
	LEFT JOIN lucky_operation.t_shop_info shopInfo ON shopInfo.department_id = co2.depId
) a3