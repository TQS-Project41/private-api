package com.example.demo.repository;

import com.example.demo.models.SavedList;
import com.example.demo.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SavedListRepository extends JpaRepository<SavedList, Long> {

  public Page<SavedList> findByProductListUser(User user, Pageable page);

}
