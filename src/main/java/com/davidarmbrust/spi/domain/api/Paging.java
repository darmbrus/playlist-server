package com.davidarmbrust.spi.domain.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides a container for incremental playlist responses
 */
public class Paging<T> {
    private String href;
    private ArrayList<T> items;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
    }

    private ArrayList<T> getItems() {
        return items;
    }

    @SuppressWarnings("unchecked")
    public List getConvertedItems(ObjectMapper mapper, Class clazz) {
        return (List) getItems()
                .stream()
                .map(item -> mapper.convertValue(item, clazz))
                .collect(Collectors.toList());
    }
}
