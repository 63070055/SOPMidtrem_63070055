package com.example.week6.contorller;

import com.example.week6.pojo.Wizard;
import com.example.week6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public ResponseEntity<?> getWizards(){
        List<Wizard> wizards = wizardService.retrieveWizards();
        return ResponseEntity.ok(wizards);
    }
    @RequestMapping(value = "/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> addWizards(@RequestBody Wizard wizard){
        Wizard wizards = wizardService.creatWizard(wizard);
        return ResponseEntity.ok(wizards);
    }
    @RequestMapping(value = "/updateWizard", method = RequestMethod.POST)
    public ResponseEntity<?> updateWizards(@RequestBody Wizard wizard){
        Wizard wizards = wizardService.UpdateWizard(wizard);
        return ResponseEntity.ok(wizards);
    }
    @RequestMapping(value = "/deleteWizard", method = RequestMethod.POST)
    public ResponseEntity<?> deleteWizards(@RequestBody String id){
        Wizard w = wizardService.getID(id);
        boolean check = wizardService.DeleteWizard(w);
        return ResponseEntity.ok(check);
    }
}
