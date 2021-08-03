package eu.daxiongmao.wordpress;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
	info = @Info(
		title = "Wordpress backup utility",
		description = "To backup and re-construct the wordpress site instead of just applying a standard backup",
		version = "1.0.0-SNAPSHOT",
		contact = @Contact(
				name = "Guillaume Diaz",
				url = "http://daxiongmao.eu",
				email = "guillaume@qin-diaz.com"),
		license = @License(
				name = "GNU GPL v3.0",
				url = "https://choosealicense.com/licenses/gpl-3.0/")
	)
)
public class WordpressUtilsApplication extends Application {
	// This is only for OpenAPI configuration
	// No details required, unlike SpringBoot
}
