package com.gsnotes.services;

import com.gsnotes.bo.Niveau;
import org.springframework.stereotype.Service;


public interface INiveauService {
    public void addNiveau(Niveau niveau);
    public Niveau getNiveau(Long id);
    public boolean existsById(Long id);
}
