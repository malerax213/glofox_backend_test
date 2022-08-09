package com.glofox.dev.repository;

import com.glofox.dev.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface ClassRepository
    extends JpaRepository<Class, Integer> {

    @Query(value = "SELECT c.CLASS_ID FROM CLASS c WHERE :dateToFind BETWEEN c.START_DATE AND c.END_DATE", nativeQuery = true)
    List<Integer> existsClassWithDate(Date dateToFind);

    @Query(value = "SELECT c.CLASS_ID FROM CLASS c WHERE :name=c.NAME", nativeQuery = true)
    List<Integer> existsClassWithName(String name);

    Optional<Class> findById(Integer id);
}
