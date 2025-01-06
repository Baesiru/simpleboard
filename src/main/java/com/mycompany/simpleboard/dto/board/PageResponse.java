package com.mycompany.simpleboard.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {
    private Long id;
    private String title;
    private String username;
}
