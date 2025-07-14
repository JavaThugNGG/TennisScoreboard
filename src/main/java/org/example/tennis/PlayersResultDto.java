package org.example.tennis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayersResultDto {
    private final PlayerResult firstPlayerResult;
    private final PlayerResult secondPlayerResult;
}
