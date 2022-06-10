package com.gsnotes.services.impl;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.dao.IInscriptionAnnuelle;
import com.gsnotes.services.IInscriptionAnnuelleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class InscriptionAnnuelleService implements IInscriptionAnnuelleService {

    @Autowired
    IInscriptionAnnuelle inscriptionAnnuelleDao;

    @Override
    public String createInscription(InscriptionAnnuelle ia) {

        inscriptionAnnuelleDao.save(ia);
        return null;
    }


}
