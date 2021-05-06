package com.slingshot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slingshot.TrieService;

@RestController
public class TrieController {

	TrieService ts = new TrieService();
	
	@RequestMapping("/add")
	String add(@RequestParam String value) {
		return ts.add(value);
	}
	
	@RequestMapping("/delete")
	String delete(@RequestParam String value) {
		return ts.delete(value);
	}
	
	@RequestMapping("/search")
	String search(@RequestParam String value) {
        return ts.search(value);
	}
	
	@RequestMapping("/display")
	String display() {
		return ts.display();
	}
	
	@RequestMapping("/prefix")
	String display(@RequestParam String value) {
		return "Words starting with " + value + ": " + ts.prefix(value);
	}
}