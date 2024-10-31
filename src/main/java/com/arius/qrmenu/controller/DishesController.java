package com.arius.qrmenu.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.arius.qrmenu.common.ByteToMultipartFileConverter;
import com.arius.qrmenu.common.Constants;
import com.arius.qrmenu.form.DishesForm;
import com.arius.qrmenu.form.DishesResponse;
import com.arius.qrmenu.model.Dishes;
import com.arius.qrmenu.service.DishesService;
import com.arius.qrmenu.service.IStorageService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping ("/api/dishes")
@CrossOrigin
public class DishesController {

    @Autowired
    private DishesService dishesService;

    @Autowired
    private IStorageService imageService;

    @GetMapping ("")
    public ResponseEntity<Page<Dishes>> getAllDishes(@RequestParam ("page") final int page,
        @RequestParam ("size") final int size, @RequestParam ("sortBy") final String sortBy) {

        return new ResponseEntity<Page<Dishes>>(dishesService.findAll(sortBy, page, size), HttpStatus.OK);
    }

    @PostMapping ("/add")
    @ResponseBody
    public ResponseEntity<?> insertDishes(@Valid @RequestPart final DishesForm dishesForm,
        @RequestPart ("Files") final MultipartFile file) {

        List<String> error = new ArrayList<>();
        Dishes dishesByName = dishesService.getDishesByName(dishesForm.getName());
        if (dishesByName != null) {
            error.add(Constants.DishesValidation.DISHES_NAME_EXIST);
        }

        if (error.size() > 0) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("errors", error);
            response.put("timestamp", new Date());

            return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
        }

        try {
            Dishes dishes = new Dishes();
            BeanUtils.copyProperties(dishesForm, dishes);
            String files = imageService.storeFile(file);
            dishes.setImage(files);
            dishesService.save(dishes);
            return new ResponseEntity<String>(files, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping ("/file")
    public ResponseEntity<?> convert(@RequestBody final Dishes dishes) {
        try {
            byte[] imageFile = imageService.readFileContent(dishes.getImage());
            MultipartFile multipartFile = ByteToMultipartFileConverter.convert(imageFile, dishes.getImage());
            Map<String, Object> files = new HashMap<>();
            files.put("name", multipartFile.getName());
            files.put("size", multipartFile.getSize());
            files.put("type", multipartFile.getContentType());
            files.put("lastModified", System.currentTimeMillis());
            files.put("byte", imageFile);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping ("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateDishes(@Valid @RequestPart final DishesForm dishesForm,
        @RequestPart ("Files") final MultipartFile file, @PathVariable ("id") final Long id) {

        try {
            Dishes dishes = new Dishes();
            BeanUtils.copyProperties(dishesForm, dishes);
            String files = imageService.storeFile(file);
            dishes.setImage(files);
            if (id != null) {
                dishesService.updateDishes(id, dishes);
                return new ResponseEntity<String>(files, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping ("/{id}")
    public ResponseEntity<Dishes> getDishesById(@PathVariable ("id") final Long id) {

        if (id != null) {
            Dishes dish = dishesService.findById(id)
                .get();
            return new ResponseEntity<Dishes>(dish, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping ("/search")
    public ResponseEntity<Page<Dishes>> search(@RequestParam ("page") final int page,
        @RequestParam ("size") final int size, @RequestParam ("sortBy") final String sortBy,
        @RequestParam ("keyword") final String keyword) {

        return new ResponseEntity<Page<Dishes>>(dishesService.findByKeyword(keyword, sortBy, page, size),
            HttpStatus.OK);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?> deleteDishes(@PathVariable ("id") final Long id) {

        if (id != null) {
            dishesService.updateDeleteFlag(id);
            return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping ("/convertImage/{imageName}")
    public ResponseEntity<?> convertImage(@PathVariable ("imageName") final String imageName) {

        byte[] imageByte = imageService.readFileContent(imageName);
        byte[] data = Base64.getEncoder()
            .encodeToString(imageByte)
            .getBytes();
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
            .contentLength(data.length)
            .body(new ByteArrayResource(data));
    }

    @GetMapping ("/getDishesActive")
    public ResponseEntity<List<DishesResponse>> getDishesActive() {

        List<Dishes> listDishesActive = dishesService.findAllActiveDishes();
        List<DishesResponse> dishesResponse = new ArrayList<>();
        for (Dishes dishes : listDishesActive) {
            DishesResponse dishesres = new DishesResponse(dishes);
            dishesResponse.add(dishesres);
        }

        return new ResponseEntity<List<DishesResponse>>(dishesResponse, HttpStatus.OK);
    }
}
