package com.toufik.challenge.services;

import com.toufik.challenge.entities.Company;
import com.toufik.challenge.entities.Distribution;
import com.toufik.challenge.entities.User;
import com.toufik.challenge.repository.CompanyRepository;
import com.toufik.challenge.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DistributionService {

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    public DistributionService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    // Method to assign a GIFt or MEAL for a user
    public User distributionGiftAndMeal(Company company, User user, Double amount, String type) {
        // Recovery of the company if it exists
        Optional<Company> currentCompany = companyRepository.findById(company.getCompanyId());
        // Verification that the company exists
        if (currentCompany.isPresent()) {
            // Verification that the balance allows the allocation of an amount to a user
            if (currentCompany.get().getBalance() >= amount) {
                // Withdraw the donated amount from the balance
                company.setBalance(company.getBalance() - amount);
                // updated the balance in the database.
                companyRepository.save(company);
                // User recovery if it exists
                Optional<User> currentUser = company.getUsers().stream().filter( us -> us.getUserId().equals(user.getUserId())).findFirst();
                // Verification that the user exists
                if (currentUser.isPresent()) {
                    // Creating a distribution
                    Distribution distribution = new Distribution(type, amount, currentUser.get());
                    user.getWedooGiftList().add(distribution);
                    // Saving the user with the new distribution in the database.
                    userRepository.save(user);
                } else {
                    // Display of the info message if the user does not exist.
                    log.info("This user does not exist: ");
                }
            } else {
                // Display of the info message if the balance is insufficient to make a distribution.
                log.info("The amount of the balance is less than the amount entered");
            }
        } else {
            // Display of the info message if the company does not exist.
            log.info("This company does not exist: ");
        }
        return user;
    }
}
