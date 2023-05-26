package com.example.finalproject12be.domain.comment.dto;



import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    @NotBlank
    private String contents;
}