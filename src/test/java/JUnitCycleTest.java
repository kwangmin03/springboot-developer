import org.junit.jupiter.api.*;

public class JUnitCycleTest {
    @BeforeEach // 테스트 케이스를 시작하기 전마다 실행
    public void prepareEachTest() {
        System.out.println("각 테스트 전 준비 실행");
    }

    @AfterEach // 각 테스트 케이스를 실행한 후에 실행
    public void cleanEachTest() {
        System.out.println("각 테스트 후 설겆이 실행");
    }

    @Test
    public void test1(){
        System.out.println("test1");
    }

    @Test
    public void test2(){
        System.out.println("test2");
    }

    @Test
    public void test3(){
        System.out.println("test3");
    }

    @BeforeAll // 전체 테스트를 시작하기 전에 1회 실행하므로 메서드는 static으로 선언
    static void beforeAll(){
        System.out.println("모든 테스트 수행 전 준비작업");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("모든 테스트 수행 후 마지막 설겆이 작업");
    }

}
