package com.library.bookgallery.repository;

import com.library.bookgallery.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    List<Authority> findByUsername(String username);

    boolean deleteById(long id);

}
