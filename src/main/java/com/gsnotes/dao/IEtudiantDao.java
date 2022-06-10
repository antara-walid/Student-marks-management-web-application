package com.gsnotes.dao;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEtudiantDao extends JpaRepository<Etudiant, Long> {
    public Etudiant getEtudiantByCne(String cin);
}
