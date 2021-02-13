drop table if exists atlas_demo.hive_hook1;
create table atlas_demo.hive_hook1
(
    id int,
    name string
) partitioned by (dt string)
stored as orc
TBLPROPERTIES('orc.compress'='SNAPPY',
'orc.create.index'='true',
'orc.bloom.filter.columns'='id',
'transactional'='false');

drop table if exists atlas_demo.hive_hook2;
create table atlas_demo.hive_hook2
(
    id int,
    name string
) partitioned by (dt string)
stored as orc
TBLPROPERTIES('orc.compress'='SNAPPY',
'orc.create.index'='true',
'orc.bloom.filter.columns'='id',
'transactional'='false');
insert into atlas_demo.hive_hook1 (id, name, dt) values (1, '2', '2021-02-05');
INSERT OVERWRITE TABLE atlas_demo.hive_hook2 partition (dt)
select id, name, dt
from atlas_demo.hive_hook1
