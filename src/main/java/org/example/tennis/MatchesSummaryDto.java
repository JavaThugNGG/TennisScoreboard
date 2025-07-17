package org.example.tennis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MatchesSummaryDto {
    List<MatchEntity> matches;
    int totalCount;
}
