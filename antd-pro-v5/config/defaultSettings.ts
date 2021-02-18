import { Settings as LayoutSettings } from '@ant-design/pro-layout';
// doc https://procomponents.ant.design/components/layout
const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  primaryColor: '#28a745',
  layout: 'top',
  contentWidth: 'Fixed',
  fixedHeader: true,
  fixSiderbar: false,
  colorWeak: false,
  //layout 的左上角 的 title
  title: 'Joymo Gaze',
  pwa: false,
  //layout 的左上角 logo 的 url
  logo: 'http://navi.joymo.tech/static/img/logo.png',
  // logo: 'https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg',
  iconfontUrl: '',
};

export default Settings;
