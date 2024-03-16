package com.msfb.cafe_finder_application.controller;

import com.msfb.cafe_finder_application.constant.RouteApiConstant;
import com.msfb.cafe_finder_application.dto.request.CafeRequest;
import com.msfb.cafe_finder_application.dto.request.PageCafeRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateCafeRequest;
import com.msfb.cafe_finder_application.dto.response.CommonResponse;
import com.msfb.cafe_finder_application.dto.response.PageResponse;
import com.msfb.cafe_finder_application.entity.Cafe;
import com.msfb.cafe_finder_application.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteApiConstant.CAFE_API)
public class CafeController {
    private final CafeService cafeService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Cafe>> create(@RequestBody CafeRequest request) {
       cafeService.createCafe(request);
        CommonResponse<Cafe> response = CommonResponse.<Cafe>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Created a new cafe successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Cafe>> getById(@PathVariable String id) {
        Cafe result = cafeService.getCafeById(id);
        CommonResponse<Cafe> response = CommonResponse.<Cafe>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cafe found")
                .data(result)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "orderBy", defaultValue = "asc") String direction,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "location", required = false) String location
    ) {
        PageCafeRequest pageCafeRequest = PageCafeRequest.builder()
                .page(page)
                .size(size)
                .direction(direction)
                .sortBy(sortBy)
                .cafeName(name)
                .location(location)
                .build();
        Page<Cafe> result = cafeService.getAllCafes(pageCafeRequest);
        List<Cafe> cafeResponses = result.getContent().stream()
                .map(cafe -> Cafe.builder()
                        .id(cafe.getId())
                        .cafeName(cafe.getCafeName())
                        .phoneNumber(cafe.getPhoneNumber())
                        .location(cafe.getLocation())
                        .address(cafe.getAddress())
                        .urlLocation(cafe.getUrlLocation())
                        .menus(cafe.getMenus())
                        .build())
                .toList();

        PageResponse pageResponse = PageResponse.builder()
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .page(result.getPageable().getPageNumber() + 1)
                .size(result.getPageable().getPageSize())
                .hasNext(result.hasNext())
                .hasPrevious(result.hasPrevious())
                .build();

        CommonResponse<List<Cafe>> responses = CommonResponse.<List<Cafe>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all data successfully")
                .data(cafeResponses)
                .pages(pageResponse)
                .build();
        return ResponseEntity.ok(responses);
    }

    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<Cafe>>> findByCafeName(
            @RequestParam(required = false) String name
    ){
        List<Cafe> result = cafeService.getCafeByCafeName(name);
        CommonResponse<List<Cafe>> response = CommonResponse.<List<Cafe>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cafe found")
                .data(result)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Cafe>> update(@RequestBody UpdateCafeRequest request) {
        cafeService.updateCafe(request);
        CommonResponse<Cafe> response = CommonResponse.<Cafe>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cafe updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> delete(@PathVariable String id) {
        cafeService.deleteById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cafe deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
