package me.kwangmin.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/test")
    // @ResponseBody
//    public ResponseEntity<List<Member>> getAllMembers() {
//
//        return ResponseEntity.ok(testService.getAllMembers());
//    }
//    public List<Member> getAllMembers() {
//        List<Member> members = testService.getAllMembers();
//        return members;
//    }
      public String getTestAPI(){
        return "Hello World";
    }

    @PostMapping("/test")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {

        return ResponseEntity.ok(testService.saveMember(member));
    }
}
