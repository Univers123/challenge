package com.toufik.challenge.services;

import com.toufik.challenge.entities.Distribution;
import com.toufik.challenge.entities.User;
import com.toufik.challenge.repository.UserRepository;
import com.toufik.challenge.utils.DistributionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public double userBalance(User user, String type) {
        // Transform type to lowercase for comparison.
        String s = type.toLowerCase();
        // Declaration and instantiation of the value to be returned.
        double solde = 0.00;
        // Recovery of the user if it exists
        Optional<User> current = repository.findById((user.getUserId()));
        // Verification that the company exists
        if (current.isPresent()) {
            // Recovery of the users wedooGiftList
            List<Distribution> wedooGiftList = current.get().getWedooGiftList();
            if (s.equals("gift")) {
                // Iterate through the wedooGiftList, filtering on distributions that have a GIFT type and retrieving only the amounts.
                List<Double> wedooGifts = wedooGiftList.stream().filter(wg -> wg.getType().equals(DistributionEnum.GIFT))
                        .map(Distribution::getAmount).collect(Collectors.toList());
                // Check if the list is not empty
                if (!wedooGifts.isEmpty()) {
                    //Iterate through the wedooGifts by summing the amounts.
                    solde = wedooGifts.stream().mapToDouble(f -> f).sum();
                }
            }
            if (s.equals("meal")) {
                // Iterate through the wedooGiftList, filtering on distributions that have a MEAL type and retrieving only the amounts.
                List<Double> wedooMeals = wedooGiftList.stream().filter(wg -> wg.getType().equals(DistributionEnum.MEAL))
                        .map(Distribution::getAmount).collect(Collectors.toList());
                // Check if the list is not empty
                if (!wedooMeals.isEmpty()) {
                    //Iterate through the wedooMeals by summing the amounts.
                    solde = wedooMeals.stream().mapToDouble(f -> f).sum();
                }
            }
        } else {
            // Display of the info message if the user does not exist.
            log.info("This user does not exist.");
        }
        // Return the result.
        return solde;
    }
}
