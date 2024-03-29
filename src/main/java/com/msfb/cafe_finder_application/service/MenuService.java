package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.dto.request.MenuRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateMenuRequest;
import com.msfb.cafe_finder_application.dto.response.MenuResponse;
import com.msfb.cafe_finder_application.entity.Menu;

import java.util.List;

public interface MenuService {
    void createMenu(MenuRequest request);
    Menu findById(String id);
    MenuResponse findMenuById(String id);
    List<MenuResponse> getAll();
    void update(UpdateMenuRequest request);
    void deleteById(String id);
}
