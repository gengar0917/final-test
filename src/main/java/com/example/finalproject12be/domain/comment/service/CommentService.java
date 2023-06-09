package com.example.finalproject12be.domain.comment.service;

import com.example.finalproject12be.common.ResponseMsgDto;
import com.example.finalproject12be.domain.comment.dto.CommentRequestDto;
import com.example.finalproject12be.domain.comment.dto.CommentResponseDto;
import com.example.finalproject12be.domain.comment.entity.Comment;
import com.example.finalproject12be.domain.comment.repository.CommentRepository;
import com.example.finalproject12be.domain.member.entity.Member;
import com.example.finalproject12be.domain.store.entity.Store;
import com.example.finalproject12be.domain.store.repository.StoreRepository;
import com.example.finalproject12be.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final StoreRepository storeRepository;

    // 댓글 생성 responseEntity
//    @Transactional
//    public ResponseEntity<CommentResponseDto> createComment(
//            CommentRequestDto commentRequestDto,
//            Member member,
//            Long storeId) {
//        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));
//
//        Comment comment = new Comment(commentRequestDto, store, member);
//        commentRepository.save(comment);
//
//        return ResponseEntity.ok(new CommentResponseDto(comment));
//    }

    // 댓글 생성 api명세서에 맞춰서 코드 내려주기
    @Transactional
    public ResponseMsgDto<CommentResponseDto> createComment(CommentRequestDto commentRequestDto,
                                                            UserDetailsImpl userDetails,
                                                            Long storeId) {
        // 게시글 검증
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        // 댓글 생성
        Comment comment = new Comment(commentRequestDto, store, userDetails.getMember());
        commentRepository.save(comment);

        // 응답 생성
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return ResponseMsgDto.setSuccess(HttpStatus.CREATED.value(), "댓글이 등록되었습니다.", commentResponseDto);
    }

    // 댓글 조회
    public ResponseEntity<List<CommentResponseDto>> getComments(Long storeId, UserDetailsImpl userDetails) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        List<Comment> comments = commentRepository.findAllByStoreIdOrderByCreatedAtAsc(storeId);

        List<CommentResponseDto> responseDtos = new ArrayList<>();
        for (Comment comment : comments) {
            boolean isCurrentUserComment = comment.getMember().getId().equals(userDetails.getMember().getId());

            CommentResponseDto responseDto = new CommentResponseDto(comment, isCurrentUserComment);
            responseDtos.add(responseDto);
        }

        return ResponseEntity.ok(responseDtos);
    }
    // 댓글 수정
    @Transactional
    public ResponseEntity<CommentResponseDto> updateComment(
            Long commentId,
            CommentRequestDto commentRequestDto,
            Member member) {
        Comment comment = commentRepository.findByIdAndMemberId(commentId, member.getId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContents(commentRequestDto.getContents());
        Comment updatedComment = commentRepository.save(comment);

        return ResponseEntity.ok(new CommentResponseDto(updatedComment));
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<Void> deleteComment(
            Long commentId,
            Member member) {
        Comment comment = commentRepository.findByIdAndMemberId(commentId, member.getId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment);

        return ResponseEntity.ok().build();
    }
}