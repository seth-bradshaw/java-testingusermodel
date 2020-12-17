package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplicationTest;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.RoleRepository;
import com.lambdaschool.usermodel.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplicationTest.class,
properties = {"command.line.runner.enabled=false"})
//To customize order the tests run
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//put a_methodname, b_methodname to choose which tests go first
public class UserServiceImplUnitTestNoDB
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
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1.setRoleid(1);
        r2.setRoleid(2);
        r3.setRoleid(3);

        // admin, data, user
        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local");
        u1.setUserid(4);
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r2));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r3));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@mymail.local"));
        userList.add(u1);

        // data, user
        User u2 = new User("cinnamon",
                "1234567",
                "cinnamon@lambdaschool.local");
        u2.setUserid(5);
        u2.getRoles()
                .add(new UserRoles(u2,
                        r2));
        u2.getRoles()
                .add(new UserRoles(u2,
                        r3));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));
        userList.add(u2);

        // user
        User u3 = new User("barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");
        u3.setUserid(6);
        u3.getRoles()
                .add(new UserRoles(u3,
                        r2));
        u3.getUseremails()
                .add(new Useremail(u3,
                        "barnbarn@email.local"));
        userList.add(u3);

        User u4 = new User("puttat",
                "password",
                "puttat@school.lambda");
        u4.setUserid(7);
        u4.getRoles()
                .add(new UserRoles(u4,
                        r2));
        userList.add(u4);

        User u5 = new User("misskitty",
                "password",
                "misskitty@school.lambda");
        u5.setUserid(8);
        u5.getRoles()
                .add(new UserRoles(u5,
                        r2));
        userList.add(u5);

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findUserById()
    {
        Mockito.when(userrepos.findById(8L))
                .thenReturn(Optional.of(userList.get(4)));

        assertEquals("misskitty", userService.findUserById(8).getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findUserByIdNotFound()
    {
        Mockito.when(userrepos.findById(8L))
                .thenReturn(Optional.empty());

        assertEquals("misskitty", userService.findUserById(8).getUsername());
    }

    @Test
    public void findByNameContaining()
    {
        Mockito.when(userrepos.findByUsernameContainingIgnoreCase("barnbarn"))
                .thenReturn(userList);

        assertEquals(5, userService.findByNameContaining("barnbarn").size());
    }

    @Test
    public void findAll()
    {
        Mockito.when(userrepos.findAll())
                .thenReturn(userList);

        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void delete()
    {
        Mockito.when(userrepos.findById(4L))
                .thenReturn(Optional.of(userList.get(0)));

        Mockito.doNothing()
                .when(userrepos)
                .deleteById(4L);

        userService.delete(4L);
        assertEquals(5, userList.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteNotFound()
    {
        Mockito.when(userrepos.findById(4L))
                .thenReturn(Optional.empty());

        Mockito.doNothing()
                .when(userrepos)
                .deleteById(4L);

        userService.delete(4L);
        assertEquals(5, userList.size());
    }

    @Test
    public void findByName()
    {
        Mockito.when(userrepos.findByUsername("misskitty"))
                .thenReturn(userList.get(4));

        assertEquals("misskitty", userService.findByName("misskitty").getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByNameNotFound()
    {
        Mockito.when(userrepos.findByUsername("misskitty"))
                .thenReturn(null);

        assertEquals("misskitty", userService.findByName("misskitty").getUsername());
    }

    @Test
    public void save()
    {
        Role r1 = new Role("admin");

        r1.setRoleid(1);

        String u1Username = "admin";
        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local");
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.setUserid(0);

        Mockito.when(userrepos.save(any(User.class)))
                .thenReturn(u1);

        Mockito.when(rolerepos.findById(1L))
                .thenReturn(Optional.of(r1));

        User newUser = userService.save(u1);

        assertNotNull(newUser);
        assertEquals(u1Username, newUser.getUsername());
    }

    @Test
    public void saveput()
    {
        Role r1 = new Role("admin");

        r1.setRoleid(1);

        String u1Username = "admin";
        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local");
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.setUserid(4);

        Mockito.when(userrepos.findById(4L))
                .thenReturn(Optional.of(u1));

        Mockito.when(userrepos.save(any(User.class)))
                .thenReturn(u1);

        Mockito.when(rolerepos.findById(1L))
                .thenReturn(Optional.of(r1));

        User newUser = userService.save(u1);

        assertNotNull(newUser);
        assertEquals(u1Username, newUser.getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveputNotFound()
    {
        Role r1 = new Role("admin");

        r1.setRoleid(1);

        String u1Username = "admin";
        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local");
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.setUserid(4);

        Mockito.when(userrepos.findById(4L))
                .thenReturn(Optional.empty());

        Mockito.when(userrepos.save(any(User.class)))
                .thenReturn(u1);

        Mockito.when(rolerepos.findById(1L))
                .thenReturn(Optional.empty());

        User newUser = userService.save(u1);

        assertNotNull(newUser);
        assertEquals(u1Username, newUser.getUsername());
    }

    @Test
    public void update()
    {
        String newusername = "newusername";
        String newpassword = "newpassword";
        User u1 = new User();
        u1.setUserid(5);
        u1.setPassword(newpassword);
        u1.setUsername(newusername);

        Mockito.when(userrepos.save(any(User.class)))
                .thenReturn(u1);

        Mockito.when(userrepos.findById(5L))
                .thenReturn(Optional.of(u1));

        User newUser = userService.update(u1, 5);

        assertNotNull(newUser);
//        assertEquals(newpassword, newUser.getPassword());
    }

    @Test
    public void deleteAll()
    {
    }
}