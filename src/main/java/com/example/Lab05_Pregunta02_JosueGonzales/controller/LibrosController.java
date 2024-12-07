package com.example.Lab05_Pregunta02_JosueGonzales.controller;

import com.example.Lab05_Pregunta02_JosueGonzales.model.Libros;
import com.example.Lab05_Pregunta02_JosueGonzales.repository.LibrosRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LibrosController {
    @Autowired
    private LibrosRepository librosRepository;

    // Procesar la creación de un nuevo libro
    @PostMapping("/submitLibro")
    public String procesarLibro(@ModelAttribute Libros libro, Model model) {
        // Guardar el libro en la base de datos
        librosRepository.save(libro);
        // Agregar el libro al modelo para mostrarlo en la vista
        model.addAttribute("libro", libro);
        return "redirect:/listarLibros";
    }
    
    // Mostrar todos los libros guardados
    @GetMapping("/listarLibros")
    public String listarLibros(Model model) {
        // Obtener todos los libros desde la base de datos
        List<Libros> libros = librosRepository.findAll();
        
        // Agregar la lista de libros al modelo
        model.addAttribute("libros", libros);
        model.addAttribute("libro", new Libros());
        return "listar_libros"; // Vista donde mostraremos los datos en una tabla
    }
    
    // Mostrar formulario para editar un libro (cargar datos de la base)
    @GetMapping("/editarLibro/{id}")
    public String editarLibro(@PathVariable("id") Long id, Model model) {
        Libros libro = librosRepository.findById(id).orElse(null);
        if (libro != null) {
            model.addAttribute("libro", libro); // Cargar el libro para editar
            return "formulario_libros";
        }
        return "redirect:/listarLibros"; // Si no se encuentra, redirigimos
    }
    
    // Procesar la edición del libro
    @PostMapping("/submitEdicionLibro")
    public String submitEdicion(@ModelAttribute Libros libro, Model model) {
        if (libro.getId() != null) {
            // Buscar el libro en la base de datos
            Libros libroExistente = librosRepository.findById(libro.getId()).orElse(null);
            if (libroExistente != null) {
                // Actualizamos los campos del libro con los nuevos datos
                libroExistente.setNombre(libro.getNombre());
                libroExistente.setAnioPublicacion(libro.getAnioPublicacion());
                libroExistente.setAutor(libro.getAutor());
                
                // Guardamos el libro actualizado (esto realiza un UPDATE)
                librosRepository.save(libroExistente);
                model.addAttribute("libro", libroExistente);
            }
        }
        return "redirect:/listarLibros"; // Redirigir al listado después de editar
    }
    
    // Eliminar un libro
    @GetMapping("/eliminarLibro/{id}")
    public String eliminarLibro(@PathVariable("id") Long id) {
        librosRepository.deleteById(id); // Eliminamos el libro por su ID
        return "redirect:/listarLibros"; // Redirigir al listado después de eliminar
    }
}
