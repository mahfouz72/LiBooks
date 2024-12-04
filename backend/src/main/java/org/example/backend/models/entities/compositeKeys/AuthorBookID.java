package org.example.backend.models.entities.compositeKeys;

import lombok.Data;
import java.io.Serializable;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookID implements Serializable {
    private Integer authorId;
    private Integer bookId;
}
