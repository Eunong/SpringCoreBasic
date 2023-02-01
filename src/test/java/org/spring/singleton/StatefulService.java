package org.spring.singleton;

public class StatefulService {

    private int price; // 상태를 유지하는 필드

    // 대 장애 발생 가능
    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; // 문제 발생 지점
    }

    public int getPrice() {
        return price;
    }

    // 무상태(stateless)로 개선
    public int orderStateless(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

}
