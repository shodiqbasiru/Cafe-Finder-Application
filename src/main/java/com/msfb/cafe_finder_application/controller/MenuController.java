package com.msfb.cafe_finder_application.controller;

import com.msfb.cafe_finder_application.constant.RouteApiConstant;
import com.msfb.cafe_finder_application.dto.request.MenuRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateMenuRequest;
import com.msfb.cafe_finder_application.dto.response.CommonResponse;
import com.msfb.cafe_finder_application.entity.Menu;
import com.msfb.cafe_finder_application.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteApiConstant.MENU_API)
public class MenuController {
    private final MenuService menuService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Menu>> create(@RequestBody MenuRequest request) {
        menuService.createMenu(request);
        CommonResponse<Menu> response = CommonResponse.<Menu>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Created a new menu successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Menu>> getById(@PathVariable String id) {
        Menu result = menuService.findById(id);
        CommonResponse<Menu> response = CommonResponse.<Menu>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Menu found")
                .data(result)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> getAll() {
        List<Menu> menus = menuService.getAll();
        CommonResponse<?> response = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all menus successfully")
                .data(menus)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Menu>> update(@RequestBody UpdateMenuRequest request) {
        menuService.update(request);
        CommonResponse<Menu> response = CommonResponse.<Menu>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Updated menu successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Menu>> deleteById(@PathVariable String id) {
        menuService.deleteById(id);
        CommonResponse<Menu> response = CommonResponse.<Menu>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Deleted menu successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
