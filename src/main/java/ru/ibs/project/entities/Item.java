package ru.ibs.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //любые поля, не связанные с полями класса, должны быть проигнорированы.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {  //это сущьность для мапинга из стороннего API
    Integer id;
    String name;
    Area area;
    Salary salary;
    Employer employer;
}