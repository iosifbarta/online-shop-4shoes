package org.fastrackit.onlineshop4shoes;


import org.fastrackit.onlineshop4shoes.domain.User;
import org.fastrackit.onlineshop4shoes.domain.UserRole;
import org.fastrackit.onlineshop4shoes.service.UserService;
import org.fastrackit.onlineshop4shoes.steps.UserTestSteps;
import org.fastrackit.onlineshop4shoes.transfer.user.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class UserServiceIntegrationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTestSteps userTestSteps;

    @Test
    public void createUser_whenValidRequest_thenReturnCreatedUser(){
        userTestSteps.createUser();
    }
//    @Test
//    public void createUser_whenIsNonValidRequest_thenThrowException(){
//        CreateUserRequest request = new CreateUserRequest();
//        request.setFirstName("Test First Name");
//        request.setRole(UserRole.ADMIN);
//
//        try {
//            User user = userService.createUser(request);
//            assertThat(user, notNullValue());
//            assertThat(user.getId(), greaterThan(0L));
//            assertThat(user.getFirstName(), is(request.getFirstName()));
//            assertThat(user.getRole(), is(request.getRole()));
//
//        } catch (Exception e) {
//            assertThat("Unexpected exception throw ", e instanceof ConstraintViolationException);
//        }
//    }

    @Test
    public void getUser_whenExistingUser_thenReturnUser(){

        User user = createUser();
        User userResponse = userService.getUser(user.getId());

        assertThat(userResponse, notNullValue());
        assertThat(userResponse.getId(), is(user.getId()));
        assertThat(userResponse.getFirstName(), is(user.getFirstName()));
        assertThat(userResponse.getRole(), is(user.getRole()));
        assertThat(userResponse.getLastName(), is(user.getLastName()));

    }
    private User createUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setRole(UserRole.CUSTOMER);
        request.setFirstName("Test FirstName");
        request.setLastName("Test LastName");

        User user = userService.createUser(request);

        assertThat(user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getFirstName(), is(request.getFirstName()));
        assertThat(user.getRole(), is(request.getRole().name()));
        assertThat(user.getLastName(), is(request.getLastName()));

        return user;
    }

}
