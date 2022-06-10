package com.gsnotes.dao;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInscriptionAnnuelle extends JpaRepository<InscriptionAnnuelle,Long> {

}
