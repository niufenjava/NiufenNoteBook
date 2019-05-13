SELECT
  -- 基本信息
  member.id as member_id,

  -- 拉新记录
  ifnull(invite.invitation_invitee_count,0) AS invitation_invitee_count,
  ifnull(invite.invitation_invitee_regist_count,0) AS invitation_invitee_regist_count,
  ifnull(invite.invitation_invitee_incomplete_count,0) AS invitation_invitee_incomplete_count,
  ifnull(invite.invitation_invitee_order_count,0) AS invitation_invitee_order_count,
  ifnull(invite.invitation_invitee_pay_count,0) AS invitation_invitee_pay_count,
  ifnull(invite.invitation_invitee_get_coupon_count,0) AS invitation_invitee_get_coupon_count,
  ifnull(invite.invitation_inviter_award_count,0) AS invitation_inviter_award_count,

  -- 口味收藏
  ifnull(taste.collection_count,0) AS collection_count,
  taste.collection_last_time AS collection_last_time,
  taste.collection_last_commodity_id AS collection_last_commodity_id,

  -- 优惠券相关
  ifnull(coupon.coupon_all_count,0) AS coupon_all_count,
  ifnull(coupon.coupon_valid_count,0) AS coupon_valid_count,
  ifnull(coupon.coupon_used_count,0) AS coupon_used_count,
  ifnull(coupon.coupon_expired_count,0) AS coupon_expired_count,
  ifnull(coupon.coupon_invalid_count,0) AS coupon_invalid_count,
  ifnull(coupon.coupon_expired_one_day_count,0) AS coupon_expired_one_day_count,
  ifnull(coupon.coupon_expired_two_day_count,0) AS coupon_expired_two_day_count,
  ifnull(coupon.coupon_expired_three_day_count,0) AS coupon_expired_three_day_count,
  ifnull(coupon.coupon_origin_crm,0) AS coupon_origin_crm,
  ifnull(coupon.coupon_origin_fission,0) AS coupon_origin_fission,

  -- 咖啡库券相关
  ifnull(coffeecoupon.coffeestore_all_count,0) AS coffeestore_all_count,
  ifnull(coffeecoupon.coffeestore_valid_count,0) AS coffeestore_valid_count,
  ifnull(coffeecoupon.coffeestore_used_count,0) AS coffeestore_used_count,
  ifnull(coffeecoupon.coffeestore_expired_count,0) AS coffeestore_expired_count,
  ifnull(coffeecoupon.coffeestore_transferring_count,0) AS coffeestore_transferring_count,
  ifnull(coffeecoupon.coffeestore_invalid_count,0) AS coffeestore_invalid_count,

  ifnull(coffeecoupon.coffeestore_expired_one_day_count,0) AS coffeestore_expired_one_day_count,
  ifnull(coffeecoupon.coffeestore_expired_two_day_count,0) AS coffeestore_expired_two_day_count,
  ifnull(coffeecoupon.coffeestore_expired_three_day_count,0) AS coffeestore_expired_three_day_count,

  ifnull(coffeecoupon.coffeestore_origin_buy_count,0) AS coffeestore_origin_buy_count,
  ifnull(coffeecoupon.coffeestore_origin_new_reward_count,0) AS coffeestore_origin_new_reward_count,

  ifnull(coffeecoupon.coffeestore_valid_amount_sum_count,0.00) AS coffeestore_valid_amount_sum_count,
  ifnull(coffeecoupon.coffeestore_used_amount_sum_count,0.00) AS coffeestore_used_amount_sum_count,

  -- 咖啡库券转赠相关
  ifnull(coffeeconpuntransfer.coffeestore_transfered_count,0) AS coffeestore_transfered_count,
  ifnull(coffeeconpuntransfer.coffeestore_transfered_wechat_count,0) AS coffeestore_transfered_wechat_count,
  ifnull(coffeeconpuntransfer.coffeestore_transfered_phone_count,0) AS coffeestore_transfered_phone_count,
  ifnull(coffeeconpunrecevier.coffeestore_received_count,0) AS coffeestore_received_count,
  ifnull(coffeeconpunrecevier.coffeestore_received_wechat_count,0) AS coffeestore_received_wechat_count,
  ifnull(coffeeconpunrecevier.coffeestore_received_phone_count,0) AS coffeestore_received_phone_count,

  -- 咖啡库红包相关
  ifnull(redpacketsender.coffeestore_redpacket_send_count,0) AS coffeestore_redpacket_send_count,
  ifnull(redpacketsender.coffeestore_redpacket_send_wechat_count,0) AS coffeestore_redpacket_send_wechat_count,
  ifnull(redpacketsender.coffeestore_redpacket_send_phone_count,0) AS coffeestore_redpacket_send_phone_count,
  ifnull(redpacketrecevier.coffeestore_redpacket_received_count,0) AS coffeestore_redpacket_received_count,
  ifnull(redpacketrecevier.coffeestore_redpacket_received_wechat_count,0) AS coffeestore_redpacket_received_wechat_count,
  ifnull(redpacketrecevier.coffeestore_redpacket_received_phone_count,0) AS coffeestore_redpacket_received_phone_count
FROM lucky_crm.t_member member
-- 拉新记录
LEFT JOIN
  (
    SELECT invitation.inviter_id AS member_id,
           sum(1) AS invitation_invitee_count,
           sum(CASE WHEN member.property = 0 THEN 1 ELSE 0 END) AS invitation_invitee_regist_count,
           sum(CASE WHEN member.property = 3 THEN 1 ELSE 0 END) AS invitation_invitee_incomplete_count,
           sum(CASE WHEN member.property = 1 THEN 1 ELSE 0 END) AS invitation_invitee_order_count,
           sum(CASE WHEN member.property = 2 THEN 1 ELSE 0 END) AS invitation_invitee_pay_count,
           sum(CASE WHEN invitee.coupons_status = 1 THEN 1 ELSE 0 END) AS invitation_invitee_get_coupon_count,
           sum(CASE WHEN invitee.reward_status = 1 THEN 1 ELSE 0 END) AS invitation_inviter_award_count
    FROM
      lucky_crm.t_invitation_info invitation
      INNER JOIN lucky_crm.t_member member ON member.id = invitation.invitee_id
      LEFT JOIN lucky_crm.t_invitee_new_reward invitee ON invitation.invitee_id = invitee.invitee_id
    GROUP BY invitation.inviter_id
    ) invite ON invite.member_id = member.id
-- 口味收藏
LEFT JOIN
  (
    SELECT
      taste.mem_id AS member_id,
      lastTaste.collection_count AS collection_count,
      lastTaste.collection_last_time AS collection_last_time,
      taste.commodity_id AS collection_last_commodity_id
    FROM lucky_crm.t_taste_collection taste
      LEFT JOIN
      (
        SELECT
          taste.mem_id AS member_id,
          MAX(taste.modified_time) AS collection_last_time,
          MAX(taste.id) as taste_id,
          count(*) AS collection_count
        FROM lucky_crm.t_taste_collection taste
        WHERE taste.status = 1
        GROUP BY taste.mem_id
      ) lastTaste ON lastTaste.member_id = taste.mem_id and lastTaste.taste_id = taste.id
    WHERE taste.status = 1
          and lastTaste.member_id is not null
    ) taste ON taste.member_id = member.id
-- 优惠券相关
LEFT JOIN
  (
    SELECT
      coupon.member_id AS member_id,
      count(1) AS coupon_all_count,
      sum(CASE WHEN coupon.status = 1 THEN 1 ELSE 0 END) AS coupon_valid_count,
      sum(CASE WHEN coupon.status = 3 THEN 1 ELSE 0 END) AS coupon_used_count,
      sum(CASE WHEN coupon.status = 4 THEN 1 ELSE 0 END) AS coupon_expired_count,
      sum(CASE WHEN coupon.status not in (1,3,4) THEN 1 ELSE 0 END) AS coupon_invalid_count,
      sum(CASE WHEN datediff(from_unixtime(unix_timestamp(coupon.expire_time),'yyyy-MM-dd'),'2019-03-18')=1 THEN 1 ELSE 0 END ) AS coupon_expired_one_day_count,
      sum(CASE WHEN datediff(from_unixtime(unix_timestamp(coupon.expire_time),'yyyy-MM-dd'),'2019-03-18')=2 THEN 1 ELSE 0 END ) AS coupon_expired_two_day_count,
      sum(CASE WHEN datediff(from_unixtime(unix_timestamp(coupon.expire_time),'yyyy-MM-dd'),'2019-03-18')=3 THEN 1 ELSE 0 END ) AS coupon_expired_three_day_count,
      sum(CASE WHEN coupon.origin_canal = 14 THEN 1 ELSE 0 END) AS coupon_origin_crm,
      sum(CASE WHEN coupon.origin_canal in (20,21) THEN 1 ELSE 0 END) AS coupon_origin_fission,
      1
    FROM lucky_marketing.t_mkt_coupon_send_record coupon
    GROUP BY coupon.member_id
    ) coupon ON coupon.member_id = member.id
-- 咖啡库券相关
LEFT JOIN
  (
    SELECT
      coffeecoupon.member_id AS member_id,
      count(1) AS coffeestore_all_count,
      sum(CASE WHEN coffeecoupon.status = 1 THEN 1 ELSE 0 END) AS coffeestore_valid_count,
      sum(CASE WHEN coffeecoupon.status = 3 THEN 1 ELSE 0 END) AS coffeestore_used_count,
      sum(CASE WHEN coffeecoupon.status = 4 THEN 1 ELSE 0 END) AS coffeestore_expired_count,
      sum(CASE WHEN coffeecoupon.status = 7 THEN 1 ELSE 0 END) AS coffeestore_transferring_count,
      sum(CASE WHEN coffeecoupon.status not in (1,3,4,7) THEN 1 ELSE 0 END) AS coffeestore_invalid_count,
      sum(CASE WHEN datediff(from_unixtime(unix_timestamp(coffeecoupon.expire_time),'yyyy-MM-dd'),'2019-03-18')=1 THEN 1 ELSE 0 END ) AS coffeestore_expired_one_day_count,
      sum(CASE WHEN datediff(from_unixtime(unix_timestamp(coffeecoupon.expire_time),'yyyy-MM-dd'),'2019-03-18')=2 THEN 1 ELSE 0 END ) AS coffeestore_expired_two_day_count,
      sum(CASE WHEN datediff(from_unixtime(unix_timestamp(coffeecoupon.expire_time),'yyyy-MM-dd'),'2019-03-18')=3 THEN 1 ELSE 0 END ) AS coffeestore_expired_three_day_count,
      sum(CASE WHEN coffeecoupon.origin = 1 THEN 1 ELSE 0 END) AS coffeestore_origin_buy_count,
      sum(CASE WHEN coffeecoupon.origin = 8 THEN 1 ELSE 0 END) AS coffeestore_origin_new_reward_count,
      sum(CASE WHEN coffeecoupon.status = 1 THEN coffeecoupon.marketing_cost ELSE 0 END) AS coffeestore_valid_amount_sum_count,
      sum(CASE WHEN coffeecoupon.status = 3 THEN coffeecoupon.marketing_cost ELSE 0 END) AS coffeestore_used_amount_sum_count
    FROM lucky_coupon_order.t_mkt_coffee_coupon_send_record coffeecoupon
    GROUP BY coffeecoupon.member_id
    ) coffeecoupon ON coffeecoupon.member_id = member.id
-- 咖啡库券转赠相关
LEFT JOIN
  (
    SELECT
      transfer.transfer_mem_id AS member_id,
      count(*)        AS coffeestore_transfered_count,
      sum(CASE WHEN transfer.transfer_type = 0 THEN 1 ELSE 0 END) AS coffeestore_transfered_wechat_count,
      sum(CASE WHEN transfer.transfer_type = 1 THEN 1 ELSE 0 END) AS coffeestore_transfered_phone_count
    FROM lucky_crm.t_coupon_transfer_record transfer
    WHERE transfer.status = 1 AND receiver_time IS NOT NULL
    GROUP BY transfer_mem_id
    ) coffeeconpuntransfer ON coffeeconpuntransfer.member_id = member.id
-- 咖啡库券接收相关
LEFT JOIN
(
  SELECT
    recevier.receiver_mem_id AS member_id,
    count(*)        AS coffeestore_received_count,
    sum(CASE WHEN recevier.transfer_type = 0 THEN 1 ELSE 0 END) AS coffeestore_received_wechat_count,
    sum(CASE WHEN recevier.transfer_type = 1 THEN 1 ELSE 0 END) AS coffeestore_received_phone_count
  FROM lucky_crm.t_coupon_transfer_record recevier
  WHERE recevier.status = 1 AND receiver_time IS NOT NULL
  GROUP BY receiver_mem_id
) coffeeconpunrecevier ON coffeeconpunrecevier.member_id = member.id
-- 咖啡库券红包发送相关
LEFT JOIN
(
  SELECT
    sender.send_mem_id AS member_id,
    count(*)        AS coffeestore_redpacket_send_count,
    sum(CASE WHEN sender.send_type = 0 THEN 1 ELSE 0 END) AS coffeestore_redpacket_send_wechat_count,
    sum(CASE WHEN sender.send_type = 1 THEN 1 ELSE 0 END) AS coffeestore_redpacket_send_phone_count
  FROM lucky_crm.t_lucky_money_record sender
  WHERE sender.status = 1 AND receiver_time IS NOT NULL
  GROUP BY sender.send_mem_id
) redpacketsender ON redpacketsender.member_id = member.id
-- 咖啡库券红包接收相关
LEFT JOIN
(
  SELECT
    recevier.receiver_mem_id AS member_id,
    count(*)        AS coffeestore_redpacket_received_count,
    sum(CASE WHEN recevier.send_type = 0 THEN 1 ELSE 0 END) AS coffeestore_redpacket_received_wechat_count,
    sum(CASE WHEN recevier.send_type = 1 THEN 1 ELSE 0 END) AS coffeestore_redpacket_received_phone_count
  FROM lucky_crm.t_lucky_money_record recevier
  WHERE recevier.status = 1 AND receiver_time IS NOT NULL
  GROUP BY recevier.receiver_mem_id
) redpacketrecevier ON redpacketrecevier.member_id = member.id