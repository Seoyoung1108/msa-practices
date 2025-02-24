package emaillist.repository;

import emaillist.domain.Email;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmaillistRepository {

    private final SqlSession sqlSession;

    public EmaillistRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Email> findAll(String keyword) {
        return sqlSession.selectList("emaillist.findAll", keyword);
    }

    public boolean insert(Email email) {
        return sqlSession.insert("emaillist.insert", email) == 1;
    }

    public boolean deleteById(Long id) {
        return sqlSession.delete("emaillist.deleteById", id) == 1;
    }
}
