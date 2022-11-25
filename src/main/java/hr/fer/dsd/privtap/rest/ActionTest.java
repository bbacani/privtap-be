package hr.fer.dsd.privtap.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seeAction")
public class ActionTest {

    @PostMapping()
    public String letsSeeThisAction(){
        return "Vediamo un po!";
    }
}
