package com.toufik.challenge.services;

import com.toufik.challenge.entities.Distribution;
import com.toufik.challenge.entities.User;
import com.toufik.challenge.repository.UserRepository;
import com.toufik.challenge.utils.DistributionEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void userBalance() {
        // Given
        // Instantiating a user with multiple distributions
        User user = User.builder().userId(1).build();
        Distribution firstGift = Distribution.builder().amount(45.5).type(DistributionEnum.GIFT).build();
        Distribution secondGift = Distribution.builder().amount(75.5).type(DistributionEnum.GIFT).build();
        Distribution firstMeal = Distribution.builder().amount(12.5).type(DistributionEnum.MEAL).build();
        Distribution secondMeal = Distribution.builder().amount(22.5).type(DistributionEnum.MEAL).build();

        user.setWedooGiftList(Arrays.asList(firstGift, secondGift, firstMeal, secondMeal));

        // When there is a call to the userRepository with the findById method returns the user.
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When
        // Calling the userService with the userBalance method to retrieve the sum by type.
        Double giftSum = userService.userBalance(user, "gift");
        Double mealSum = userService.userBalance(user, "meal");

        // Then
        // Verification of results against what is expected.
        assertEquals(121, giftSum);
        assertEquals(35, mealSum);
    }
}
