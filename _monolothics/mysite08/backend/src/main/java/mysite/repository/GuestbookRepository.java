package mysite.repository;

import java.util.List;

import mysite.domain.Guestbook;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class GuestbookRepository {
	private final SqlSession sqlSession;

	public GuestbookRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<Guestbook> findAll(Long id) {
		return sqlSession.selectList("guestbook.findAllById", id);
	}

	public boolean insert(Guestbook guestbook) {
		return sqlSession.insert("guestbook.insert", guestbook) == 1;
	}
	
	public boolean deleteByIdAndPassword(Guestbook guestbook) {
		return sqlSession.delete("guestbook.deleteByIdAndPassword", guestbook) == 1;
	}
}