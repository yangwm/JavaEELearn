/**
 * 
 */
package first;

/**
 * person bean
 * 
 * @author yangwm Aug 5, 2010 11:20:16 AM 
 */
public class Person {
    private String name;
    private int age;
    private String hobby;
    
    public Person() {
    }

    public Person(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person{name=");
        sb.append(name);
        sb.append(", age=");
        sb.append(age);
        sb.append(", hobby=");
        sb.append(hobby);
        sb.append("}");
        return sb.toString();
    }
    

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getHobby() {
        return hobby;
    }
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    
}
