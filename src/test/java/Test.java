import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import ru.stepup.task.Account;
import ru.stepup.task.Currency;

public class Test {
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка имени на пустое значение")
    public void  testName(){
        Account acc = new Account("Vasia");
        Assertions.assertTrue(!(acc.getName() == null | acc.getName().equals("")));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Проверка имени на не корректные значение")
    public void  testIncName(){
        Account acc;
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Account(""));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Проверка баланса валюты на корректные значение")
    public void  testCurrenc(){
        Account acc = new Account("Vasia");;
        Currency currr = new Currency();
        acc.setCurrency(currr, 100);
        currr.setCurCurrency(2);
        acc.setCurrency(currr, 990);
        acc.setCurrency(currr, 888);
    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка баланса валюты на не корректные значение")
    public void  testIncCurrenc(){
        Account acc = new Account("Vasia");;
        Currency currr = new Currency();

        Assertions.assertThrows(IllegalArgumentException.class, () -> acc.setCurrency(currr, -100));
    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка undo")
    public void  testUndo(){
        Account acc = new Account("Denis");
        String str = acc.getName();
        acc.setName("Vasia");
        acc.undo();
        Assertions.assertTrue(acc.getName().equals(str));

    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка undo, если отменять нечего")
    public void  testUndoInc(){
        Account acc = new Account("Vasia");;
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> acc.undo());
    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка Copy")
    public void  testCopy(){
        Account acc = new Account("Denis");
        Account accCopy;
        Currency currr = new Currency();
        acc.setCurrency(currr, 100);
        currr.setCurCurrency(2);
        acc.setCurrency(currr, 990);
        acc.setCurrency(currr, 888);
        accCopy = acc.getCopy();
        Assertions.assertTrue(accCopy.getName().equals(acc.getName()) & acc.getCurBalance().equals(accCopy.getCurBalance()));

    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка Copy на не равно")
    public void  testnotCopy(){
        Account acc = new Account("Denis");
        Account accCopy;
        String str = acc.getName();
        acc.setName("Vasia");
        Currency currr = new Currency();
        acc.setCurrency(currr, 100);
        currr.setCurCurrency(2);
        acc.setCurrency(currr, 990);
        acc.setCurrency(currr, 888);
        accCopy = acc.getCopy();
        acc.setName("Vladimir");
        Assertions.assertFalse(accCopy.getName().equals(acc.getName()) & acc.getCurBalance().equals(accCopy.getCurBalance()));

    }
}


