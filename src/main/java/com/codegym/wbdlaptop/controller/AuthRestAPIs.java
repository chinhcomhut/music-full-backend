package com.codegym.wbdlaptop.controller;

import com.codegym.wbdlaptop.message.request.LoginForm;
import com.codegym.wbdlaptop.message.request.PasswordForm;
import com.codegym.wbdlaptop.message.request.SignUpForm;
import com.codegym.wbdlaptop.message.request.UserForm;
import com.codegym.wbdlaptop.message.response.JwtResponse;
import com.codegym.wbdlaptop.message.response.ResponseMessage;
import com.codegym.wbdlaptop.model.*;
import com.codegym.wbdlaptop.repository.IUserRepository;
import com.codegym.wbdlaptop.security.jwt.JwtProvider;
import com.codegym.wbdlaptop.security.service.UserPrinciple;
import com.codegym.wbdlaptop.service.IRoleService;
import com.codegym.wbdlaptop.service.ISingerService;
import com.codegym.wbdlaptop.service.ISongService;
import com.codegym.wbdlaptop.service.IUserService;
import com.codegym.wbdlaptop.service.Impl.SingerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    ISingerService singerService;
    @Autowired
    ISongService songService;
    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),
                userDetails.getId() , userDetails.getName(), userDetails.getEmail(), userDetails.getAvatar() ,
                userDetails.getAuthorities()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }

    @PutMapping("/update-profile/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserForm userForm, @PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user == null) {
            return new ResponseEntity<>("Can't Find User By Id" + id, HttpStatus.BAD_REQUEST);
        }

        try {
            user.get().setName(userForm.getName());

            userRepository.save(user.get());

            return new ResponseEntity<>(new ResponseMessage("Update successful"), HttpStatus.OK);
        } catch (Exception e ) {
            throw new RuntimeException("Fail!");
        }
    }


    @PutMapping("/update-password/{id}")
    public ResponseEntity<?>updatePassword(@Valid @RequestBody PasswordForm passForm, @PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user == null ){
            return new ResponseEntity<>(new ResponseMessage("Not found user"),HttpStatus.NOT_FOUND);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(passForm.getUsername(), passForm.getCurrentPassword()));

            user.get().setPassword(passwordEncoder.encode(passForm.getNewPassword()));

            userRepository.save(user.get());

            return new ResponseEntity<>(new ResponseMessage("Change password successful"),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Fail!");
        }
    }
    @GetMapping("/listSingerByUser")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> getListSingerUserById() {
        Iterable<Singer> singers = this.singerService.findAllByUserId(getCurrentUser().getId());
        if (singers == null) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage("List null", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Success", singers), HttpStatus.OK);
    }
    @GetMapping("/listSongByUser")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseMessage> getListSongUserById() {
        List<Song> songs = this.songService.findAllByUserId(getCurrentUser().getId());
        if (songs == null) {
            return new ResponseEntity<ResponseMessage>(new ResponseMessage("List null", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Success", songs), HttpStatus.OK);
    }


}
