package repository;

import model.Blog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepo extends PagingAndSortingRepository<Blog,Integer> {

    @Query(value = "select b from Blog b where b.name like %:name%")
    Page<Blog> findByName(@Param(value = "name") String search);
}
