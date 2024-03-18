package com.msfb.cafe_finder_application.service.impl;

import com.msfb.cafe_finder_application.dto.request.CafeRequest;
import com.msfb.cafe_finder_application.dto.request.PageCafeRequest;
import com.msfb.cafe_finder_application.dto.request.UpdateCafeRequest;
import com.msfb.cafe_finder_application.entity.Cafe;
import com.msfb.cafe_finder_application.entity.CafeOwner;
import com.msfb.cafe_finder_application.entity.Image;
import com.msfb.cafe_finder_application.repository.CafeRepository;
import com.msfb.cafe_finder_application.service.CafeService;
import com.msfb.cafe_finder_application.service.ImageService;
import com.msfb.cafe_finder_application.service.OwnerService;
import com.msfb.cafe_finder_application.specification.CafeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {
    private final CafeRepository cafeRepository;
    private final ImageService imageService;
    private final OwnerService ownerService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCafe(CafeRequest cafeRequest) {
        CafeOwner owner = ownerService.getById(cafeRequest.getOwnerId());
        if (cafeRequest.getImage() == null || cafeRequest.getImage().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image is required");
        }
        Image image = imageService.create(cafeRequest.getImage());

        Cafe.CafeBuilder cafeBuilder = Cafe.builder();
        cafeBuilder.id(UUID.randomUUID().toString());
        cafeBuilder.cafeName(cafeRequest.getCafeName());
        cafeBuilder.location(cafeRequest.getLocation());
        cafeBuilder.phoneNumber(cafeRequest.getPhoneNumber());
        cafeBuilder.address(cafeRequest.getAddress());
        cafeBuilder.urlLocation(cafeRequest.getUrlLocation());
        cafeBuilder.owner(owner);
        cafeBuilder.image(image);

        cafeRepository.insert(cafeBuilder.build());
    }

    @Transactional(readOnly = true)
    @Override
    public Cafe findCafeById(String id) {
        return cafeRepository.findCafeById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cafe not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Cafe> getAllCafes(PageCafeRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        Specification<Cafe> specification = CafeSpecification.getSpecification(request);

        return cafeRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Cafe> getCafeByCafeName(String cafeName) {
        return cafeRepository.findAllByCafeName(cafeName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCafe(UpdateCafeRequest request) {
        Cafe cafe = findCafeById(request.getId());
        Image oldImage = cafe.getImage();

        cafe.setCafeName(request.getCafeName());
        cafe.setLocation(request.getLocation());
        cafe.setPhoneNumber(request.getPhoneNumber());
        cafe.setAddress(request.getAddress());
        cafe.setUrlLocation(request.getUrlLocation());

        if (request.getImage() != null) {
            Image image = imageService.create(request.getImage());
            cafe.setImage(image);

            if (oldImage != null) {
                imageService.deleteById(oldImage.getId());
            }
        }
        cafeRepository.updateCafe(cafe);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Cafe cafe = findCafeById(id);
        cafeRepository.deleteMenuById(cafe.getId());
    }
}
