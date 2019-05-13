package pres.niufen.common;

import java.io.*;
import java.util.HashSet;

/**
 * @Description
 * @Author niufenjava
 * @Date 2018-07-19 19:20
 **/
public class Test {

    public static void main(String [] args){
       String sql = "select     " +
                "  t.mem_id,     " +
                "  t.senderID,     " +
                "  t.receiverMobile,     " +
                "  t.coffeeRedTicketNum,   " +
                "  unix_timestamp(t.ttime, 'yyyy-MM-dd HH:mm:ss') event_time,      " +
                "  t.time,     " +
                "  t.app_version as appVersion,     " +
                "  t.ip,     " +
                "  t.country,     " +
                "  t.city,     " +
                "  t.province,     " +
                "  t.manufacturer,     " +
                "  t.model,     " +
                "  t.os,     " +
                "  t.os_version as osVersion,     " +
                "  t.screen_height as screenHeight,        " +
                "  t.screen_width as screenWidth,        " +
                "  t.wifi,        " +
                "  t.browser,        " +
                "  t.browser_version as browserVersion,        " +
                "  t.carrier,        " +
                "  t.network_type as networkType,      " +
                "  t.device_id as deviceId,        " +
                "  case  datediff(date_format(t.ttime,'yyyy-MM-dd HH:mm:ss'),t.is_first_day)     " +
                "    when '0' then true     " +
                "  else false end as  firstDay     " +
                "  from     " +
                "  (     " +
                "  select     " +
                "  *,     " +
                "  ROW_NUMBER()      " +
                "  over( partition by      " +
                "  origin.lot_number,  " +
                "  origin.mem_id  " +
                "  order by abs(unix_timestamp(origin.ttime, 'yyyy-MM-dd HH:mm:ss') - unix_timestamp(p.time, 'yyyy-MM-dd HH:mm:ss')) asc) as ranknum     " +
                "  from     " +
                "  (select     " +
                "  first(send_mem_id) as senderID,      " +
                "  receiver_mem_id as mem_id,     " +
                "  first(receiver_phone_no) as receiverMobile,      " +
                "  lot_number,     " +
                "  count(1) coffeeRedTicketNum,     " +
                "  date_format(first(cr.receiver_time),'yyyy-MM-dd HH:mm:ss') ttime     " +
                "  from      " +
                "  lucky_crm.t_lucky_money_record cr      " +
                "  where receiver_mem_id is not null      " +
                "  group by lot_number,receiver_mem_id) origin     " +
                "  left join     " +
                "  dw_growth.t_base_properties p     " +
                "  on origin.mem_id = p.distinct_id) t     " +
                "  where      " +
                "  t.ranknum<=1     " +
                "  limit 1000";

        System.out.println(sql);
    }


}
