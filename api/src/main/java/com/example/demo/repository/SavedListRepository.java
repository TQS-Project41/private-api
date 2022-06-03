package com.example.demo.repository;

import com.example.demo.models.SavedList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SavedListRepository extends JpaRepository<SavedList, Long> {
  
}
