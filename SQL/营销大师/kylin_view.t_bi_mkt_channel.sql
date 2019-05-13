SELECT distinct
   regexp_replace(channel.invitation_code,'\r\n','') as invatation_code,
   conf1.channel_name as channel_one,
   conf2.channel_name as channel_two,
   conf3.channel_name as channel_three,
   channel.store_in_city as city,
   channel.put_in_store as shop,
   channel.status
FROM
   lucky_marketing.t_mkt_invitation_code channel
   LEFT JOIN lucky_marketing.t_mkt_invitation_channel conf1 ON channel.belong_one_level_channel = conf1.id AND conf1.channel_level = 'A'
   LEFT JOIN lucky_marketing.t_mkt_invitation_channel conf2 ON channel.belong_two_level_channel = conf2.id AND conf2.channel_level = 'B'
   LEFT JOIN lucky_marketing.t_mkt_invitation_channel conf3 ON channel.belong_three_level_channel = conf3.id AND conf3.channel_level = 'C'
WHERE
   channel.invitation_type = 2 and channel.invitation_code not in ('NATURALLYNEW','COMPANYPULLNEW','MEMBERPULLNEW')
union
SELECT
   regexp_replace(cube_chennel.invatation_code,'\r\n','') as invatation_code,
   cube_conf1.name as channel_one,
   cube_conf2.name as channel_two,
   cube_conf3.name as channel_three,
   cube_chennel.city as city,
   cube_chennel.shop as shop,
   cube_chennel.status
FROM
   lucky_cube.t_bi_mkt_channel cube_chennel
LEFT JOIN lucky_cube.t_bi_mkt_channel_relation relation ON cube_chennel.channel_relation_id = relation.id
LEFT JOIN lucky_cube.t_bi_mkt_channel_conf cube_conf1 ON relation.level1 = cube_conf1.id
LEFT JOIN lucky_cube.t_bi_mkt_channel_conf cube_conf2 ON relation.level2 = cube_conf2.id
LEFT JOIN lucky_cube.t_bi_mkt_channel_conf cube_conf3 ON relation.level3 = cube_conf3.id
WHERE
   1 = 1 and cube_chennel.invatation_code in ('MEMBERPULLNEW','NATURALLYNEW','COMPANYPULLNEW')