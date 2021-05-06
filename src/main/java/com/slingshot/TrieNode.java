package com.slingshot;

import java.util.HashMap;
import java.util.TreeSet;

public class TrieNode {

	private HashMap<Character, TrieNode> children;
	private String str;
	private boolean isEnd;
	private TreeSet<String> words;
	
	public TrieNode(String s) {
		children = new HashMap<Character, TrieNode>();
		str = s;
		isEnd = false;
		words = new TreeSet<String>();
	}
	
	public TrieNode getChild(char c) {
		if (!children.containsKey(c)) {
			children.put(c, new TrieNode(str + c));
		}
		return children.get(c);
	}
	
	public HashMap<Character, TrieNode> getChildren() {
		return children;
	}

	public void setChildren(HashMap<Character, TrieNode> children) {
		this.children = children;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public TreeSet<String> getWords() {
		return words;
	}

	public void setWords(TreeSet<String> words) {
		this.words = words;
	}
}
