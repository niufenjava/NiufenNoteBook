// 一级菜单
export interface ListMenu {
  id: string;
  title: string;
  avatar: string;
  subMenus: ListSubMenu[]
}

// 二级菜单
export interface ListSubMenu {
  id: string;
  title: string;
  avatar: string;
}


export interface ListItemDataType {
  id: string;
  title: string;
  avatar: string;
}
