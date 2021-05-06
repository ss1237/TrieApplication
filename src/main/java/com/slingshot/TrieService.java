package com.slingshot;

public class TrieService {
	
	private TrieNode root = new TrieNode("");
	
	public String add(String s) {
		TrieNode cur = root;
		s = s.toLowerCase();
		
		if (cur.getWords().contains(s)) {
			return s + " is already in the trie.";
		}
		
		for (char c : s.toCharArray()) {
			cur.getWords().add(s);
			cur = cur.getChild(c);
		}
		
		cur.getWords().add(s);
		cur.setEnd(true);
		return s + " was added to the trie.";
	}
	
	public String delete(String s) {
		TrieNode cur = root;
		
		if (!cur.getWords().contains(s)) {
			return s + " was not found in the trie.";
		}
		
		cur.getWords().remove(s);
		
		for (char c : s.toCharArray()) {
			cur = cur.getChild(c);
			cur.getWords().remove(s);
		}
		
		cur.setEnd(false);
		return s + " was deleted from the trie.";
		
	}
	
	public String search(String s) {
		if (root.getWords().contains(s)) {
			return s + " was found in the trie.";
		}
		return s + " was not found in the trie.";
	}
	
	public String prefix(String s) {
		TrieNode cur = root;
		
		for (char c : s.toCharArray()) {
			if (!cur.getChildren().containsKey(c)) {
				return "[]";
			}
			cur = cur.getChild(c);
		}
		
		return cur.getWords().toString();
	}
	
	public String display() {
		return "Contents of the trie: " + prefix("");
	}
}
