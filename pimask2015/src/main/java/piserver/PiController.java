package piserver;

/**
 * Created by anupkher on 10/11/15.
 */

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class PiController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(PiController.class, args);
    }

}
