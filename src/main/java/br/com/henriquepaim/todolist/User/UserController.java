package br.com.henriquepaim.todolist.User;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
public class UserController {

    @PostMapping("/")
    public void create(@RequestBody UserModel userModel) {

    }
}
