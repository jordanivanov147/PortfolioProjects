package portfolio.projects.chillblog.dao;

import portfolio.projects.chillblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
