package ru.job4j.concurrent.pool;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class ParallelArraySearchTest {

    @Test
    public void whenSearch() {
        User model = new User("Смирнова Светлана Николаевна", "iv1006@gmail.com");
        User[] users = {
                new User("Иванов Иван Иванович", "iv1000@yandex.ru"),
                new User("Петрова Мария Павловна", "iv1001@yandex.ru"),
                new User("Сидоров Петр Павлович", "iv1002@yandex.ru"),
                new User("Макаренко Светлана Павловна", "iv1003@yandex.ru"),
                new User("Макаренко Леонид Иванович", "iv1004@yandex.ru"),
                new User("Курицына Галина Николаевна", "iv1005@yandex.ru"),
                model,
                new User("Коробов Валерий Юрьевич", "iv1007@yandex.ru"),
                new User("Скосорева Зинаида Анатольевна", "iv1008@yandex.ru"),
                new User("Метлина Светлана Анатольевна", "iv1009@yandex.ru"),
                new User("Блинов Вениамин Проклович", "iv1010@gmail.com"),
                new User("Шашков Юстиниан Станиславович", "iv1011@gmail.com"),
                new User("Виноградов Матвей Васильевич", "iv1012@yandex.ru"),
                new User("Юдина Беатриса Еремеевна", "iv1013@yandex.ru"),
                new User("Стрелкова Марфа Романовна", "iv1014@yandex.ru")
        };
        ForkJoinPool fjp = new ForkJoinPool();
        Integer result = fjp.invoke(new ParallelArraySearch<>(users, model, 0, users.length));
        assertThat(result, is(6));
    }
}