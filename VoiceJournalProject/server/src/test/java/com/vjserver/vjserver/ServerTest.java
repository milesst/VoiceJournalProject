package com.vjserver.vjserver;

import com.vjserver.vjserver.controller.UserController;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserControllerTest 
{
    @InjectMocks
    UserController userController;
     
    @Test
    public void testAddUser() 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        UserResource user = new User(1, "login", "password", "Иванов", "Иван", "Иванович", "teacher");
        ResponseEntity<Object> responseEntity = userController.post(user);
         
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }
}