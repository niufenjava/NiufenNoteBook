package pres.niufen.common;

/**
 * @Description
 * @Author niufenjava
 * @Date 2018-10-27 14:13
 **/
public class test4 {
    public static void main(String [] args){
        String sql= "select a.*,b.commodityNum, c.takeCommNumDrink, b.isMakingCoffee, b.isMaking,d.takeCommNumFood, b.commodityGmv,b.commodityPay,b.couponMoney, b.coffeestoreDealMoney  " +
                " from " +
                "(select ord.DT,count(1) orderNum,sum(V_NEED_RECV_MONEY) payableMoney ," +
                "sum(V_REAL_MONEY) actuallyPayMoney,count(distinct v_order_member_id) as memberNum, " +
                "sum(V_OFFER_TIME)/count(1) avgResponseTime, sum(V_MAKE_TIME)/count(1) avgMakeTime, " +
                "sum(V_WAIT_TIME)/count(1) avgWaitTime,sum(v_is_dispatch) dispatchNum,sum(v_is_new_member) newCustomerNum," +
                "sum(v_dispatch_time) allDispatchTime,sum(v_new_m_commodity_num) newCusCommNum, sum(v_gmv) gmv from kylin_view.t_order_all ord " +
                "where  STATUS=50 and dep_id !=4901 and dep_id in(13401) " +
                "group by ord.DT) a " +
                "left join  " +
                "(select co.dt,count(co.V_IS_VALID) commodityNum,sum(co.v_is_make_coffee) isMakingCoffee," +
                "sum(co.commodityGmv) commodityGmv," +
                "sum(co.commodityPay) commodityPay," +
                "sum(co.couponMoney) couponMoney," +
                "sum(co.coffeestoreDealMoney) coffeestoreDealMoney," +
                "sum(co.v_is_make) isMaking from T_ORDER_COMMODITY co  where co.status=50 and co.dep_id != 4901 and co.dep_id in(13401) " +
                "group by co.dt) b on a.dt=b.dt " +
                "left join " +
                "(select oc.dt,count(*) takeCommNumDrink from KYLIN_VIEW.T_ORDER_COMMODITY oc " +
                "inner join (select id from DW_LUCKY_COMMODITY.T_LUCKY_COMMODITY_CATEGORY where mode=1 and one_category_name='饮品') lcc " +
                "on oc.commodity_id = lcc.id " +
                "where " +
                //"oc.situation!=2 " +
                "oc.dep_id!=4901 " +
                "and oc.status=50 " +
                "and dep_id in(13401)  " +
                "group by oc.dt) c " +
                "on a.dt = c.dt " +
                "left join " +
                "(select oc.dt,count(*) takeCommNumFood from KYLIN_VIEW.T_ORDER_COMMODITY oc " +
                "inner join (select id from DW_LUCKY_COMMODITY.T_LUCKY_COMMODITY_CATEGORY where mode=1 and one_category_name='食品') lcc " +
                "on oc.commodity_id = lcc.id " +
                "where " +
                //"oc.situation!=2 " +
                "oc.dep_id!=4901 " +
                "and oc.status=50 " +
                "and dep_id in(13401)  " +
                "group by oc.dt) d " +
                "on a.dt = d.dt " +
                "where a.dt>='2018-10-01' and a.dt<='2018-10-31' ORDER BY a.dt desc";
        System.out.println("sql:"+sql);
    }
}
