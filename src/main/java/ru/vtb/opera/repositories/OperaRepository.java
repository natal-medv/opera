package ru.vtb.opera.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vtb.opera.repositories.entities.OperaEntity;

import javax.persistence.LockModeType;
import java.util.List;

public interface OperaRepository extends JpaRepository<OperaEntity, Long> {
    @Query("SELECT o FROM OperaEntity o WHERE o.name LIKE '%' || :name || '%'")
    List<OperaEntity> findLikeName(@Param("name") String name);

    @Query("SELECT o FROM OperaEntity o WHERE o.name LIKE :name AND o.description LIKE :desc")
    List<OperaEntity> findLikeNameDesc(@Param("name") String name, @Param("desc") String desc);

    List<OperaEntity> findByName(String name);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select o from OperaEntity o where o.id = :id")
    OperaEntity getForUpdate(Long id);
}
