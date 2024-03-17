package com.msfb.cafe_finder_application.service.impl;

import com.msfb.cafe_finder_application.dto.request.MenuRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateMenuRequest;
import com.msfb.cafe_finder_application.dto.response.MenuResponse;
import com.msfb.cafe_finder_application.entity.Cafe;
import com.msfb.cafe_finder_application.entity.Image;
import com.msfb.cafe_finder_application.entity.Menu;
import com.msfb.cafe_finder_application.repository.MenuRepository;
import com.msfb.cafe_finder_application.service.CafeService;
import com.msfb.cafe_finder_application.service.ImageService;
import com.msfb.cafe_finder_application.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final CafeService cafeService;
    private final ImageService imageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createMenu(MenuRequest request) {
        Cafe cafe = cafeService.findCafeById(request.getCafeId());
        Image image;
        Menu.MenuBuilder builder = Menu.builder();
        if (request.getImage() != null) {
            image = imageService.create(request.getImage());
            builder.image(image);
        }
        builder.menuName(request.getMenuName());
        builder.price(request.getPrice());
        builder.description(request.getDescription());
        builder.cafe(cafe);
        menuRepository.insert(builder.build());
    }

    @Override
    public Menu findById(String id) {
        return menuRepository.findMenuById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found"));
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UpdateMenuRequest request) {
        Menu menu = findById(request.getId());
        Image oldImage = menu.getImage();

        menu.setMenuName(request.getMenuName());
        menu.setPrice(request.getPrice());
        menu.setDescription(request.getDescription());

        if (request.getImage() != null) {
            Image image = imageService.create(request.getImage());
            menu.setImage(image);

            if (oldImage != null) {
                imageService.deleteById(oldImage.getId());
            }
        }
        menuRepository.updateMenu(menu);
    }

    @Override
    public void deleteById(String id) {
        Menu menu = findById(id);
        menuRepository.deleteMenuById(menu.getId());
    }
}
