package com.msfb.cafe_finder_application.service.impl;

import com.msfb.cafe_finder_application.dto.request.MenuRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateMenuRequest;
import com.msfb.cafe_finder_application.dto.response.MenuResponse;
import com.msfb.cafe_finder_application.entity.Cafe;
import com.msfb.cafe_finder_application.entity.Menu;
import com.msfb.cafe_finder_application.repository.MenuRepository;
import com.msfb.cafe_finder_application.service.CafeService;
import com.msfb.cafe_finder_application.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final CafeService cafeService;

    @Override
    public void createMenu(MenuRequest request) {
        Cafe cafe = cafeService.getCafeById(request.getCafeId());
        Menu menu = Menu.builder()
                .id(UUID.randomUUID().toString())
                .menuName(request.getMenuName())
                .price(request.getPrice())
                .description(request.getDescription())
                .cafe(cafe)
                .build();
        menuRepository.insert(menu);
    }

    @Override
    public Menu findById(String id) {
        return menuRepository.findMenuById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Menu not found"));
    }

    @Override
    public MenuResponse findMenuById(String id) {
        Menu menu = findById(id);
        return MenuResponse.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .cafeId(menu.getCafe().getId())
                .build();
    }

    @Override
    public List<MenuResponse> getAll() {
        return menuRepository.findAllMenus().stream().map(menu -> MenuResponse.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .cafeId(menu.getCafe().getId())
                .build()).toList();
    }

    @Override
    public void update(UpdateMenuRequest request) {
        Menu menu = findById(request.getId());

        menu.setMenuName(request.getMenuName());
        menu.setPrice(request.getPrice());
        menu.setDescription(request.getDescription());

        menuRepository.updateMenu(menu);
    }

    @Override
    public void deleteById(String id) {
        findById(id);
        menuRepository.deleteMenuById(id);
    }
}
