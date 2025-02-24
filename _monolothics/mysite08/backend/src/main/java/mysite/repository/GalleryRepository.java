package mysite.repository;

import java.util.List;

import mysite.domain.Gallery;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class GalleryRepository {
	private final SqlSession sqlSession;

	public GalleryRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int insert(Gallery gallery) {
		return sqlSession.insert("gallery.insert", gallery);
	}

	public int delete(Long id) {
		return sqlSession.delete("gallery.deleteById", id);
	}

	public List<Gallery> findAll() {
		return sqlSession.selectList("gallery.findAll");
	}
}
