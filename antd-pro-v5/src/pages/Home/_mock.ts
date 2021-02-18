// eslint-disable-next-line import/no-extraneous-dependencies
import { Request, Response } from 'express';
import { ListMenu } from './data.d';

const menus = [
  {
    id: '1',
    title: '营销大师',
    avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
    subMenus: [
      {
        id: '1',
        title: '实时数据',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
      },
      {
        id: '2',
        title: '历史数据',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/zOsKZmFRdUtvpqCImOVY.png',
      },
      {
        id: '3',
        title: '渠道分析',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/dURIMkkrRFpPgTuzkwnB.png',
      },
      {
        id: '4',
        title: '渠道汇总',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/sfjbOqnsXXJgNCjCzDBL.png',
      },
      {
        id: '5',
        title: '漏斗分析',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/siCrBXXhmvTQGWPNLBow.png',
      }
    ]
  },
  {
    id: '2',
    title: '智慧运营',
    avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
    subMenus: [
      {
        id: '1',
        title: '实时数据',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/dURIMkkrRFpPgTuzkwnB.png',
      },
      {
        id: '2',
        title: '历史数据',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/siCrBXXhmvTQGWPNLBow.png',
      },
      {
        id: '3',
        title: '运营分析',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/kZzEzemZyKLKFsojXItE.png',
      },
      {
        id: '4',
        title: '订单汇总',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/ComBAopevLwENQdKWiIn.png',
      },
      {
        id: '5',
        title: '订单趋势',
        avatar: 'https://gw.alipayobjects.com/zos/rmsportal/nxkuOJlFJuAUhzlMTCEe.png',
      }
    ]
  }
];
//
// const titles = [
//   'Alipay',
//   'Angular',
//   'Ant Design',
//   'Ant Design Pro',
//   'Bootstrap',
//   'React',
//   'Vue',
//   'Webpack',
// ];
// const avatars = [
//   'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png', // Alipay
//   'https://gw.alipayobjects.com/zos/rmsportal/zOsKZmFRdUtvpqCImOVY.png', // Angular
//   'https://gw.alipayobjects.com/zos/rmsportal/dURIMkkrRFpPgTuzkwnB.png', // Ant Design
//   'https://gw.alipayobjects.com/zos/rmsportal/sfjbOqnsXXJgNCjCzDBL.png', // Ant Design Pro
//   'https://gw.alipayobjects.com/zos/rmsportal/siCrBXXhmvTQGWPNLBow.png', // Bootstrap
//   'https://gw.alipayobjects.com/zos/rmsportal/kZzEzemZyKLKFsojXItE.png', // React
//   'https://gw.alipayobjects.com/zos/rmsportal/ComBAopevLwENQdKWiIn.png', // Vue
//   'https://gw.alipayobjects.com/zos/rmsportal/nxkuOJlFJuAUhzlMTCEe.png', // Webpack
// ];

function fakeList(): ListMenu[] {
  const list = menus;
  // for (let i = 0; i < count; i += 1) {
  //   list.push({
  //     id: `fake-list-${i}`,
  //     title: titles[i % 8],
  //     avatar: avatars[i % 8],
  //   });
  // }

  return list;
}

function getFakeList(req: Request, res: Response) {
  const result = fakeList();
  console.info(result)
  return res.json(result);
}

export default {
  'GET  /api/menu/list': getFakeList,
};
