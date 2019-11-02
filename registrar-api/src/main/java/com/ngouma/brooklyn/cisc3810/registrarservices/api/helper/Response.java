package com.ngouma.brooklyn.cisc3810.registrarservices.api.helper;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int numItems;
    private Object item;
    private List<?> items;

    public Response(Object item) {
        this.item = item;
    }

    public Response(List<?> items) {
        this.numItems = items.size();
        this.items = items;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }
}
