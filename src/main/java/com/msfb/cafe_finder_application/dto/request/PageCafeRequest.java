package com.msfb.cafe_finder_application.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageCafeRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String cafeName;
    private String location;
}
