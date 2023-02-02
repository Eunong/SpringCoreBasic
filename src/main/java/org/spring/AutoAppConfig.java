package org.spring;

import org.spring.member.MemberRepository;
import org.spring.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
/**
 * 아무 속성도 추가하지 않았을 경우에는 디폴트로 자기 자신의 패키지 하위에서 찾는다. => 관계적으로 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 둔다. 보통은 basePackages 지정은 생략한다.
 * @SpringBootApplication 애노테이션 안에 @ComponentScan이 포함되어 있다.
 * 보통 @SpringBootApplication 애노테이션이 붙은 클래스는 프로젝트 시작 루트 위치에 두는 것이 관례이다.
 */
@ComponentScan(
        /**
         * 해당 패키지 하위만 컴포넌트 스캔을 수행하게 지정한다. 탐색할 시작 패키지를 지정함!
        */
        basePackages = "org.spring.member",
        // basePackages = {"org.spring.member", "org.spring.order"} // 여러 패키지를 지정하고 싶을 경우

        /**
         * 해당 클래스의 패키지부터 찾아온다. AutoAppConfig.class로 지정되어 있을 경우에는 org.spring에서 찾음
        */
        basePackageClasses = AutoAppConfig.class,

        /**
         * ComponentScan을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 등록되기 때문에
         * 앞에서 만든 AppConfig.java, TestConfig.java의 설정정보도 함께 등록되고 실행된다.
         * excludeFilters를 사용하여 설정정보는 컴포넌트 스캔 대상에서 제외한다. (보통 실무에서는 이렇게 별도로 제외하지 않는다.)
        */
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

        /**
         * MemoryMemberRepository 클래스에 @Component 애노테이션이 붙어 있어서 자동으로 빈을 생성하지만 수동으로 생성한 빈과 중복 상황을 테스트하기 위해 수동으로 동일 이름의 빈을 생성하는 코드를 추가한다.
         * 수동으로 생성한 빈이 생성되었음을 확인한다. (runtime message)Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing [Generic bean: class [org.spring.member.MemoryMemberRepository]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in file [/Users/eunyoung/workspace/project/SpringCoreBasic/build/classes/java/main/org/spring/member/MemoryMemberRepository.class]] with [Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=autoAppConfig; factoryMethodName=memberRepository; initMethodName=null; destroyMethodName=(inferred); defined in org.spring.AutoAppConfig]
         * - SpringCore에서 : 수동으로 등록한 빈과, 자동으로 등록한 빈이 동시에 존재할 경우 “수동”으로 등록한 빈이 우선권을 가지고 생성된다.
         * - SpringBoot에서(@SpringBootApplication) : 수동 등록 빈과 자동 등록 빈이 동시에 존재할 경우 오류를 발생시킨다.
         *                                           스프링부트는 중복으로 빈이 생성될 경우 오류를 기본으로 발생시킨다.
         *                                           스프링Core와 같이 수동 등록 빈을 우선으로 생성하고 싶을 경우 설정을 변경해주면 된다.
         *                                           CoreApplication.java 클래스 수행 시 오류 메세지 : The bean 'memoryMemberRepository', defined in class path resource [org/spring/AutoAppConfig.class], could not be registered. A bean with that name has already been defined in file [/Users/eunyoung/workspace/project/SpringCoreBasic/out/production/classes/org/spring/member/MemoryMemberRepository.class] and overriding is disabled.
         */
        @Bean("memoryMemberRepository")
        MemberRepository memberRepository() {
                return new MemoryMemberRepository();
        }
}
