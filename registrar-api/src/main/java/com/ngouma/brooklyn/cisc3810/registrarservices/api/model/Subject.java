package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subject {
    private Long id;
    private String name;
    @Size(min=4, max=4)
    private String nameShort;

    public Subject(){}

    public Subject(Long id, String name, String nameShort) {
        this.id = id;
        this.name = name;
        this.nameShort = nameShort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name : this.name;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort != null ? nameShort : this.nameShort;
    }
}
