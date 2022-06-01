package com.example.demo.Repository;

import com.example.demo.Models.SavedList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SavedListRepository extends JpaRepository<SavedList, Long> {
  
}
