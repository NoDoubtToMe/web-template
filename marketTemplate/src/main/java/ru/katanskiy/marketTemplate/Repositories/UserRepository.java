package ru.katanskiy.marketTemplate.Repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.katanskiy.marketTemplate.Entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByPhone(String phone);
    boolean existsByPhone(String phone);
}
