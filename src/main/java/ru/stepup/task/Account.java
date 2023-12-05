package ru.stepup.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Account {
    private String name;
    private HashMap<String, Integer> curBalance = new HashMap<>();
    private Stack<String> stUndo = new Stack<>();

    private boolean copyObj = false;
    public Account(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(this.copyObj) return;
        if (name == null | name.equals("")) throw new IllegalArgumentException("Имя не может быть пустым.");

            stUndo.push("name " + this.name);
            this.name = name;

    }

    public HashMap<String, Integer> getCurBalance() {
        return new HashMap<>(curBalance);
    }

    public void setCurrency(Currency nameCur, Integer bal) {

        if (bal < 0) throw new IllegalArgumentException("Баланс валюты не может быть отрицательным.");
        if(this.copyObj) return;
            if (curBalance.containsKey(nameCur.curCurrency))
                stUndo.push("set " + nameCur.curCurrency + " " + this.curBalance.get(nameCur.curCurrency));
            else stUndo.push("remove " + nameCur.curCurrency);
            this.curBalance.put(nameCur.curCurrency, bal);
    }
    public Account getCopy() {
        Account acccopy = new Account(this.getName());
        acccopy.curBalance = this.getCurBalance();
        acccopy.copyObj = true;
        return acccopy;
    }
    public void undo() {
        if(this.copyObj) return;
        String[] arr = stUndo.pop().split(" ");
        if (stUndo.isEmpty()) throw new IndexOutOfBoundsException("Нет изменений для отмены.");
        if (arr.length > 0) {
            switch (arr[0]) {
                case "name": {
                    this.name = arr[1];
                    break;
                }
                case "set": {
                    this.curBalance.put(arr[1], Integer.parseInt(arr[2]));
                    break;
                }
                case "remove": {
                    this.curBalance.remove(arr[1]);
                    break;
                }
                }
        }

    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", currency=" + curBalance +
                '}';
    }
}

