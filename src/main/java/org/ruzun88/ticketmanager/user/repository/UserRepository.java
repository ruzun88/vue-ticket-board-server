package org.ruzun88.ticketmanager.user.repository;

import org.ruzun88.ticketmanager.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    UserInfo save(UserInfo build);

    Optional<UserInfo> findByEmail(String email);
}
