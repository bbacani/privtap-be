package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.Action;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seeAction")
public class ActionTest {

    @PostMapping()
    public void letsSeeThisAction(@RequestBody Action action){

        System.out.println(action.toString());
    }
}
