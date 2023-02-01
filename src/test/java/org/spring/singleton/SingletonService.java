package org.spring.singleton;

public class SingletonService {

    // static으로 선언했기 때문에 객체가 하나만 존재함
    // jvm이 기동될 때 static 영역에 instance를 미리 하나 생성해서 올려둔다.
    private static final SingletonService instance = new SingletonService();

    // 본 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다.
    public static SingletonService getInstance() {
        return instance;
    }

    // 외부에서 객체 선언 불가하도록 제어하기 위해 private 생성자를 선언한다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
