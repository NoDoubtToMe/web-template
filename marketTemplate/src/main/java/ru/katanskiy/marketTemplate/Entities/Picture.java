package ru.katanskiy.marketTemplate.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "picture")
public class Picture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "image_data")
    private byte[] image_data;

    @Column(name = "image_name")
    private String name;

    @Transient
    private MultipartFile multipartFile;


    @ManyToMany(mappedBy = "pictures")
    private List<Product> products;

}
