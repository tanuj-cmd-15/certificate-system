package com.certificate.repository;

import com.certificate.model.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepository extends MongoRepository<Certificate, String> {
    
    Optional<Certificate> findByCertificateNumber(String certificateNumber);
    
    Optional<Certificate> findByRecipientEmail(String email);
}
