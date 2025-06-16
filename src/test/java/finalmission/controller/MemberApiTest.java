package finalmission.controller;

import finalmission.domain.Member;
import finalmission.domain.Role;
import finalmission.dto.request.SignupRequest;
import finalmission.repository.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        memberRepository.deleteAll();
    }

    @Nested
    @DisplayName("회원가입 할 수 있다.")
    public class canSignup {

        @DisplayName("정상적으로 회원가입 할 수 있다.")
        @Test
        void canSignup() {
            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .body(new SignupRequest("test@test.com", "qwer1234!", "kim"))
                    .when().log().all()
                    .post("/member")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @DisplayName("이미 존재하는 이메일인 경우 회원가입이 불가능하다.")
        @Test
        void cannotSignupByDuplicatedEmail() {
            // given
            String duplicatedEmail = "test@test.com";
            memberRepository.save(new Member(duplicatedEmail, "qwer1234!", "kim", Role.GENERAL));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .body(new SignupRequest(duplicatedEmail, "qwer1234!", "kim"))
                    .when().log().all()
                    .post("/member")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
