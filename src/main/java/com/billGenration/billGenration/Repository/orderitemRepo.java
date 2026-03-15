package com.billGenration.billGenration.Repository;

import com.billGenration.billGenration.model.order;
import com.billGenration.billGenration.model.orderitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderitemRepo extends JpaRepository<orderitem,Long> {
}
