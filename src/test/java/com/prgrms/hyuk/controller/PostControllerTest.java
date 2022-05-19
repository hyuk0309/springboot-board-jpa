package com.prgrms.hyuk.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.UserDto;
import com.prgrms.hyuk.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("게시글 생성")
    void testSave() throws Exception {
        //given
        var postCreateRequest = new PostCreateRequest(
            "this is title ...",
            "content",
            new UserDto(
                "pang",
                26,
                "soccer"
            )
        );

        given(postService.create(any(PostCreateRequest.class))).willReturn(1L);

        //when
        //then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateRequest)))
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.data").value("1"))
            .andExpect(jsonPath("$.serverDatetime").exists());
    }
}