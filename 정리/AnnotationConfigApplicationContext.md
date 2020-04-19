## AnnotationConfigApplicationContext

**빈 생성 원리를 파악해보자**

<img src="https://user-images.githubusercontent.com/40616436/79639825-06294900-81c9-11ea-8758-20b4537ecbdc.png" alt="image" style="zoom:50%;" />



AbstractApplicationContext.class

~~~java
static {
  ContextClosedEvent.class.getName();
}
~~~

- 애플리케이션 컨텍스트가 닫힐 경우 발생하는 이벤트로서, 애플리케이션 컨텍스트 닫힐 시 호출하기 위해 미리 클래스 네임을 받아오는 것 같다..?



AnnotationConfigApplicationContext.class

![image](https://user-images.githubusercontent.com/40616436/79641831-cff1c680-81d4-11ea-93cf-5f203a47df38.png)

- AnnotationConfigApplicationContext 생성 및 빈 등록, refresh 하는 부분이다.



this()

<img src="https://user-images.githubusercontent.com/40616436/79681061-9b702000-8251-11ea-95f2-9dd93b5c420c.png" alt="image" style="zoom:50%;" />

- AnnoatedBeanDefinitionReader
- ClassPathBeanDefinitionScanner



register()

AnnotatedBeanDefinitionReader.java의 doRegisterBean()에서 bean 생성

~~~java
private <T> void doRegisterBean(Class<T> beanClass, @Nullable String name,
			@Nullable Class<? extends Annotation>[] qualifiers, @Nullable Supplier<T> supplier,
			@Nullable BeanDefinitionCustomizer[] customizers) {

  AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
  if (this.conditionEvaluator.shouldSkip(abd.getMetadata())) {
    return;
  }

  abd.setInstanceSupplier(supplier);
  ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
  abd.setScope(scopeMetadata.getScopeName());
  String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, this.registry));

  AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
  if (qualifiers != null) {
    for (Class<? extends Annotation> qualifier : qualifiers) {
      if (Primary.class == qualifier) {
        abd.setPrimary(true);
      }
      else if (Lazy.class == qualifier) {
        abd.setLazyInit(true);
      }
      else {
        abd.addQualifier(new AutowireCandidateQualifier(qualifier));
      }
    }
  }
  if (customizers != null) {
    for (BeanDefinitionCustomizer customizer : customizers) {
      customizer.customize(abd);
    }
  }

  BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
  definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
  BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);
}
~~~

- 해당 메소드에서 주의깊게 볼 것
  - AnnotatedGenericBeanDefinition
  - ScopeMetadata
  - BeanDefinitionHolder
  - ...



*AnnotatedGenericBeanDefinition*

~~~java
AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
~~~

![image](https://user-images.githubusercontent.com/40616436/79682271-c2335400-825b-11ea-9ba6-bdac96385a84.png)

- beanClass(@Configuration)의 생성자 인수 값, Bean 인스턴스 정보를 갖는다.

<img src="https://user-images.githubusercontent.com/40616436/79682511-ed1ea780-825d-11ea-9b65-93c20fa30d6f.png" alt="image" style="zoom:50%;" />

- 해당 객체(AppConfig)의 어노테이션 정보도 확인되는 것을 볼 수 있다.

  ![image](https://user-images.githubusercontent.com/40616436/79682796-2d7f2500-8260-11ea-99d1-1157cd40efe7.png)

