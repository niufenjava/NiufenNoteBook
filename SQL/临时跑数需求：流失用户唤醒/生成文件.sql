%livy.spark
val filepath = "/projects/usergrowth/msg/dt=2019-03-08/B1/";
val sql1 = """
concat(t.member_id,',',member.phone_no,',','null')

 """
spark.sql(sql1).repartition(1).write.mode(org.apache.spark.sql.SaveMode.Overwrite).option("header", "false").text(filepath)