package com.codegym.wbdlaptop.service.Impl;

import com.codegym.wbdlaptop.model.Comment;
import com.codegym.wbdlaptop.repository.ICommentRepository;
import com.codegym.wbdlaptop.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;
    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment line) {
        return commentRepository.save(line);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Iterable<Comment> findCommentsBySongId(Long song_id) {
        return commentRepository.findCommentsBySongId(song_id);
    }
}
