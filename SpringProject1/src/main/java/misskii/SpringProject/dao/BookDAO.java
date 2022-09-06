package misskii.SpringProject.dao;

import misskii.SpringProject.models.Book;
import misskii.SpringProject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private  final  JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id},new BookMapper()).stream().findAny().orElse(null);

    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, year_of_publishing, author) VALUES(?,?,?)", book.getName(), book.getYear_of_publishing(), book.getAuthor());
    }

    public void update(Book book) {
        jdbcTemplate.update("UPDATE book SET name=?, author=?, year_of_publishing=? WHERE book_id=?", book.getName(), book.getAuthor(), book.getYear_of_publishing(), book.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book Join Person ON Book.person_id=Person.person_id WHERE book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void releaseBook(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", id);
    }

    public void assignBook(int id, Person person) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", person.getId(), id);
    }


//   id public void assign(Book book) {
//        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", book.getPerson_id(), )
//    }
}
