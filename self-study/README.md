
# 목차
- [목차](#목차)
- [Lombok](#lombok)
  - [1.1. @ToString](#11-tostring)
  - [1.2. @Getter](#12-getter)
  - [1.3. @Setter](#13-setter)
  - [1.4. @Data](#14-data)

# Lombok
## 1.1. @ToString
- exclude : 특정 필드를 toString() 결과에서 제외
```java
@ToString(exclude = "name")
public class User {
    private String name;
}
```
## 1.2. @Getter
## 1.3. @Setter

## 1.4. @Data
- @ToString
- @EqualsAndHashCode
- @Getter, @Setter
- @RequiredArgsConstructor