package finalmission.controller;

import finalmission.domain.Member;
import finalmission.domain.Role;
import finalmission.dto.request.LoginRequest;
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
class AuthApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        memberRepository.deleteAll();
    }

    @Nested()
    @DisplayName("로그인 할 수 있다.")
    public class canLogin {

        @DisplayName("정상적으로 로그인 할 수 있다.")
        @Test
        void canLogin() {
            // given
            memberRepository.save(new Member("member@email.com", "qwer1234!", "kim", Role.GENERAL));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .body(new LoginRequest("member@email.com", "qwer1234!"))
                    .when().log().all()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @DisplayName("비밀번호가 맞지 않은 경우 로그인이 불가능하다.")
        @Test
        void cannotLogin() {
            // given
            memberRepository.save(new Member("member@email.com", "asdf1234!", "kim", Role.GENERAL));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .body(new LoginRequest("member@email.com", "qwer1234!"))
                    .when().log().all()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    @DisplayName("로그아웃 할 수 있다.")
    public class canLogout {

        @DisplayName("정상적으로 로그아웃 할 수 있다.")
        @Test
        void canLogout() {
            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .when().log().all()
                    .post("/logout")
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }
    }
}
