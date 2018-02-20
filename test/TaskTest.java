import org.junit.Test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import static org.junit.Assert.assertNotNull;

import models.Task;
import org.junit.Test;
import play.db.jpa.JPA;

public class TaskTest {

	//@Test
	@Override
	public void create() {
		running(fakeApplication(), new Runnable(){
			public void run() {
				Task task = new Task();
				task.setContents("Some Test");
				JPA.em().persist(task);
				
			}
		});
	}
}