package misskii.SpringProject.dao;


import misskii.SpringProject.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book =new Book();
        book.setId(rs.getInt("book_id"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
//        book.setPerson_id(rs.getInt("person_id"));
        book.setYear_of_publishing(rs.getInt("year_of_publishing"));
        return book;
    }
}
