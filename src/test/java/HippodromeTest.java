import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {

    @Test
    void ctor_nullList_throws() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", ex.getMessage());
    }

    @Test
    void ctor_emptyList_throws() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> new Hippodrome(List.of()));
        assertEquals("Horses cannot be empty.", ex.getMessage());
    }

    @Test
    void getHorses_sameObjectsSameOrder() {
        List<Horse> src = new ArrayList<>();
        for (int i = 0; i < 30; i++) src.add(new Horse("H" + i, i, i));
        Hippodrome hip = new Hippodrome(src);

        assertEquals(src, hip.getHorses());
        assertThrows(UnsupportedOperationException.class,
                () -> hip.getHorses().add(new Horse("X", 1)));
    }

    @Test
    void move_callsMoveOnEachHorse() {
        List<Horse> mocks = new ArrayList<>();
        for (int i = 0; i < 50; i++) mocks.add(mock(Horse.class));
        Hippodrome hip = new Hippodrome(mocks);

        hip.move();

        mocks.forEach(h -> verify(h).move());
    }

    @Test
    void getWinner_returnsMaxDistance() {
        Horse a = new Horse("A", 1, 10);
        Horse b = new Horse("B", 1, 20);
        Horse c = new Horse("C", 1, 15);
        Hippodrome hip = new Hippodrome(List.of(a, b, c));

        assertSame(b, hip.getWinner());
    }
}