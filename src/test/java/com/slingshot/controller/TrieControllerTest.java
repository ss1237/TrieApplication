package com.slingshot.controller;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TrieController.class)
@WithMockUser
public class TrieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testAdd() throws Exception{
		System.out.println("\ntestAdd() - Testing the add operation for one value.\n");
		String value = "val";
		mockMvc.perform(MockMvcRequestBuilders.get("/add?value="+value).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(value + " was added to the trie.")));

		cleanTrie(value);
	}

	@Test
	public void testAddDup() throws Exception{
		System.out.println("\ntestAddDup() - Testing the add operation for duplicate values.\n");
		String value = "val";
		mockMvc.perform(MockMvcRequestBuilders.get("/add?value="+value).accept(MediaType.APPLICATION_JSON));
		mockMvc.perform(MockMvcRequestBuilders.get("/add?value="+value).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(value + " is already in the trie.")));

		cleanTrie(value);
	}


	@Test
	// Characters that work: !@$*()-_=;:'"><,.?/~
	// Characters that don't work: #%^&+[]{}|\`
	public void testAddSpecial() throws Exception{
		System.out.println("\ntestAddSpecial() - Testing the add operation for special characters.\n");
		String value = "!@$*()-_=;:'\"><,.?/~";
		mockMvc.perform(MockMvcRequestBuilders.get("/add?value="+value).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(value + " was added to the trie.")));

		cleanTrie(value);
	}
	
	@Test
	public void testAddFailure() throws Exception{
		System.out.println("\ntestAddFailure() - Testing the add operation for no parameters.\n");
		mockMvc.perform(MockMvcRequestBuilders.get("/add"))
		.andExpect(status().is4xxClientError());
	}

	@Test
	public void testDelete() throws Exception{
		System.out.println("\ntestDelete() - Testing the delete operation for one value.\n");
		String value = "val";
		mockMvc.perform(MockMvcRequestBuilders.get("/add?value="+value).accept(MediaType.APPLICATION_JSON));
		mockMvc.perform(MockMvcRequestBuilders.get("/delete?value="+value).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(value + " was deleted from the trie.")));
	}

	@Test
	public void testDeleteNotFound() throws Exception{
		System.out.println("\ntestDeleteNotFound() - Testing the delete operation for a value not in the trie.\n");
		String value = "val";
		mockMvc.perform(MockMvcRequestBuilders.get("/delete?value="+value).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(value + " was not found in the trie.")));
	}

	@Test
	public void testDeleteFailure() throws Exception{
		System.out.println("\ntestDeleteFailure() - Testing the delete operation for no parameters.\n");
		mockMvc.perform(MockMvcRequestBuilders.get("/delete"))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testPrefix() throws Exception{
		System.out.println("\ntestPrefix() - Testing the prefix operation.\n");
		String[] values = new String[] {"val1", "val2", "not1"};
		String prefix = "va";
		
		for (String s : values) {
			mockMvc.perform(MockMvcRequestBuilders.get("/add?value="+s).accept(MediaType.APPLICATION_JSON));
		}
		
		mockMvc.perform(MockMvcRequestBuilders.get("/prefix?value=" + prefix).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("Words starting with " + prefix + ": [val1, val2]")));
		
		for (String s : values) {
			cleanTrie(s);
		}
	}

	@Test
	public void testPrefixNotFound() throws Exception{
		System.out.println("\ntestPrefixNotFound() - Testing the prefix operation for a value not in the trie.\n");
		String value = "val";
		mockMvc.perform(MockMvcRequestBuilders.get("/prefix?value="+value).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("Words starting with " + value + ": []")));
	}

	@Test
	public void testPrefixFailure() throws Exception{
		System.out.println("\ntestPrefixFailure() - Testing the prefix operation for no parameters.\n");
		mockMvc.perform(MockMvcRequestBuilders.get("/prefix"))
		.andExpect(status().is4xxClientError());
	}

	@Test
	public void testDisplayEmpty() throws Exception{
		System.out.println("\ntestDisplayEmpty() - Testing the display operation for an empty trie.\n");
		String expectedResult = "Contents of the trie: []";
		mockMvc.perform(MockMvcRequestBuilders.get("/display").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(expectedResult)));
	}

	@Test
	public void testDisplay() throws Exception{
		System.out.println("\ntestDisplay() - Testing the display operation for a nonempty trie.\n");
		String expectedResult = "Contents of the trie: [val]";
		mockMvc.perform(MockMvcRequestBuilders.get("/add?value=val").accept(MediaType.APPLICATION_JSON));
		mockMvc.perform(MockMvcRequestBuilders.get("/display").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(expectedResult)));

		cleanTrie("val");
	}
	
	@Test
	public void testSearchFound() throws Exception{
		System.out.println("\ntestSearchFound() - Testing the search operation for a value in the trie.\n");
		
		String[] values = new String[] {"val1", "val2", "not1"};
		
		for (String s : values) {
			mockMvc.perform(MockMvcRequestBuilders.get("/add?value="+s).accept(MediaType.APPLICATION_JSON));
		}
		
		mockMvc.perform(MockMvcRequestBuilders.get("/search?value=" + values[0]).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(values[0] + " was found in the trie.")));
		
		for (String s : values) {
			cleanTrie(s);
		}
	}
	
	@Test
	public void testSearchNotFound() throws Exception{
		System.out.println("\ntestSearchNotFound() - Testing the search operation for a value not in the trie.\n");

		String[] values = new String[] {"val1", "val2", "not1"};
		String other = "xx";
		
		for (String s : values) {
			mockMvc.perform(MockMvcRequestBuilders.get("/add?value="+s).accept(MediaType.APPLICATION_JSON));
		}
		
		mockMvc.perform(MockMvcRequestBuilders.get("/search?value=" + other).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(other + " was not found in the trie.")));
		
		for (String s : values) {
			cleanTrie(s);
		}
	}
	
	@Test
	public void testSearchFailure() throws Exception{
		System.out.println("\ntestSearchFailure() - Testing the search operation for no parameters.\n");
		mockMvc.perform(MockMvcRequestBuilders.get("/search"))
		.andExpect(status().is4xxClientError());
	}

	public void cleanTrie(String value) throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/delete?value="+value).accept(MediaType.APPLICATION_JSON));
	}
}
