package org.example.backend.models.entities.compositeKeys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BooksBookShelfId implements Serializable {

    private Integer bookShelfId;

    private Integer bookId;
}
