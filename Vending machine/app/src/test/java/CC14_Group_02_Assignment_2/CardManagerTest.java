package CC14_Group_02_Assignment_2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CardManagerTest {
    @Test
    public void TestCardDetails() {
        CardManager cm = new CardManager("../app/src/main/resources/Databases/Card.csv", "../app/src/main/resources/Databases/UserSavedCard.csv");
        boolean validity1 = cm.checkCardDetail("1234", "5678");//invalid becuase of wrong card number
        assertEquals(validity1, false);
        boolean validity2 = cm.checkCardDetail("1234567890", "5678");//invalid becuase pf wrong password
        assertEquals(validity2, false);
        boolean validity3 = cm.checkCardDetail("1234567890", "4399");//valid
        assertEquals(validity3, true);
    }

}
