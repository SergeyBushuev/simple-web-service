package org.example.controller.model;

public record Paging(
        int pageNumber,
        int pageSize,
        boolean hasNext,
        boolean hasPrevious) {
}
