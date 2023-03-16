package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedTest {

    @Test
    void unchecked_catch(){
        Service service = new Service();
        service.callCatch();

    }

    @Test
    void unchecked_throw(){
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    //RuntimeException을 상속받은 예외는 언체크 예외가 된다.
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    static class Repository{
        public void call(){
            throw new MyUncheckedException("ex");
        }
    }

    // Unchecked예외는 예외를 잡거나 던지지 않아도 된다.
    // 예외를 잡지 않으면 자동으로 밖으로 던진다.
    static class Service{
        Repository repository = new Repository();

        //필요한경우 예외를 잡아서 처리하면된다.
        public void callCatch(){
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외처리, message={}", e.getMessage(), e);
            }
            }

        /**
         * 예외를 잡지 않아도 자연스럼게 사우이로 넘아간다
         * 체크와 다르게 throws 예외 선언을 하지 않아도 된다.
         */
        public void callThrow(){
                repository.call();
            }
    }
}
