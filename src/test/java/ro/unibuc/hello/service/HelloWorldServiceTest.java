package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;

// import io.micrometer.core.annotation.Counted;
// import io.micrometer.core.annotation.Timed;
// import io.micrometer.core.instrument.MeterRegistry;
// import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class HelloWorldServiceTest {

    @Mock
    InformationRepository mockInformationRepository;

    @InjectMocks
    HelloWorldService helloWorldService = new HelloWorldService();

    // @Autowired
    // MeterRegistry metricsRegistry;

    @Test
    // @Timed(value = "hello.greeting.time", description = "Time taken to return greeting")
    // @Counted(value = "hello.greeting.count", description = "Times greeting was returned")
    void test_hello_returnsGreeting(){
        // Arrange
        String name = "John";

        // Act
        Greeting greeting = helloWorldService.hello(name);

        // Assert
        Assertions.assertEquals(1, greeting.getId());
        Assertions.assertEquals("Hello, John!", greeting.getContent());
    }

    @Test
    void test_hello_returnsGreeting_whenNameNull(){
        // Arrange

        // Act
        Greeting greeting = helloWorldService.hello(null);

        // Assert
        Assertions.assertEquals(1, greeting.getId());
        Assertions.assertEquals("Hello, null!", greeting.getContent());
    }

    @Test
    void test_buildGreetingFromInfo_returnsGreetingWithInformation() {
        // Arrange
        String title = "someTitle";
        InformationEntity informationEntity = new InformationEntity(title, "someDescription");
        String text = "Information[title='someTitle', description='someDescription']";
        Assertions.assertEquals(text, informationEntity.toString());

        when(mockInformationRepository.findByTitle(title)).thenReturn(informationEntity);

        // Act
        Greeting greeting = helloWorldService.buildGreetingFromInfo(title);

        // Assert
        Assertions.assertEquals(1, greeting.getId());
        Assertions.assertEquals("someTitle : someDescription!", greeting.getContent());
    }

    @Test
    void test_buildGreetingFromInfo_throwsEntityNotFoundException_whenInformationNull() {
        // Arrange
        String title = "someTitle";

        when(mockInformationRepository.findByTitle(title)).thenReturn(null);

        try {
            // Act
            Greeting greeting = helloWorldService.buildGreetingFromInfo(title);
        } catch (Exception ex) {
            // Assert
            Assertions.assertEquals(ex.getClass(), EntityNotFoundException.class);
            Assertions.assertEquals(ex.getMessage(), "Entity: someTitle was not found");
        }
    }

}