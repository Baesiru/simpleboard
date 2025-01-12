package com.mycompany.simpleboard.service;

import com.mycompany.simpleboard.config.exception.board.BoardErrorCode;
import com.mycompany.simpleboard.config.exception.board.BoardNotFoundException;
import com.mycompany.simpleboard.config.exception.likes.LikesAlreadyFoundException;
import com.mycompany.simpleboard.config.exception.likes.LikesErrorCode;
import com.mycompany.simpleboard.config.exception.likes.LikesNotFoundException;
import com.mycompany.simpleboard.config.exception.user.UserErrorCode;
import com.mycompany.simpleboard.config.exception.user.UsernameNotFoundException;
import com.mycompany.simpleboard.entity.Board;
import com.mycompany.simpleboard.entity.Likes;
import com.mycompany.simpleboard.entity.User;
import com.mycompany.simpleboard.repository.BoardRepository;
import com.mycompany.simpleboard.repository.LikesRepository;
import com.mycompany.simpleboard.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikesService {
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void like(Long boardId, HttpSession httpSession) {
        String username = getUsername(httpSession);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(UserErrorCode.USER_NOT_FOUND));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND));
        if (likesRepository.existsByUserAndBoard(user, board))
            throw new LikesAlreadyFoundException(LikesErrorCode.LIKE_ALREADY_FOUND);
        Likes likes = new Likes();
        likes.setUser(user);
        likes.setBoard(board);
        likesRepository.save(likes);
    }

    @Transactional
    public void unlike(Long boardId, HttpSession httpSession) {
        String username = getUsername(httpSession);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(UserErrorCode.USER_NOT_FOUND));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND));
        Likes likes = likesRepository.findByUserAndBoard(user, board)
                .orElseThrow(() -> new LikesNotFoundException(LikesErrorCode.LIKE_NOT_FOUND));

        likesRepository.delete(likes);
    }

    // 아이디 추출 로직 분리
    public String getUsername(HttpSession httpSession) {
        return httpSession.getAttribute("username").toString();
    }

}
