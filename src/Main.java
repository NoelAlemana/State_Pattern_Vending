import java.util.Scanner;

interface VendingAlertState {
    public abstract void alert(AlertStateContext ctx);
}

class AlertStateContext {
    private VendingAlertState currentState;
    private int money;
    private int itemA;
    private int itemB;
    private int itemC;
    public AlertStateContext()
    {
        money = 0;
        itemA = 0;
        itemB = 0;
        itemC = 0;
    }

    public void setState(VendingAlertState state)
    {
        currentState = state;
    }

    public void alert() { currentState.alert(this); }

    public int getMoney() {
        return money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public int getItemA() {
        return itemA;
    }

    public void incItemA() {
        this.itemA++;
    }

    public int getItemB() {
        return itemB;
    }

    public void incItemB() {
        this.itemB++;
    }

    public int getItemC() {
        return itemC;
    }

    public void incItemC() {
        this.itemC++;
    }
    public void resetItem(){
        this.itemA = 0;
        this.itemB = 0;
        this.itemC = 0;
    }
}

class ReceiveMoneyState implements VendingAlertState {
    @Override public void alert(AlertStateContext ctx) {
        System.out.println("Please insert money");
        Scanner scanner = new Scanner(System.in);
        int money = scanner.nextInt();
        ctx.addMoney(money);
    }
}

class ChooseState implements VendingAlertState {
    @Override public void alert(AlertStateContext ctx)
    {
        System.out.println("Please choose a product\nA - 20\nB - 50\nC - 75");
        System.out.println("Money: "+ ctx.getMoney());
        char item;
        Scanner scanner = new Scanner(System.in);

        item = scanner.next().charAt(0);
        if(item == 'A' && ctx.getMoney()>=20){
            ctx.incItemA();
            ctx.addMoney(-20);
        } else if (item == 'B' && ctx.getMoney()>=50){
            ctx.incItemB();
            ctx.addMoney(-50);
        } else if (item == 'C' && ctx.getMoney()>=75){
            ctx.incItemC();
            ctx.addMoney(-75);
        } else{
            System.out.println("Insufficient money or Invalid input\n");
        }
    }
}
class ChangeState implements VendingAlertState{
    @Override public void alert(AlertStateContext ctx)
    {
        System.out.println("Please take your change");
        System.out.println("Change: " + ctx.getMoney()+ "\n");
        ctx.addMoney(-ctx.getMoney());
    }
}
class DispenseState implements VendingAlertState {
    @Override public void alert(AlertStateContext ctx)
    {
        System.out.println("Dispensing Item.....");
        if(ctx.getItemA() > 0){
            System.out.println(ctx.getItemA() + "x of item A");
        }
        if(ctx.getItemB() > 0){
            System.out.println(ctx.getItemB() + "x of item B");
        }
        if(ctx.getItemC() > 0){
            System.out.println(ctx.getItemC() + "x of item C");
        }
        ctx.resetItem();
    }
}

class StatePattern {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        AlertStateContext stateContext = new AlertStateContext();
        while (true) {
            System.out.println("Vending Machine Options:");
            System.out.println("1. Insert Money");
            System.out.println("2. Choose Product");
            System.out.println("3. Dispense Item");
            System.out.println("4. Take Change");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    stateContext.setState(new ReceiveMoneyState());
                    stateContext.alert();
                    break;
                case 2:
                    stateContext.setState(new ChooseState());
                    stateContext.alert();

                    break;
                case 3:
                    stateContext.setState(new DispenseState());
                    stateContext.alert();
                    break;
                case 4:
                    stateContext.setState(new ChangeState());
                    stateContext.alert();
                    break;
                case 5:
                    System.out.println("Exiting Vending Machine.");
                    stateContext.setState(new DispenseState());
                    stateContext.alert();
                    stateContext.setState(new ChangeState());
                    stateContext.alert();
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}