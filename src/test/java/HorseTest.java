import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorseTest {

    @Test
    void ctor_nullName_throws() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void ctor_blankName_throws(String bad) {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> new Horse(bad, 1, 1));
        assertEquals("Name cannot be blank.", ex.getMessage());
    }

    @Test
    void ctor_negativeSpeed_throws() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> new Horse("A", -1, 1));
        assertEquals("Speed cannot be negative.", ex.getMessage());
    }

    @Test
    void ctor_negativeDistance_throws() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> new Horse("A", 1, -1));
        assertEquals("Distance cannot be negative.", ex.getMessage());
    }

    @Test
    void getName_returnsCtorValue() {
        assertEquals("Bolt", new Horse("Bolt", 2, 3).getName());
    }

    @Test
    void getSpeed_returnsCtorValue() {
        assertEquals(2, new Horse("Bolt", 2, 3).getSpeed());
    }

    @Test
    void getDistance_returnsCtorValue() {
        assertEquals(3, new Horse("Bolt", 2, 3).getDistance());
    }

    @Test
    void getDistance_defaultCtor_zero() {
        assertEquals(0, new Horse("Bolt", 2).getDistance());
    }

    @Test
    void move_invokesRandomWithArgs() {
        try (MockedStatic<Horse> ms = mockStatic(Horse.class)) {
            ms.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            Horse h = new Horse("Bolt", 2, 5);

            h.move();

            ms.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @DisplayName("move() updates distance correctly")
    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void move_updatesDistance(double rnd) {
        try (MockedStatic<Horse> ms = mockStatic(Horse.class)) {
            ms.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(rnd);
            Horse h = new Horse("Bolt", 3, 7);

            h.move();

            assertEquals(7 + 3 * rnd, h.getDistance());
        }
    }
}