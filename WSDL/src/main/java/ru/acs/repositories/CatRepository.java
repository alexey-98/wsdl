package ru.acs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.acs.entities.CatEntity;

@Repository
public interface CatRepository extends JpaRepository<CatEntity, Integer> {

}
