package org.example.ch01;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers() : 멤버 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception {
        // given
        final String url = "/members";
        Member member = memberRepository.save(new Member(1L, "홍길동"));
        // when
        final ResultActions result = mvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        // then

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(member.getId()))
                .andExpect(jsonPath("$[0].name").value(member.getName()));
    }

}