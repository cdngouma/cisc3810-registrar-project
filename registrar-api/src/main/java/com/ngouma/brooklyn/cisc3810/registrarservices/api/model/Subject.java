package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subject {
    private Integer id;
    private String name;
    private String shortName;

    public Subject(){}

    public Subject(Integer id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name : this.name;
    }

    public String getNameShort() {
        return shortName;
    }

    public void setNameShort(String nameShort) {
        this.shortName = nameShort != null ? nameShort : this.shortName;
    }
}
