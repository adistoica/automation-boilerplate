package api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class APITest {

    @Test(description = "Basic API test")
    public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {

        given().
                when().
                get("https://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
    }
}
