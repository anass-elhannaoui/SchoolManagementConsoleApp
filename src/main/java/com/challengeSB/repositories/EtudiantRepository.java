package com.challengeSB.repositories;

import com.challengeSB.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {
    Etudiant findByEmail(String email);
    boolean existsByEmail(String email);
}
