import { Avatar, Card, List } from 'antd';
import type { FC } from 'react';
import React, { useEffect } from 'react';
import ProCard from '@ant-design/pro-card';
import type { Dispatch } from 'umi';
import { connect } from 'umi';
import type { ListSubMenu } from './data.d';
import styles from './style.less';
import type { StateType } from './model';


interface HomeProps {
  dispatch: Dispatch;
  home: StateType;
  loading: boolean;
}

export const Home: FC<HomeProps> = (props) => {
  const {
    dispatch,
    loading,
    home: { list },
  } = props;
  const listGutter = {
    gutter: 16,
    xs: 1,
    sm: 2,
    md: 4,
    lg: 4,
    xl: 4,
    xxl: 4,
  };
  useEffect(() => {
    dispatch({
      type: 'home/fetch',
      payload: {
        count: 8,
      },
    });
  }, [1]);


  return (
    <div className={styles.filterCardList}>
      {list.map((menu) =>
        <ProCard key={menu.id} title={menu.title} headerBordered
                 collapsible>
          <List<ListSubMenu>
            rowKey='id'
            grid={listGutter}
            loading={loading}
            dataSource={menu.subMenus}
            renderItem={(item) => (
              <List.Item key={item.id}>
                <Card hoverable
                      bodyStyle={{ paddingBottom: 20 }}
                >
                  <Card.Meta avatar={<Avatar size='small' src={item.avatar} />} title={item.title} />
                </Card>
              </List.Item>
            )}
          />
        </ProCard>,
      )}
    </div>
  );
};

export default connect(
  ({
     home,
     loading,
   }: {
    home: StateType;
    loading: { models: Record<string, boolean> };
  }) => ({
    home,
    loading: loading.models.home,
  }),
)(Home);
