package com.gsnotes.services.impl;

import com.gsnotes.bo.Niveau;
import com.gsnotes.dao.INiveauDao;
import com.gsnotes.services.INiveauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class NiveauServiceImpl implements INiveauService {
    @Autowired
    INiveauDao niveauDao;

    @Override
    public void addNiveau(Niveau niveau) {
        niveauDao.save(niveau);
    }

    @Override
    public Niveau getNiveau(Long id) {

        return niveauDao.getById(id);
    }

    @Override
    public boolean existsById(Long id) {

        return  niveauDao.existsById(id);

    }
}
