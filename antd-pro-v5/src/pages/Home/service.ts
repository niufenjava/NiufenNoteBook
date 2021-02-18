import request from 'umi-request';
// import { ListItemDataType } from './data.d';

// export async function queryFakeList(params: ListItemDataType) {
//   return request('/api/fake_list', {
//     params,
//   });
// }

export async function queryMenuList() {
  return request('/api/menu/list', {});
}
