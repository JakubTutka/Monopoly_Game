package General;

import Cells.*;

import java.util.*;

public class Player {

    private String name;
    private int number;
    private int balance;
    private boolean inPrison;
    private int prisonCount;
    private int currentCell;
    private List<Property> cities;
    private HashMap<Nationality, Integer> nationalityCounter = new HashMap<Nationality, Integer>();
    private List oferty = new LinkedList<Offer>();
    private boolean isDrawn = false;
    private boolean drugaSzansa = false;

    Dice cube1;
    Dice cube2;
    Offer propo;

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
        this.balance = 3000;
        this.currentCell = 0;
        for(Nationality l:Nationality.values()) {
            nationalityCounter.put(l, 0);
        }

        cube1 = new Dice();
        cube2 = new Dice();

        cities = new ArrayList<>();

    }
    // getters and setters


    public boolean isDrugaSzansa() {
        return drugaSzansa;
    }

    public void setDrugaSzansa(boolean drugaSzansa) {
        this.drugaSzansa = drugaSzansa;
    }

    public boolean isDrawn() {
        return isDrawn;
    }

    public void setDrawn(boolean drawn) {
        isDrawn = drawn;
    }

    public Dice getCube1(){
        return cube1;
    }

    public Dice getCube2(){
        return cube2;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isInPrison() {
        return inPrison;
    }

    public void setInPrison(boolean inPrison) {
        this.inPrison = inPrison;
    }

    public List<Property> getCities() { return cities;
   }

    public void setCities(List<Property> cities) {
        this.cities = cities;
    }

    public HashMap<Nationality, Integer> getNationalityCounter() {
        return this.nationalityCounter;
    }

    public void setNationalityCounter(HashMap<Nationality, Integer> nationalityCounter) {
        this.nationalityCounter = nationalityCounter;
    }

    public List getOferty() {
        return oferty;
    }

    public void setOferty(List oferty) {
        this.oferty = oferty;
    }

    public int getPrisonCount() {
        return prisonCount;
    }

    public void setPrisonCount(int prisonCount) {
        this.prisonCount = prisonCount;
    }

    public int getCurrentCell() {
        return this.currentCell;
    }

    public void setCurrentCell(int currentCell) {
        this.currentCell = currentCell;
    }

    // moving methods

    public void setInPrison() {
        this.inPrison = true;
        this.currentCell = 10;
        this.prisonCount = 2;
    }

    public int drawDices(Dice cube1, Dice cube2) {
        int drawed1 = cube1.draw();
        int drawed2 = cube2.draw();
        isDrawn = true;
        return drawed1 + drawed2;
    }

    public void move() {
        this.currentCell += drawDices(cube1, cube2);
    }

    // money methods

    public void plusMoney(int money) {
        this.balance += money;
    }

    public void minusMoney(int money) {
        this.balance -= money;
    }

    public void isBankrut() {
        if (this.getBalance() < 0);
        System.out.println("Bankrut");
    }

    public void trade(Player trader, Property card, Property myProperty) {
        int proposalPrice;
        Scanner scan = new Scanner(System.in);
        System.out.println("Buy(1) or exchange(2)?");
        int tradeDecision = scan.nextInt();
        switch (tradeDecision) {
            case 1:
                System.out.println("Proposal price: ");
                proposalPrice = scan.nextInt();
                propo = new Offer(this, trader, card, proposalPrice);
                trader.oferty.add(propo);
                break;
            case 2:
                propo = new Offer(this, trader, myProperty, card);
                trader.oferty.add(propo);
                break;
        }
    }

    public void addOffer() {
        oferty.add(propo);
    }

    public void printOffer(Offer propo) {
        if (propo.isExchange()) {
            System.out.println("Gracz: " + propo.getKupiec().getName() + " proponuje wymienic: " + propo.getSprzedawane().getName()
                    + " za: " + propo.getKupowane().getName());
        }
        else {
            System.out.println("Gracz: " + propo.getKupiec().getName() + " proponuje wymienic: " + propo.getPrice()
                    + " za: " + propo.getKupowane().getName());
        }


    }
    public void acceptOffer() {
        if (propo.isExchange()) {
            this.removeCity(propo.kartaSprzedajacego);
            propo.kupiec.addCity(propo.kartaSprzedajacego);
            propo.kupiec.removeCity(propo.kartaKupca);
            this.addCity(propo.kartaKupca);
        }
        else {
            this.removeCity(propo.kartaSprzedajacego);
            propo.kupiec.addCity(propo.kartaSprzedajacego);
            propo.getKupiec().minusMoney(propo.getPrice());
            this.plusMoney(propo.getPrice());
        }
        oferty.remove(propo);
    }

    public void rejectOffer() {
        oferty.remove(propo);
    }


    public void addCity(Property card) {
        cities.add(card);
        int tmp = nationalityCounter.get(card.getNationality()) + 1;
        nationalityCounter.replace(card.getNationality(), tmp);
    }

    public void removeCity(Property card) {
        cities.remove(card);
        int tmp = nationalityCounter.get(card.getNationality()) - 1;
        nationalityCounter.replace(card.getNationality(), tmp);
    }

    @Override
    public String toString() {
        return name;
    }

}
