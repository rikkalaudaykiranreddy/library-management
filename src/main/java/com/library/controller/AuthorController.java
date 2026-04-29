package com.library.controller;

import com.library.exception.LibraryException;
import com.library.model.Author;
import com.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // ── LIST ──────────────────────────────────────────────────
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("pageTitle", "Authors");
        return "authors/list";
    }

    // ── ADD FORM ──────────────────────────────────────────────
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("pageTitle", "Add Author");
        return "authors/form";
    }

    // ── SAVE (CREATE) ─────────────────────────────────────────
    @PostMapping("/save")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add Author");
            return "authors/form";
        }
        try {
            authorService.saveAuthor(author);
            redirectAttributes.addFlashAttribute("successMsg", "Author added successfully!");
        } catch (LibraryException ex) {
            redirectAttributes.addFlashAttribute("errorMsg", ex.getMessage());
        }
        return "redirect:/authors";
    }

    // ── EDIT FORM ─────────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("author", authorService.getAuthorById(id));
            model.addAttribute("pageTitle", "Edit Author");
            return "authors/form";
        } catch (LibraryException ex) {
            redirectAttributes.addFlashAttribute("errorMsg", ex.getMessage());
            return "redirect:/authors";
        }
    }

    // ── UPDATE ────────────────────────────────────────────────
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Author");
            return "authors/form";
        }
        try {
            authorService.updateAuthor(id, author);
            redirectAttributes.addFlashAttribute("successMsg", "Author updated successfully!");
        } catch (LibraryException ex) {
            redirectAttributes.addFlashAttribute("errorMsg", ex.getMessage());
        }
        return "redirect:/authors";
    }
}
