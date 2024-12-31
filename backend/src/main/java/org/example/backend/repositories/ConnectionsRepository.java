package org.example.backend.repositories;

import org.example.backend.models.entities.Connection;
import org.example.backend.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionsRepository extends JpaRepository<Connection, Integer> {
    List<Connection> findByFollower(User follower);
    List<Connection> findByFollowing(User following);
    boolean existsByFollowerAndFollowing(User follower, User following);
    void deleteByFollowerAndFollowing(User follower, User following);

    @Query("SELECT COUNT(c) FROM Connection c WHERE c.following = :user")
    int countFollowers(@Param("user") User user);

    @Query("SELECT COUNT(c) FROM Connection c WHERE c.follower = :user")
    int countFollowing(@Param("user") User user);
}
