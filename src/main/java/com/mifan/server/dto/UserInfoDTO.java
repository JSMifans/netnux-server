package com.mifan.server.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserInfoDTO {
    private Long id;
    private String phone;
    private String nickname;
    private RoleDTO role;
    private List<MenuDTO> menus;
}

@Data
class RoleDTO {
    private Long id;
    private String roleName;
}

@Data
class MenuDTO {
    private Long id;
    private Long parentId;
    private String name;
    private String path;
    private String component;
    private String icon;
    private Integer type;
    private String permission;
} 