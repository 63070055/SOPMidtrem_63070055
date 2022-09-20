package com.example.week6.view;

import com.example.week6.pojo.Wizard;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Route("mainPage.it")
public class MainWizardView extends VerticalLayout {
    private int number;
    private String id;

    Wizard wizard;
    public MainWizardView(){
        TextField name = new TextField();
        RadioButtonGroup<String> sex = new RadioButtonGroup<>("Gender : ");
        sex.setItems("Male","Female");
        ComboBox<String> Position = new ComboBox();
        Position.setPlaceholder("Position");
        Position.setItems("teacher","student");
        TextField Dollars = new TextField();
        Dollars.setPrefixComponent(new Span("$"));
        ComboBox<String> School = new ComboBox();
        School.setPlaceholder("School");
        School.setItems("Hogwarts","Durmstrang","Beauxbatons");
        ComboBox<String> House = new ComboBox();
        House.setPlaceholder("House");
        House.setItems("Slytherin","Gryffindor","Ravenclaw","Hufflepuff");
        Button bt1 = new Button("<<");
        Button Create = new Button("Create");
        Button Update = new Button("Update");
        Button Delete = new Button("Delete");
        Button bt5 = new Button(">>");
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(bt1,Create,Update,Delete,bt5);
        add(name,sex,Position,Dollars,School,House,hl);

        bt1.addClickListener(buttonClickEvent -> {

            List<Wizard> out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
            ObjectMapper mapper = new ObjectMapper();

            if(this.number > 0){
                this.number--;
            }else{
                this.number = out.size()-1;
            }

            Wizard wizard = mapper.convertValue(out.get(this.number), Wizard.class);
            this.id = wizard.get_id();
            name.setValue(wizard.getName());
            if(wizard.getSex().equals("m")){
                sex.setValue("Male");
            }else{
                sex.setValue("Female");
            }
            House.setValue(wizard.getHouse());
            Dollars.setValue(String.valueOf(wizard.getMoney()));
            School.setValue(wizard.getSchool());
            Position.setValue(wizard.getPosition());
        });

        Create.addClickListener(buttonClickEvent -> {
            String sex1 = sex.getValue();
            String name1 = name.getValue();
            String school1 = School.getValue();
            int Dollars1 = Integer.parseInt(Dollars.getValue());
            String House1 = House.getValue();
            String Position1 = Position.getValue();
            String Truesex;
            if(sex1.equals("Male")) {
                Truesex = "m";
            }else{
                Truesex = "f";
            }
            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .body(Mono.just(new Wizard(null, Truesex, name1, school1, House1, Dollars1, Position1)),Wizard.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("Show Success");
            Notification notification = Notification.show("Financial report generated");
        });

        Update.addClickListener(buttonClickEvent -> {
            String sex1 = sex.getValue();
            String name1 = name.getValue();
            String school1 = School.getValue();
            int Dollars1 = Integer.parseInt(Dollars.getValue());
            String House1 = House.getValue();
            String Position1 = Position.getValue();
            String Truesex = "";
            if (sex1.equals("Male")) {
                Truesex = "m";
            } else {
                Truesex = "f";
            }
            System.out.println(sex1);
            Wizard out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .body(Mono.just(new Wizard(this.id, Truesex, name1, school1, House1, Dollars1, Position1)), Wizard.class)
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();
            System.out.println("Update Success");
            Notification.show("Financial report generated");
        });

        Delete.addClickListener(buttonClickEvent -> {
            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .body(Mono.just(this.id), String.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("Delete Success");
            Notification.show("Financial report generated");
        });

        bt5.addClickListener(buttonClickEvent -> {

            List<Wizard> out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
            ObjectMapper mapper = new ObjectMapper();
            if(this.number >= out.size()-1){
                this.number = -1;
            }
            this.number++;
            Wizard wizard = mapper.convertValue(out.get(this.number), Wizard.class);
            this.id = wizard.get_id();
            name.setValue(wizard.getName());
            if(wizard.getSex().equals("m")){
                sex.setValue("Male");
            }else{
                sex.setValue("Female");
            }
            House.setValue(wizard.getHouse());
            Dollars.setValue(String.valueOf(wizard.getMoney()));
            School.setValue(wizard.getSchool());
            Position.setValue(wizard.getPosition());
        });
    }
}
