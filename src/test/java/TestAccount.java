import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import ru.stepup.task.Account;
import ru.stepup.task.Currency;
import ru.stepup.task.Save;

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
        acc = acc.undo();
        System.out.println(acc);
        Assertions.assertTrue(acc.getName().equals(str));

    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка undo, если отменять нечего")
    public void  testUndoInc(){
        Account acc = new Account("Vasia");;
        System.out.println(acc);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> acc.undo());
    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка Copy")
    public void  testCopy(){
        Account acc = new Account("Denis");
        String tmp = acc.getName();
        Save<Account> save =acc.save();
        Currency currr = new Currency();
        acc.setName("Vasia");
        acc.setCurrency(currr, 100);
        currr.setCurCurrency(2);
        acc.setCurrency(currr, 990);
        acc.setCurrency(currr, 888);
        save.restore();

        Assertions.assertEquals(tmp, acc.getName());

    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка Copy на не равно")
    public void  testnotCopy(){
        Account acc = new Account("Denis");
        String tmp = acc.getName();
        Save<Account> save =acc.save();
        Currency currr = new Currency();
        acc.setName("Vasia");
        acc.setCurrency(currr, 100);
        currr.setCurCurrency(2);
        acc.setCurrency(currr, 990);
        acc.setCurrency(currr, 888);
        Assertions.assertFalse(Boolean.parseBoolean(tmp), acc.getName());

    }
}


