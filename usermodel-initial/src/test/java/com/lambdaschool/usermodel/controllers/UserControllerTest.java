package com.lambdaschool.usermodel.controllers;

import com.lambdaschool.usermodel.UserModelApplicationTest;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.repository.RoleRepository;
import com.lambdaschool.usermodel.repository.UserRepository;
import com.lambdaschool.usermodel.services.RoleService;
import com.lambdaschool.usermodel.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = UserController.class)
@SpringBootTest(classes = UserModelApplicationTest.class,
        properties = {"command.line.runner.enabled=false"})
public class UserControllerTest
{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @MockBean
    private UserRepository userrepos;

    @MockBean
    private RoleRepository rolerepos;

    private List<User> userList = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers()
    {
    }

    @Test
    public void getUserById()
    {
    }

    @Test
    public void getUserByName()
    {
    }

    @Test
    public void getUserLikeName()
    {
    }

    @Test
    public void addNewUser()
    {
    }

    @Test
    public void updateFullUser()
    {
    }

    @Test
    public void updateUser()
    {
    }

    @Test
    public void deleteUserById()
    {
    }
}