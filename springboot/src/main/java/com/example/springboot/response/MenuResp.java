package com.example.springboot.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * @date： 2024/8/20 23:09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResp {

    private String menuId;
    private String parentId;
    private String menuName;
    private String menuUrl;
    private String menuIcon;
    private Integer menuOrder;
    private String permission;
    private List<MenuResp> children;

    public void addChild(MenuResp child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }
}
