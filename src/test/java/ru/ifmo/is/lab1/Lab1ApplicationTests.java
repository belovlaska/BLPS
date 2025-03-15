package ru.ifmo.is.lab1;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Lab1Application")
public class Lab1ApplicationTests {

  transient MockedStatic<SpringApplication> springApplicationMock;

  @BeforeAll
  void beforeAll() {
    springApplicationMock = mockStatic(SpringApplication.class);
  }

  @Test
  void contextLoads() {
    springApplicationMock
      .when(() -> SpringApplication.run(Lab1Application.class, new String[] {}))
      .thenCallRealMethod();

    Lab1Application.main(new String[] {});

    springApplicationMock.verify(() -> SpringApplication.run(Lab1Application.class, new String[] {}), times(1));
  }
}
