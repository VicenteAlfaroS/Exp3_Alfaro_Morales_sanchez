package com.vicente.springboot.app.ecomarket.ecomarket_crud.services;

import java.util.List;
import java.util.Optional;

import com.vicente.springboot.app.ecomarket.ecomarket_crud.entities.Reporte;

public interface ReporteService {

    List<Reporte> findByAll();

    Optional<Reporte> findById(Long id);

    Reporte save(Reporte unReporte);

    Optional<Reporte> delete(Reporte unReporte);

}
