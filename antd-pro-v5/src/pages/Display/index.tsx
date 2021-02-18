// import {PageContainer} from '@ant-design/pro-layout';
import {Affix, PageHeader} from 'antd';
import { history} from 'umi';
/** 此方法会跳转到 redirect 参数所在的位置 */
const goto = () => {
  if (!history) return;
  setTimeout(() => {
    const { query } = history.location;
    const { redirect } = query as { redirect: string };
    history.push(redirect || '/');
  }, 10);
};

export default () => {
  return (
    // <PageContainer content="aaa">
    <div>
      <Affix >
        <PageHeader
          className="site-page-header"
          onBack={() => goto()}
          title=""
          subTitle="订单分析"
        />
      </Affix>
      <iframe style={{border: 0, width: "100%", height: "100%", position: "absolute"}}
              src="http://davinciadmintest.joymo.tech/share.html?shareToken=eNoNysEBADEEBMCWELE8Cfov6e41n6k4LYbnrHFU-5X5Vgit2WyuCODSuGTLINfjT7TDt0dE9ZCMedyhipGaY6nJYPvbkfZnKhm6M1vG0CJk8BXlwpvt5b1MNvhPxMlwqkl9C3SU0MTz2_w7N6iRB8UKI9zIpP0AV0grdA#share/dashboard"/>
    </div>
    // </PageContainer>
  );
};
