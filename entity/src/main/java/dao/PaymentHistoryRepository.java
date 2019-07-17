package dao;

import model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findAllByUser_Id(Long userId);
}
