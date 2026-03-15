package com.billGenration.billGenration.Repository;

import com.billGenration.billGenration.model.order;
import com.billGenration.billGenration.model.product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface productRepo extends JpaRepository<product,Long> {

}
