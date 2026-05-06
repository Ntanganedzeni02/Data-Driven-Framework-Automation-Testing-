package testcases;

import base.Keywords;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class OpenAccountTest extends Keywords {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void OpenAccount(String customer, String currency){

    }

}
