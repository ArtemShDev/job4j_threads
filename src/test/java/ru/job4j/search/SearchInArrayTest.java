package ru.job4j.search;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SearchInArrayTest {

    @Test
    public void whenSortArr13() {
        Integer[] array = {2, 4, 4, 15, 78, 45, 77, 88, 92, 75, 32, 65, 42};
        Integer res = (Integer) SearchInArray.sort(array, 92);
        assertThat(res, is(8));
    }

    @Test
    public void whenSortArr9() {
        Integer[] array = {2, 4, 4, 15, 78, 45, 77, 88, 92};
        Integer res = (Integer) SearchInArray.sort(array, 77);
        assertThat(res, is(6));
    }

}