package org.example.tennis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.tennis.entity.MatchEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MatchesSummaryDto {
    List<MatchEntity> matches;
    int totalCount;
}
