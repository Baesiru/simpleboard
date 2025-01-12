package com.mycompany.simpleboard.service;

import com.mycompany.simpleboard.config.exception.board.BoardErrorCode;
import com.mycompany.simpleboard.config.exception.board.BoardNotFoundException;
import com.mycompany.simpleboard.config.exception.user.LoginFailException;
import com.mycompany.simpleboard.config.exception.user.UserErrorCode;
import com.mycompany.simpleboard.dto.board.BoardRequest;
import com.mycompany.simpleboard.dto.board.BoardResponse;
import com.mycompany.simpleboard.dto.board.PageResponse;
import com.mycompany.simpleboard.entity.Board;
import com.mycompany.simpleboard.entity.Image;
import com.mycompany.simpleboard.entity.enums.BoardStatus;
import com.mycompany.simpleboard.repository.BoardRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void create(BoardRequest boardRequest, List<MultipartFile> images, HttpSession httpSession) {
        Board board = modelMapper.map(boardRequest, Board.class);
        board.setCreatedAt(LocalDateTime.now());
        board.setStatus(BoardStatus.CREATED);
        board.setUsername(getUsername(httpSession));
        Board saved = boardRepository.save(board);
        if (images != null && images.size() > 0) {
            List<Image> newImages = imageService.storeFiles(images, saved.getId());
        }
    }

    @Transactional
    public void modify(Long id, BoardRequest boardRequest, List<MultipartFile> images, HttpSession httpSession) {
        Board board = boardRepository.findById(id).orElse(null);
        if (board == null || board.getStatus().equals(BoardStatus.DELETED)) {
            throw new BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        checkUsername(httpSession, board);
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setStatus(BoardStatus.MODIFIED);
        board.setModifiedAt(LocalDateTime.now());
        boardRepository.save(board);
    }

    @Transactional
    public void delete(Long id, HttpSession httpSession) {
        Board board = boardRepository.findById(id).orElse(null);
        if (board == null || board.getStatus().equals(BoardStatus.DELETED)) {
            throw new BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        checkUsername(httpSession, board);
        board.setStatus(BoardStatus.DELETED);
        board.setDeletedAt(LocalDateTime.now());
        boardRepository.save(board);
    }

    @Transactional
    public BoardResponse read(Long id) {
        Board board = boardRepository.findById(id).orElse(null);
        BoardResponse boardResponse = modelMapper.map(board, BoardResponse.class);
        if (board == null || board.getStatus().equals(BoardStatus.DELETED)) {
            throw new BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        List<Image> images = imageService.findImagesByBoardId(id);
        if (images != null && images.size() > 0) {
            boardResponse.setImages(images);
        }
        board.setViewCount(board.getViewCount() + 1);
        return boardResponse;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> readPage(Integer page) {
        Page<Board> boards = boardRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
        Map<String, Object> map = new HashMap<>();
        map.put("boards", boards.getContent().stream().map(board -> modelMapper.map(board, BoardResponse.class)).toList());
        map.put("page", boards.getTotalPages());
        return map;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findLikeTitle(String title, Integer page) {
        Page<Board> boards = boardRepository.findByTitleContainsAndStatusNot(title, BoardStatus.DELETED, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
        Map<String, Object> map = new HashMap<>();
        map.put("boards", boards.getContent().stream().map(board -> modelMapper.map(board, BoardResponse.class)).toList());
        map.put("page", boards.getTotalPages());
        return map;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findLikeContent(String content, Integer page) {
        Page<Board> boards = boardRepository.findByContentContainsAndStatusNot(content, BoardStatus.DELETED, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
        Map<String, Object> map = new HashMap<>();
        map.put("boards", boards.getContent().stream().map(board -> modelMapper.map(board, BoardResponse.class)).toList());
        map.put("page", boards.getTotalPages());
        return map;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findLikeUsername(String username, Integer page) {
        Page<Board> boards = boardRepository.findByUsernameContainsAndStatusNot(username, BoardStatus.DELETED, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
        Map<String, Object> map = new HashMap<>();
        map.put("boards", boards.getContent().stream().map(board -> modelMapper.map(board, BoardResponse.class)).toList());
        map.put("page", boards.getTotalPages());
        return map;
    }

    // 아이디 추출 로직 분리
    public String getUsername(HttpSession httpSession) {
        return httpSession.getAttribute("username").toString();
    }

    // 아이디 검증 로직 분리
    public void checkUsername(HttpSession httpSession, Board board) {
        String username = getUsername(httpSession);
        if (!board.getUsername().equals(username)) {
            throw new LoginFailException(UserErrorCode.USER_NOT_MATCHED);
        }
    }



}
