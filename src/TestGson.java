import com.google.gson.Gson;

import bean.Person;

public class TestGson {
	
	public static void main(String[] args) {
		Person person = new Person();
		person.setName("CHK");
		person.setSex("boy");
		person.setAccount("12345");
		person.setPassword("12345");
		person.setPhone("18826402897");
		
		Gson gson = new Gson();
		gson.toJson(person);
		System.out.println(gson.toJson(person));
	}
}
