package org.spring.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1); // PrototypeBean이 1회만 생성되기 때문에 count2는 2가 됨!
    }

    @Scope("singleton")
    static class ClientBean {
        // private final PrototypeBean prototypeBean; // (생성 시점에 주입) ClientBean이 생성되는 시점에 PrototypeBean을 1회만 호출해서 생성한다!!

        // @Autowired
        // private ObjectProvider<PrototypeBean> prototypeBeanProvider; // ObjectFactory에서 기능이 더 확장된 클래스
        // private ObjectFactory<PrototypeBean> prototypeBeanProvider;

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        // @Autowired
        // public ClientBean(PrototypeBean prototypeBean) {
        //     this.prototypeBean = prototypeBean;
        // }

        public int logic() {
            // PrototypeBean prototypeBean = prototypeBeanProvider.getObject(); // 스프링 컨테이너에서 빈을 찾아서 반환해줌. 항상 새로운 프로토타입 빈이 생성됨
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
