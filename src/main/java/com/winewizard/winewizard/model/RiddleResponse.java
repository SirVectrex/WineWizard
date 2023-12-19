package com.winewizard.winewizard.model;

import lombok.Getter;

@Getter
public class RiddleResponse {
    public RiddleResponse(String riddle, String answer) {
        this.riddle = riddle;
        this.answer = answer;
    }

    private String riddle;

    public void setRiddle(String riddle) {
        this.riddle = riddle;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRiddle() {
        return riddle;
    }

    public String getAnswer() {
        return answer;
    }

    private String answer;

}
