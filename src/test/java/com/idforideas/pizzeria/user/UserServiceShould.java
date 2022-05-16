package com.idforideas.pizzeria.user;

import static com.idforideas.pizzeria.user.UserMother.getNewUser001;
import static com.idforideas.pizzeria.user.UserMother.getNewUser002;
import static com.idforideas.pizzeria.user.UserMother.getUser001;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.idforideas.pizzeria.exception.BadRequestException;
import com.idforideas.pizzeria.exception.NotFoundException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class UserServiceShould {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    UserServiceJpa userService;

    @Test
    void throw_exception_when_user_by_id_not_exist() {
         // Given
         final Long mockedId = 1L;

         // When
 
         // Then
         assertThrows(NotFoundException.class, ()-> userService.get(mockedId));
    }

    @Test
    void throw_exception_when_user_by_email_not_exist() {
         // Given
         final String mockedEmail = "no-exist@mail.com";

         // When
 
         // Then
         assertThrows(NotFoundException.class, ()-> userService.get(mockedEmail));
    }
    
    @Test
    void throw_exception_when_email_user_is_already_registered() {
        // Given
        User mockedNewUser001 = getNewUser001();
        User mockedUser = getUser001();

        // When
        when(userRepo.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(mockedUser));
        
        // Then
        assertThrows(BadRequestException.class, ()-> userService.create(mockedNewUser001));    
    }

    @Test
    void throw_exception_when_user_is_updated_with_email_registered() {
        // Given
        User mockedEditUser = getNewUser002();
        mockedEditUser.setEmail("test@mail.com");
        User mockedUsery001 = getUser001();

        // When
        when(userRepo.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(mockedUsery001));
        
        // Then
        assertThrows(BadRequestException.class, ()-> userService.update(mockedUsery001, mockedEditUser));    
    }
}