package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.ProductListItem;
import com.example.demo.models.ListItemId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductListItemRepository extends JpaRepository<ProductListItem, ListItemId> {

  public List<ProductListItem> findByListId(Long productListId);

  public Optional<ProductListItem> findByListIdAndProductId(Long productListId, Long productId);
  
}
