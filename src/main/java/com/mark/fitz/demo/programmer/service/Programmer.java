package com.mark.fitz.demo.programmer.service;

import com.mark.fitz.demo.programmer.state.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Programmer {

    private Long id;

    private String name;

    private boolean tired;

    private boolean stressed;

    private boolean competent;

    private boolean nice;

    private State state;

    public boolean canWriteGoodCode() {
        return !this.tired && !this.stressed || this.isCompetent();
    }

}
