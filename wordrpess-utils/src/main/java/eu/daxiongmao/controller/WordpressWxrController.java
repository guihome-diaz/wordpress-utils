package eu.daxiongmao.controller;

import eu.daxiongmao.wordpress.db.model.WpUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wordpress/wxr")
public class WordpressWxrController {

    @GetMapping(name = "getAuthors")
    public ResponseEntity<List<WpUser>> getAuthors() {

    }

}
