package com.example.demo.model.persistence.repositories;

import com.example.demo.model.persistence.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByName(String name);
}
