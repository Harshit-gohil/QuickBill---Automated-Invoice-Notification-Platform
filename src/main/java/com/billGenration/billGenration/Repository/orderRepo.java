package com.billGenration.billGenration.Repository;

import com.billGenration.billGenration.model.order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderRepo extends JpaRepository<order,Long> {
}
