package com.msfb.cafe_finder_application.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse {
    private Integer totalPages;
    private Long totalElements;
    private Integer page;
    private Integer size;
    private Boolean hasNext;
    private Boolean hasPrevious;
}
