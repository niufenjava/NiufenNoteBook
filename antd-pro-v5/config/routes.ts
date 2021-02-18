export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        routes: [
          {
            name: 'login',
            path: '/user/login',
            component: './User/login',
          },
        ],
      },
    ],
  },
  {
    path: '/home',
    name: 'Home',
    icon: 'smile',
    component: './Home',
  },
  {
    path: '/',
    redirect: '/home',
  },
  {
    layout: false,
    name: '看板',
    icon: 'smile',
    path: '/display',
    component: './Display',
  },
  {
    component: './404',
  },
];
