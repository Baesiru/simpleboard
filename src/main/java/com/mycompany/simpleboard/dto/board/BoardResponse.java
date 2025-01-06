package com.mycompany.simpleboard.dto.board;

import com.mycompany.simpleboard.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private String username;
    private List<Image> images;
}
