package ru.stepup.task;

import java.util.HashMap;
import java.util.Stack;

public class Account {
    private String name;
    private HashMap<String, Integer> curBalance = new HashMap<>();
    private Stack<String> stUndo = new Stack<>();

    public Account(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null | name.equals("")) throw new IllegalArgumentException("Имя не может быть пустым.");

            stUndo.push("name " + this.name);
            this.name = name;

    }

    public HashMap<String, Integer> getCurBalance() {
        return new HashMap<>(curBalance);
    }

    public void setCurrency(Currency nameCur, Integer bal) {

        if (bal < 0) throw new IllegalArgumentException("Баланс валюты не может быть отрицательным.");
            if (curBalance.containsKey(nameCur.curCurrency))
                stUndo.push("set " + nameCur.curCurrency + " " + this.curBalance.get(nameCur.curCurrency));
            else stUndo.push("remove " + nameCur.curCurrency);
            this.curBalance.put(nameCur.curCurrency, bal);
    }
    public void undo() {
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

    public Save<Account> save(){
        return new AccountSave();
    }

    private class AccountSave implements Save<Account> {
    private String name;
    private HashMap<String, Integer> curBalance;
        public AccountSave(){
            this.name = Account.this.name;
            this.curBalance = new HashMap<>(Account.this.curBalance);
        }

        @Override
        public void restore() {
            Account.this.name = this.name;
            Account.this.curBalance = new HashMap<>(this.curBalance);

        }
    }
}

