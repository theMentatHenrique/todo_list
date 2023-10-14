package br.com.henriquepaim.todolist.User;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.catalina.User;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        UserModel byName = this.userRepository.findByName(userModel.getName());
        if (byName != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("usuário já existe");
        }
        userModel.setPassword(BCrypt.withDefaults().
                hashToString(12, userModel.getPassword().toCharArray()));

        UserModel userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
}
