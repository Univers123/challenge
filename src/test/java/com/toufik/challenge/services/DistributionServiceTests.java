package com.toufik.challenge.services;

import com.toufik.challenge.entities.Company;
import com.toufik.challenge.entities.Distribution;
import com.toufik.challenge.entities.User;
import com.toufik.challenge.repository.CompanyRepository;
import com.toufik.challenge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class DistributionServiceTests {
    @InjectMocks
    private DistributionService distributionService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    // Test adding a distribution to a user.
    void distributionGiftAndMeal() {
        // Given
        // Instantiating a company and a user with multiple distributions.
        User user = User.builder()
                .userId(1).build();

        Company company = Company.builder()
                .companyId(1)
                .balance(50.00)
                .users(Collections.singletonList(user))
                .build();

        List<Distribution> wedooGiftList = new ArrayList<>();
        Distribution gift = new Distribution("gift", 10.00, user);
        Distribution meal = new Distribution("meal", 50.00, user);

        gift.setDistributionId(1);
        meal.setDistributionId(2);
        wedooGiftList.add(gift);
        wedooGiftList.add(meal);
        user.setWedooGiftList(wedooGiftList);

        // The expected value after calling the distributionService.distributionGiftAndMeal.
        Distribution expect = new Distribution("meal", 10.00, user);

        // When there is a call to the companyRepository with the findById method, returns the company.
        Mockito.when(companyRepository.findById(1)).thenReturn(Optional.of(company));

        // When
        // Calling the distributionService with the method distributionGiftAndMeal to retrieve the user with the addition of a distribution.
        User actuelUser = distributionService.distributionGiftAndMeal(company, user, 10.0, "meal");

        // Then
        // Verification of the results against what is expected and that the distribution has been added
        assertEquals(expect, actuelUser.getWedooGiftList().get(2));
        // Verification that the scale has been updated.
        assertEquals(40, company.getBalance());
    }

    @Test
    // Test the case where the scale does not allow the allocation of a gift or a meal.
    void dontDistributionGiftAndMeal() {
        // Given
        // Instantiating a company and a user.
        User user = User.builder().userId(1).build();

        Company company = Company.builder()
                .companyId(1)
                .balance(9.00)
                .users(Collections.singletonList(user))
                .build();
        // When there is a call to the companyRepository with the findById method, returns the company.
        Mockito.when(companyRepository.findById(1)).thenReturn(Optional.of(company));

        // When
        // Calling the distributionService with the method distributionGiftAndMeal.
        try {
            distributionService.distributionGiftAndMeal(company, user, 10.0, "meal");
        } catch (Exception e) {
        // Then
        // There is an exception because the amount is greater than the balance.
            fail("The amount of the balance is less than the amount entered");
        }
    }

    @Test
        // Test the case where the user does not exist
    void userDoesNotExist() {
        // Given
        // Instantiating a company and a multiple users.
        User firstUser = User.builder().userId(1).build();
        User secondUser = User.builder().userId(2).build();

        Company company = Company.builder()
                .companyId(1)
                .balance(50.00)
                .users(Collections.singletonList(firstUser))
                .build();
        // When there is a call to the companyRepository with the findById method, returns the company.
        Mockito.when(companyRepository.findById(1)).thenReturn(Optional.of(company));

        // When
        // Calling the distributionService with the method distributionGiftAndMeal.
        try {
            distributionService.distributionGiftAndMeal(company, secondUser, 10.0, "meal");
        } catch (Exception e) {
        // Then
            // There is an exception because the user does not exist.
            fail("This user does not exist");
        }
    }
}
