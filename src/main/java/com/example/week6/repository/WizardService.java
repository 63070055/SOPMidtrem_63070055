package com.example.week6.repository;

import com.example.week6.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;

    public WizardService(WizardRepository repository){
        this.repository = repository;
    }
    public List<Wizard> retrieveWizards(){
        return repository.findAll();
    }
    public Wizard creatWizard(Wizard wizard){
        return repository.insert(wizard);
    }
    public Wizard UpdateWizard(Wizard wizard){
        return repository.save(wizard);
    }
    public boolean DeleteWizard(Wizard wizard){
        repository.delete(wizard);
        return true;
    }
    public Wizard getID(String id){
        return repository.getWizardById(id);
    }
}
