package ru.vtb.opera.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vtb.opera.entities.Opera;

import javax.persistence.LockModeType;
import java.util.List;

public interface OperaRepository extends JpaRepository<Opera, Long> {
    @Query("SELECT o FROM Opera o WHERE o.name LIKE :name")
    List<Opera> findLikeName(@Param("name") String name);

    @Query("SELECT o FROM Opera o WHERE o.name LIKE :name AND o.description LIKE :desc")
    List<Opera> findLikeNameDesc(@Param("name") String name, @Param("desc") String desc);

    List<Opera> findByName(String name);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select o from Opera o where o.id = :id")
    Opera getForUpdate(Long id);
}
