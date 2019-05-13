package com.weiyi.wx.order.request;

public class AddRoleRequest extends BaseRequest
{
    //角色名称
    private String roleName;

    //角色描述
    private String roleDesc;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
