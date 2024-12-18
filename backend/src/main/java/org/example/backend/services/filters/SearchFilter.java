package org.example.backend.services.filters;

import java.util.List;

public interface SearchFilter {
    List<?> applyFilter(String query);
}
