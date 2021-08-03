package eu.daxiongmao.controller;

import eu.daxiongmao.wordpress.db.model.WpUser;
import eu.daxiongmao.wordpress.db.services.WpUserTransactionalService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
