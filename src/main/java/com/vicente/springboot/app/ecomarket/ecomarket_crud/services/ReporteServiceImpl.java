package com.vicente.springboot.app.ecomarket.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Reporte;
import com.vicente.springboot.app.ecomarket.ecomarket_crud.repository.ReporteRepository;

@Service
public class ReporteServiceImpl implements ReporteService{

    @Autowired
    private ReporteRepository repository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Reporte> findByAll() {
        return (List<Reporte>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reporte> findById(Long id) {
       return repository.findById(id);
    }

    @Override
    @Transactional
    public Reporte save(Reporte unReporte) {
        return repository.save(unReporte);
    }

    @Override
    @Transactional
    public Optional<Reporte> delete(Reporte unReporte) {
        Optional<Reporte> reporteOPtional = repository.findById(unReporte.getId());
        reporteOPtional.ifPresent(reporteDB->{
            repository.delete(unReporte);
        });
        return reporteOPtional;      
    }

}
