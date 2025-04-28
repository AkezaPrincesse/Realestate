package com.innova.realestate.services;

import com.innova.realestate.models.Lease;
import com.innova.realestate.repositories.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaseService {
    @Autowired
    private LeaseRepository leaseRepository;

    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }

    public Lease getLeaseById(Long id) {
        return leaseRepository.findById(id).orElse(null);
    }

    public Lease saveLease(Lease lease) {
        return leaseRepository.save(lease);
    }

    public void deleteLease(Long id) {
        leaseRepository.deleteById(id);
    }
}

