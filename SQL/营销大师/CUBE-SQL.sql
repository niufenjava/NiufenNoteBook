SELECT
T_MKT_CHANNEL_MEMBER_HISTORY.INVITATION_CODE as T_MKT_CHANNEL_MEMBER_HISTORY_INVITATION_CODE
,T_MKT_CHANNEL_MEMBER_HISTORY.DT as T_MKT_CHANNEL_MEMBER_HISTORY_DT
,DATE_DIM.DT as DATE_DIM_DT
,DATE_DIM.TIMEFLAG_YEAR as DATE_DIM_TIMEFLAG_YEAR
,DATE_DIM.TIMEFLAG_MONTH as DATE_DIM_TIMEFLAG_MONTH
,DATE_DIM.WEEKNUM as DATE_DIM_WEEKNUM
,T_BI_MKT_CHANNEL.INVATATION_CODE as T_BI_MKT_CHANNEL_INVATATION_CODE
,T_BI_MKT_CHANNEL.CHANNEL_ONE as T_BI_MKT_CHANNEL_CHANNEL_ONE
,T_BI_MKT_CHANNEL.CHANNEL_TWO as T_BI_MKT_CHANNEL_CHANNEL_TWO
,T_BI_MKT_CHANNEL.CHANNEL_THREE as T_BI_MKT_CHANNEL_CHANNEL_THREE
,T_BI_MKT_CHANNEL.CITY as T_BI_MKT_CHANNEL_CITY
,T_BI_MKT_CHANNEL.SHOP as T_BI_MKT_CHANNEL_SHOP
,T_MKT_CHANNEL_MEMBER_HISTORY.IS_REGISTER as T_MKT_CHANNEL_MEMBER_HISTORY_IS_REGISTER
,T_MKT_CHANNEL_MEMBER_HISTORY.IS_LOGIN as T_MKT_CHANNEL_MEMBER_HISTORY_IS_LOGIN
,T_MKT_CHANNEL_MEMBER_HISTORY.IS_ORDER as T_MKT_CHANNEL_MEMBER_HISTORY_IS_ORDER
,T_MKT_CHANNEL_MEMBER_HISTORY.COST as T_MKT_CHANNEL_MEMBER_HISTORY_COST
,T_MKT_CHANNEL_MEMBER_HISTORY.CLICK_CNT as T_MKT_CHANNEL_MEMBER_HISTORY_CLICK_CNT
,T_MKT_CHANNEL_MEMBER_HISTORY.EXPOSURE_CNT as T_MKT_CHANNEL_MEMBER_HISTORY_EXPOSURE_CNT
,T_MKT_CHANNEL_MEMBER_HISTORY.PLAN_NAME as T_MKT_CHANNEL_MEMBER_HISTORY_PLAN_NAME
FROM KYLIN_VIEW.T_MKT_CHANNEL_MEMBER_HISTORY as T_MKT_CHANNEL_MEMBER_HISTORY
LEFT JOIN KYLIN_VIEW.T_BI_MKT_CHANNEL as T_BI_MKT_CHANNEL
ON T_MKT_CHANNEL_MEMBER_HISTORY.INVITATION_CODE = T_BI_MKT_CHANNEL.INVATATION_CODE
LEFT JOIN DW_DIM.DATE_DIM as DATE_DIM
ON T_MKT_CHANNEL_MEMBER_HISTORY.DT = DATE_DIM.DT
WHERE 1=1