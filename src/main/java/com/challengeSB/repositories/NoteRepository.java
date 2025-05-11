package com.challengeSB.repositories;

import com.challengeSB.model.Etudiant;
import com.challengeSB.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findByEtudiant(Etudiant etudiant);
}
