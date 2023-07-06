package eu.daxiongmao.controller;

import eu.daxiongmao.wordpress.db.model.WpUser;
import eu.daxiongmao.wordpress.db.services.WpUserTransactionalService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/wordpress/wxr")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WordpressWxrController {

    private final WpUserTransactionalService wpUserService;

    @Inject
    public WordpressWxrController(WpUserTransactionalService wpUserService) {
        this.wpUserService = wpUserService;
    }

    @GET
    @Path("/getAuthors")
    public List<WpUser> getAuthors() {
        final List<WpUser> authors = wpUserService.getAuthors();
        return wpUserService.getAuthors();
    }

}
