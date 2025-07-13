package org.example.tennis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchPageDto {
    private Map<String, String> matchAttributes;
    private int currentPage;
    private int totalPages;
}
