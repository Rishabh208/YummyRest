package com.rishabh.yummyrest.repo;

import com.rishabh.yummyrest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
//    List<Product> findTop2ByPriceBetweenOrderByPriceAsc(int minPrice, int maxPrice);
@Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice ORDER BY p.price ASC")
List<Product> findTop2ProductsByPriceRange(
        @Param("minPrice") int minPrice,
        @Param("maxPrice") int maxPrice
);
}
