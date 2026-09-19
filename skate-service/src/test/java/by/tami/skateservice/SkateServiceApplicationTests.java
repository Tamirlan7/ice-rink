package by.tami.skateservice;

import by.tami.skateservice.model.Sex;
import by.tami.skateservice.model.Skate;
import by.tami.skateservice.repository.SkateRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class SkateServiceApplicationTests {

    @Autowired
    private SkateRepository skateRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Disabled
    void insertMockData() {
        List<Skate> skates = new LinkedList<>();
        int sizeIncrement = 0;

        for (int i = 0; i < 20; i++) {
            Skate skate = new Skate();
            skate.setSkateIdentityNumber((long) (1000 + i));
            skate.setSex(i % 2 == 0 ? Sex.FEMALE : Sex.MALE);
            skate.setSize((short)(36 + sizeIncrement));
            sizeIncrement++;
            if (i % 10 == 0) {
                sizeIncrement = 0;
            }
            skates.add(skate);
        }

        skateRepository.saveAll(skates);
    }

}
