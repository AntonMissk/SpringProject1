package misskii.SpringProject.controllers;

import misskii.SpringProject.dao.BookDAO;
import misskii.SpringProject.dao.PersonDAO;
import misskii.SpringProject.models.Book;
import misskii.SpringProject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO1) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO1;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.show(id));
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());
        return "books/show";
    }



    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book")@Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "books/new";
        bookDAO.create(book);
        return "redirect:/books";
    }


    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping ("/{id}")
    public String update(@ModelAttribute("book")@Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "books/edit";
        bookDAO.update(book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id")int id){
        bookDAO.releaseBook(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person")Person person, @PathVariable("id") int id){
        bookDAO.assignBook(id, person);
        return "redirect:/books/{id}";
    }
}
