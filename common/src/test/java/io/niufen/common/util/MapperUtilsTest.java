package io.niufen.common.util;

import io.niufen.common.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

@Slf4j
public class MapperUtilsTest {

    @Test
    public void tableNameByClass() {
        Assert.assertEquals("t_sys_user",MapperUtils.tableNameByEntity(new SysUserEntity()));
        Assert.assertEquals("b_sys_role",MapperUtils.tableNameByEntity(new SysRoleEntity()));
        Assert.assertEquals("sys_user_role_rel",MapperUtils.tableNameByEntity(new SysUserRoleRelEntity()));
    }

    @Test
    public void camelToUnderline() {
        System.out.println(MapperUtils.camelToUnderline("sysUser"));
    }

    @Test
    public void removeEntityStr() {
        System.out.println(MapperUtils.removeEntityStr("sysUserEntity"));
    }

    @Data
    class SysUserEntity implements Serializable {
        private static final long serialVersionUID = 1302771408586990491L;
        private Long id;
        private String username;
    }

    @Data
    @TableName(value = "b_")
    class SysRoleEntity implements Serializable {
        private static final long serialVersionUID = 3447715908340183111L;
        private Long id;
        private String name;
    }

    @Data
    @TableName(value = "")
    class SysUserRoleRelEntity implements Serializable {
        private static final long serialVersionUID = 3447715908340183111L;
        private Long id;
        private String name;
    }

}
