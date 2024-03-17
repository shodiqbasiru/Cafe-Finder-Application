package com.msfb.cafe_finder_application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msfb.cafe_finder_application.constant.RouteApi;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteApi.CAFE_API)
public class CafeController {
    private final CafeService cafeService;
    private final ObjectMapper objectMapper;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Cafe>> create(
            @RequestPart(name = "cafe") String jsonCafe,
            @RequestPart(name = "images", required = false) List<MultipartFile> images
    ) {
        CommonResponse.CommonResponseBuilder<Cafe> builder = CommonResponse.builder();
        try {
            CafeRequest request = objectMapper.readValue(jsonCafe, CafeRequest.class);
            request.setImages(images);

            cafeService.createCafe(request);
            builder.statusCode(HttpStatus.CREATED.value());
            builder.message("Created Data Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(builder.build());
        } catch (Exception e) {
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            builder.message(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(builder.build());
        }
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Cafe>> getById(@PathVariable String id) {
        Cafe result = cafeService.findCafeById(id);
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
                        .menus(cafe.getMenus().isEmpty() ? null : cafe.getMenus())
                        .reviews(cafe.getReviews().isEmpty() ? null : cafe.getReviews())
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
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Cafe>> update(
            @RequestPart(name = "cafe") String jsonCafe,
            @RequestPart(name = "image", required = false) List<MultipartFile> images
    ) {
        CommonResponse.CommonResponseBuilder<Cafe> builder = CommonResponse.builder();
        try {
            UpdateCafeRequest request = objectMapper.readValue(jsonCafe, UpdateCafeRequest.class);
            request.setImages(images);

            cafeService.updateCafe(request);
            builder.statusCode(HttpStatus.CREATED.value());
            builder.message("Updated Data Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(builder.build());
        } catch (Exception e) {
            builder.message(e.getMessage());
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(builder.build());
        }
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
