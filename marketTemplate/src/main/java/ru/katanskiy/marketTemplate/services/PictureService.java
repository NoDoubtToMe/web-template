package ru.katanskiy.marketTemplate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.katanskiy.marketTemplate.Entities.Picture;
import ru.katanskiy.marketTemplate.Repositories.PictureRepository;

import java.util.Optional;

@Service
public class PictureService {
    @Autowired
    private PictureRepository pictureRepository;

    public Picture save(Picture picture){
        return pictureRepository.save(picture);
    }

    public Optional<Picture> findById(Long id){
        return pictureRepository.findById(id);
    }
}
