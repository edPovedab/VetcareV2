package com.Vetcare.Controller;

import com.Vetcare.domain.Producto;
import com.Vetcare.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listarProductos(Model model) {
        var productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        return "productos";
    }
    
    @GetMapping("/admin")
    public String adminProductos(Model model) {
        var productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        return "admin/productos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("accion", "Agregar");
        return "admin/producto-form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.getProducto(new Producto(id));
        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", "El producto no existe");
            return "redirect:/admin/productos";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("accion", "Editar");
        return "admin/producto-form";
    }

    @PostMapping("/guardar")
    public String guardarProducto(Producto producto, RedirectAttributes redirectAttributes) {
        productoService.guardar(producto);
        redirectAttributes.addFlashAttribute("success", "Producto guardado con éxito");
        return "redirect:/admin/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Producto producto = new Producto(id);
        productoService.eliminar(producto);
        redirectAttributes.addFlashAttribute("success", "Producto eliminado con éxito");
        return "redirect:/admin/productos";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleProducto(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.getProducto(new Producto(id));
        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", "El producto no existe");
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        return "detalle-producto";
    }
}