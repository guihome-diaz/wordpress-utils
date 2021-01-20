package eu.daxiongmao.controller;

import eu.daxiongmao.wordpress.db.model.WpUser;
import eu.daxiongmao.wordpress.db.services.WpUserTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wordpress/wxr")
public class WordpressWxrController {

    private final WpUserTransactionalService wpUserService;

    @Autowired
    public WordpressWxrController(WpUserTransactionalService wpUserService) {
        this.wpUserService = wpUserService;
    }

    @GetMapping("/getAuthors")
    public ResponseEntity<List<WpUser>> getAuthors() {
        final List<WpUser> authors = wpUserService.getAuthors();
        return ResponseEntity.ok(authors);
    }

}
