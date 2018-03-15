// package services;

// import static org.junit.Assert.assertTrue;
// import static org.junit.Assert.fail;

// import jpa.Task;
// import org.junit.Test;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.junit4.AbstractTransactionalJUnit4SpringContextTests;

// public class TaskPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

// 	@Test
// 	public void emptyListTest() {
// 		final List<Task> list = taskPersist.fetchAllTasks();
// 		assertTrue("List should be empty",list.isEmpty);
// 	}

// 	@Test
// 	public void saveValidTaskTest() {

// 	    // check to make sure that the test is correctly saving the data
// 	}

// 	@Test
// 	public void getValidTaskTest(){
// // can i get something back out of the database
// 	}	

// 	@Test
// 	public void saveBlankTaskTest(){
// 		try{
// 			final Task t = new Task();
// 			taskPersist.saveTask(t);
// 			fail("This should have failed since contents is blank");
// 		}catch(IllegalArguentException ignored){
// 		}
// 	}
	
// }