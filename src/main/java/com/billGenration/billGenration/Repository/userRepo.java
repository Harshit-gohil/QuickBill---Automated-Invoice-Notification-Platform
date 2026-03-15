package com.billGenration.billGenration.Repository;

import com.billGenration.billGenration.model.order;
import com.billGenration.billGenration.model.users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepo extends JpaRepository<users,Long> {
}
