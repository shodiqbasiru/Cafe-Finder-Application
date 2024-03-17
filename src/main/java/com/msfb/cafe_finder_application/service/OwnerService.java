package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.dto.request.UpdateOwnerRequest;
import com.msfb.cafe_finder_application.entity.CafeOwner;

import java.util.List;

public interface OwnerService {
    void create(CafeOwner owner);
    CafeOwner getById(String id);
    List<CafeOwner> getAllData();
    void update(UpdateOwnerRequest request);
    void delete(String id);
}
