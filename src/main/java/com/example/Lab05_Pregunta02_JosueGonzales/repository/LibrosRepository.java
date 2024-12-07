package com.example.Lab05_Pregunta02_JosueGonzales.repository;

import com.example.Lab05_Pregunta02_JosueGonzales.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrosRepository extends JpaRepository<Libros, Long> {
    // Aquí se pueden agregar consultas personalizadas si es necesario
}
