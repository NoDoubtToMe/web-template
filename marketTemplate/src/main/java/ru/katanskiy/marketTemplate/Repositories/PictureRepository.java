package ru.katanskiy.marketTemplate.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.katanskiy.marketTemplate.Entities.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}
