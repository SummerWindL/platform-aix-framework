package com.platform.aix.service.user.menu;

import net.sf.json.JSONArray;

public interface UserAccountFunctionMenuService {

    // 获取帐号角色对应的功能菜单
    JSONArray getUserAccountFunctionMenuList(String acc);

}
