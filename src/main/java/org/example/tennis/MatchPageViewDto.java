package org.example.tennis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MatchPageViewDto {
    private Map<String, String> matchAttributes;
    private int currentPage;
    private int totalPages;
}
