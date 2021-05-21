package ru.katanskiy.marketTemplate.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.katanskiy.marketTemplate.Entities.Category;
import ru.katanskiy.marketTemplate.Entities.Product;
import ru.katanskiy.marketTemplate.Entities.Picture;
import ru.katanskiy.marketTemplate.services.CategoryService;
import ru.katanskiy.marketTemplate.services.PictureService;
import ru.katanskiy.marketTemplate.services.ProductService;
import ru.katanskiy.marketTemplate.utils.ProductFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private  ProductService productService;
    @Autowired
    private  CategoryService categoryService;
    @Autowired
    private PictureService pictureService;

    @GetMapping()
    public String indexAdmin(Model model, @RequestParam Map<String,String> params){
        int pageIndex = 0;
        if(params.containsKey("p")){
            pageIndex = Integer.parseInt(params.get("p") )-1;
        }
        Pageable pageable = PageRequest.of(pageIndex, 50);
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> page = productService.findAll(productFilter.getSpec(), pageable);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("productFiler", productFilter.getFilterDefinition());
        model.addAttribute("categories", categories);
        model.addAttribute("page", page);
        return "indexAdmin";
    }

    @GetMapping("/load")
    public String loadImage(Model model){
        Picture image = new Picture();
        image.setProducts(new ArrayList<Product>(productService.findAll()));
        model.addAttribute("imageData", image);
        return "loadImage";
    }

    @PostMapping("/load")
    public  String saveImage(@ModelAttribute(name = "imageData")Picture picture,
                             @RequestParam(name = "products") Product product){
        try {
            picture.setImage_data(picture.getMultipartFile().getBytes());
            picture.setName(picture.getMultipartFile().getOriginalFilename());

            for(Product i : picture.getProducts()){
                System.out.println(i.getClass() );
                System.out.println(i.getId() + "   " + i.getTitle());
            }
            pictureService.save(picture);
            System.out.println("Image saved.....");
        } catch (IOException e) {
            System.out.println("IO_Exeption");
        }
        return "redirect:/";
    }


    @GetMapping("pic/{id}")
    public String viewImage(Model model, @PathVariable Long id){
        Product product = productService.findById(id);
        Optional<Picture> picture = pictureService.findById(id);
        List<Picture> pictures = product.getPictures();


        System.out.println("_______________________________________");
        System.out.println(picture.get().getId());
        System.out.println(picture.get().getImage_data());
        for(Product i : picture.get().getProducts()) {
            System.out.println(i);
            System.out.println("pic");
        }
        System.out.println("_______________________________________");
        System.out.println(product.getId());
        System.out.println(product.getCategory());
        for(Picture i : product.getPictures()) {
            System.out.println(i);
            System.out.println("prod");
        }
        System.out.println("_______________________________________");


        model.addAttribute("picture", picture.get().getImage_data());
        return "viewImage";
    }

}


//    Сохранение файлов
//    @PostMapping("/load")
//    public  String saveImage(@ModelAttribute(name = "imageData")Picture pic){
//        try {
//            byte[]bytes = pic.getMultipartFile().getBytes();
//            Path path = Paths.get("C:/Users/Andrey/Desktop/" + pic.getMultipartFile().getOriginalFilename());
//            Files.write(path, bytes);
//            System.out.println("Image saved.....");
//
//        } catch (IOException e) {
//            System.out.println("IO Exeption");
//        }
//        return "redirect:/";
//    }