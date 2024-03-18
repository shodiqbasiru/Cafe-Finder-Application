package com.msfb.cafe_finder_application.service.impl;

import com.msfb.cafe_finder_application.dto.request.UpdateOwnerRequest;
import com.msfb.cafe_finder_application.entity.Account;
import com.msfb.cafe_finder_application.entity.CafeOwner;
import com.msfb.cafe_finder_application.repository.OwnerRepository;
import com.msfb.cafe_finder_application.service.AccountService;
import com.msfb.cafe_finder_application.service.OwnerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final AccountService accountService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(CafeOwner owner) {
        ownerRepository.insert(owner);
    }

    @Transactional(readOnly = true)
    @Override
    public CafeOwner getById(String id) {
        return ownerRepository.findOwnerById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cafe owner not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CafeOwner> getAllData() {
        return ownerRepository.findAllOwners();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UpdateOwnerRequest request) {
        CafeOwner owner = getById(request.getId());

        owner.setName(request.getOwnerName());
        owner.setEmail(request.getEmail());
        owner.setNumberPhone(request.getNumberPhone());

        ownerRepository.update(owner);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        CafeOwner currentOwner = getById(id);
        Account account = accountService.getByUserId(currentOwner.getAccount().getId());
        account.setIsEnable(false);
    }
}
