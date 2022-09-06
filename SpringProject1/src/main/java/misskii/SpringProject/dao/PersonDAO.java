package misskii.SpringProject.dao;

import misskii.SpringProject.models.Book;
import misskii.SpringProject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM PERSON",new PersonMapper());

    }


    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE person_id=?", new Object[]{id},new PersonMapper()).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO PERSON(name, age) VALUES(?,?)", person.getName(), person.getAge());
    }

    public void update(Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, age=? WHERE person_id=?", person.getName(), person.getAge(), person.getId());

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", id);

    }

    public List<Book> hasBooks(int id) {
       return jdbcTemplate.query("SELECT Book.* FROM Person JOIN Book ON Person.person_id = Book.person_id WHERE Person.person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
