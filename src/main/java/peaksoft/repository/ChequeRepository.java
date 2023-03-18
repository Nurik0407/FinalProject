package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.responses.cheque.ChequeResponse;
import peaksoft.entity.Cheque;

import java.time.LocalDate;
import java.util.List;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    @Query("select sum (m.price)from User u join u.cheques c join c.menuItems m where u.id=:id and " +
            "c.createdAt = :date")
    Integer getTopByCreatedAt(LocalDate date,Long id);

@Query("select avg(m.price) from Restaurant r join r.users u join u.cheques c join c.menuItems m where r.id=1 and c.createdAt=:date")
    Integer avg(LocalDate date);
}