package finalmission.controller;

import finalmission.dto.layer.MemberCreationContent;
import finalmission.dto.request.SignupRequest;
import finalmission.servcie.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
        MemberCreationContent creationContent = new MemberCreationContent(request);
        memberService.addMember(creationContent);
        return ResponseEntity.noContent().build();
    }
}
