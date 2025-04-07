package thread.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankAccountV5 implements BankAccount {

    private int balance;

    private final Lock lock = new ReentrantLock();

    public BankAccountV5(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작 : " + getClass().getSimpleName());

        if (!lock.tryLock()) {
            log("[진입 실패] 이미 처리 중인 작업이 있습니다.");
            return false;
        }

        try {
            log("[검증 시작] 출금액 : " + amount + "원, 잔액 : " + balance + "원");
            if (balance < amount) {
                log("[검증 실패] 출금액 : " + amount + "원, 잔액 : " + balance + "원");
                return false;
            }

            log("[검증 완료] 출금액 : " + amount + "원, 잔액 : " + balance + "원");
            sleep(1000);
            balance = balance - amount;
            log("[출금 완료] 출금액 : " + amount + "원, 잔액 : " + balance + "원");
        } finally {
            lock.unlock();  // lock 해제
        }

        log("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock();    // ReentrantLock 이용하여 lock 을 걸기
        try {
            return balance;
        } finally {
            lock.unlock();  // lock 해제
        }
    }
}
